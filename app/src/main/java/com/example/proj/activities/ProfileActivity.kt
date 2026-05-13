package com.example.proj.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proj.R
import com.example.proj.adapters.AppointmentAdapter
import com.example.proj.data.DataStore
import com.example.proj.data.SessionManager

class ProfileActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: AppointmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val user = SessionManager.getCurrentUser(this)

        val etName = findViewById<EditText>(R.id.et_name)
        val etPassword = findViewById<EditText>(R.id.et_password)
        val tvStudentNo = findViewById<TextView>(R.id.student_no)

        val profileName = findViewById<TextView>(R.id.tv_profile_name)
        val profileEmail = findViewById<TextView>(R.id.tv_profile_email)

        recycler = findViewById(R.id.recycler_appointments)
        recycler.layoutManager = LinearLayoutManager(this)


        val profileBtn = findViewById<ImageButton>(R.id.imageButton)

        if (DataStore.hasNotification) {
            profileBtn.setColorFilter(Color.RED)
        }

        profileBtn.setOnClickListener {
            DataStore.hasNotification = false
            profileBtn.clearColorFilter()
        }


        val sellerName = intent.getStringExtra("sellerName")
        val sellerEmail = intent.getStringExtra("sellerEmail")
        val isSellerView = sellerName != null

        if (isSellerView) {

            // SELLER PROFILE VIEW

            profileName.text = sellerName
            profileEmail.text = sellerEmail

            etName.visibility = View.GONE
            etPassword.visibility = View.GONE
            tvStudentNo.visibility = View.GONE

        } else {


            //  USER PROFILE

            if (user != null) {
                tvStudentNo.text = user.studentNo
                etName.setText(user.name)
                etPassword.setText(user.password)
            }

            loadAppointments()
        }

        findViewById<Button>(R.id.nav_home).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        findViewById<Button>(R.id.nav_listing).setOnClickListener {
            startActivity(Intent(this, ListingActivity::class.java))
        }

        findViewById<Button>(R.id.nav_add).setOnClickListener {
            startActivity(Intent(this, AddTextbookActivity::class.java))
        }

        findViewById<Button>(R.id.nav_appointment).setOnClickListener {
            startActivity(Intent(this, AppointmentActivity::class.java))
        }

        findViewById<Button>(R.id.btn_logout).setOnClickListener {
            if (!isSellerView) {
                SessionManager.logout(this)
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }


    private fun loadAppointments() {

        val user = SessionManager.getCurrentUser(this)

        val myAppointments = DataStore.appointments.filter {
            it.bookedBy == user?.studentNo || it.bookedWith == user?.studentNo
        }

        adapter = AppointmentAdapter(
            myAppointments,
            user?.studentNo ?: ""
        ) { appt, action ->

            when (action) {
                "ACCEPT" -> appt.status = "ACCEPTED"
                "REJECT" -> appt.status = "REJECTED"
            }

            DataStore.hasNotification = true
            loadAppointments() // refresh
        }

        recycler.adapter = adapter
    }
}