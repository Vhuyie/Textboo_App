package com.example.proj.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proj.R
import com.example.proj.data.DataStore
import com.example.proj.data.SessionManager
import com.example.proj.models.User

class RegisterActivity : AppCompatActivity() {
    private lateinit var etStudentNo: EditText
    private lateinit var etName: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirm: EditText
    private lateinit var btnRegister: Button
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        etStudentNo = findViewById(R.id.et_student_no)
        etName = findViewById(R.id.et_name)
        etPassword = findViewById(R.id.et_password)
        etConfirm = findViewById(R.id.et_confirm)
        btnRegister = findViewById(R.id.btn_register)
        btnLogin = findViewById(R.id.btn_login)

        btnRegister.setOnClickListener {

            if (validate()) {

                val users = SessionManager.getUsers(this)

                // CHECK IF USER EXISTS
                val existingUser = users.find {

                    it.studentNo == etStudentNo.text.toString()

                            ||

                            it.name.equals(
                                etName.text.toString(),
                                ignoreCase = true
                            )
                }

                // STOP REGISTRATION
                if (existingUser != null) {

                    Toast.makeText(
                        this,
                        "Student number or name already exists",
                        Toast.LENGTH_LONG
                    ).show()

                    return@setOnClickListener
                }

                // CREATE NEW USER
                val user = User(
                    etStudentNo.text.toString(),
                    etName.text.toString(),
                    etPassword.text.toString()
                )

                // SAVE USER
                users.add(user)

                SessionManager.saveUsers(this, users)

                Toast.makeText(
                    this,
                    "Registered Successfully",
                    Toast.LENGTH_SHORT
                ).show()

                startActivity(
                    Intent(this, LoginActivity::class.java)
                )

                finish()
            }
        }

        btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun validate(): Boolean {
        if (etStudentNo.text.isEmpty()) {
            etStudentNo.error = "Required"
            return false
        }
        if (etName.text.isEmpty()) {
            etName.error = "Required"
            return false
        }
        if (etPassword.text.isEmpty()) {
            etPassword.error = "Required"
            return false
        }

        if (etPassword.text.toString() != etConfirm.text.toString()) {
            etConfirm.error = "Passwords do not match"
            return false
        }
        return true
    }
}
