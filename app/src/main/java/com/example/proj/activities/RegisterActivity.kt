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
import com.example.proj.data.SessionManager
import com.example.proj.models.User

class RegisterActivity : AppCompatActivity() {
    private lateinit var stStudentNo: EditText
    private lateinit var stName: EditText
    private lateinit var stPassword: EditText
    private lateinit var stConfirm: EditText
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
        stStudentNo = findViewById(R.id.st_student_no)
        stName = findViewById(R.id.st_name)
        stPassword = findViewById(R.id.st_password)
        stConfirm = findViewById(R.id.st_confirm)
        btnRegister = findViewById(R.id.btn_register)
        btnLogin = findViewById(R.id.btn_login)

        btnRegister.setOnClickListener {

            if (validate()) {

                val users = SessionManager.getUsers(this)

                // validate and checks if the user exists
                val existingUser = users.find {
                    it.studentNo == stStudentNo.text.toString()
                            || it.name.equals(stName.text.toString(), ignoreCase = true)
                }

                // if the user exists then the registration won't go through
                if (existingUser != null) {

                    Toast.makeText(
                        this,
                        "Student number or name already exists",
                        Toast.LENGTH_LONG
                    ).show()

                    return@setOnClickListener
                }

                // if user does not exist then it creates a new user
                val user = User(
                    stStudentNo.text.toString(),
                    stName.text.toString(),
                    stPassword.text.toString()
                )

                // saves the user to details in the storage
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

    //validation
    private fun validate(): Boolean {
        if (stStudentNo.text.isEmpty()) {
            stStudentNo.error = "Required"
            return false
        }
        if (stName.text.isEmpty()) {
            stName.error = "Required"
            return false
        }
        if (stPassword.text.isEmpty()) {
            stPassword.error = "Required"
            return false
        }

        if (stPassword.text.toString() != stConfirm.text.toString()) {
            stConfirm.error = "Passwords do not match"
            return false
        }
        return true
    }
}
