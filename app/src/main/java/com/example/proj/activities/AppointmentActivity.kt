package com.example.proj.activities

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.proj.R
import com.example.proj.data.DataStore
import com.example.proj.data.SessionManager
import com.example.proj.models.Appointment
import java.util.*

class AppointmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment)

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

        val profileBtn = findViewById<ImageButton>(R.id.imageButton)


        if (DataStore.hasNotification) {
            profileBtn.setColorFilter(android.graphics.Color.RED)
        }


        profileBtn.setOnClickListener {
            DataStore.hasNotification = false
            profileBtn.clearColorFilter() // remove red dot effect
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        val autoUser = findViewById<AutoCompleteTextView>(R.id.auto_user)
        val etDate = findViewById<EditText>(R.id.et_date)
        val etTime = findViewById<EditText>(R.id.et_time)
        val etNotes = findViewById<EditText>(R.id.et_notes)
        val btnSubmit = findViewById<Button>(R.id.btn_submit)

        val users = SessionManager.getUsers(this)

        autoUser.setAdapter(
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,
                users.map { "${it.name} (${it.studentNo})" })
        )

        etDate.setOnClickListener {
            val c = Calendar.getInstance()
            DatePickerDialog(this, { _, y, m, d ->
                etDate.setText("$d/${m + 1}/$y")
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show()
        }

        etTime.setOnClickListener {
            val c = Calendar.getInstance()
            TimePickerDialog(this, { _, h, m ->
                etTime.setText(String.format("%02d:%02d", h, m))
            }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show()
        }

        btnSubmit.setOnClickListener {

            val selected = autoUser.text.toString()
            val date = etDate.text.toString()
            val time = etTime.text.toString()
            val notes = etNotes.text.toString()

            val studentNo = selected
                .substringAfter("(", "")
                .substringBefore(")", "")
                .trim()

            val receiver = users.find {
                it.studentNo == studentNo
            }

            val sender = SessionManager.getCurrentUser(this)

            if (receiver == null || sender == null) {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val appt = Appointment(
                bookedBy = sender.studentNo,
                bookedByName = sender.name,
                bookedWith = receiver.studentNo,
                bookedWithName = receiver.name,
                date = date,
                time = time,
                notes = notes,
                status = "PENDING"
            )

            DataStore.appointments.add(appt)
            DataStore.hasNotification = true

            Toast.makeText(this, "Request sent", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}