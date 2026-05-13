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

class LoginActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnGoLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        etUsername = findViewById(R.id.et_username)
        etPassword = findViewById(R.id.et_password)

        btnLogin = findViewById(R.id.btn_login)
        btnGoLogin = findViewById(R.id.btn_go_login)

        btnLogin.setOnClickListener {

            val studentNo = etUsername.text.toString()
            val password = etPassword.text.toString()

            val users = SessionManager.getUsers(this)

            val user = users.find {
                it.studentNo == studentNo && it.password == password
            }

            if (user != null) {

                DataStore.currentUser = user

                SessionManager.saveCurrentUser(this, user)

                Toast.makeText(this, "Login Successful",
                    Toast.LENGTH_SHORT
                ).show()

                //startActivity(Intent(this, MainActivity::class.java))
                //finish()
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)

            } else {

                Toast.makeText(
                    this,
                    "Invalid student number or password",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        btnGoLogin.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}