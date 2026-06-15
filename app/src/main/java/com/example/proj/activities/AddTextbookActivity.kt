package com.example.proj.activities

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proj.data.DataStore
import com.example.proj.data.PrefsManager
import com.example.proj.models.Textbook
import com.example.proj.R
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts

class AddTextbookActivity : AppCompatActivity() {

    private var selectedImageUri: Uri? = null

    private val imagePicker =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                selectedImageUri = uri
                findViewById<ImageView>(R.id.book_image_preview).setImageURI(uri)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_textbook)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etName = findViewById<EditText>(R.id.textbook_name)
        val etModule = findViewById<EditText>(R.id.module)
        val etCode = findViewById<EditText>(R.id.code_module)
        val etIsbn = findViewById<EditText>(R.id.isb_number)
        val etAuthor = findViewById<EditText>(R.id.book_author)
        val etPrice = findViewById<EditText>(R.id.book_price)
        val etDescription = findViewById<EditText>(R.id.book_description)
        val etEdition = findViewById<EditText>(R.id.book_edition)
        val btnUpload = findViewById<Button>(R.id.btn_upload_image)
        val btnAdd = findViewById<Button>(R.id.btn_add)

        btnUpload.setOnClickListener {
            imagePicker.launch("image/*")
        }
        btnAdd.setOnClickListener {

            if (etName.text.isEmpty() ||
                etPrice.text.isEmpty() ||
                selectedImageUri == null
            ) {
                Toast.makeText(this, "Fill all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = DataStore.currentUser

            if (user == null) {
                Toast.makeText(this, "Please login again", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val book = Textbook(
                etName.text.toString(),
                etModule.text.toString(),
                etCode.text.toString(),
                etIsbn.text.toString(),
                etAuthor.text.toString(),
                etPrice.text.toString(),
                etDescription.text.toString(),
                etEdition.text.toString(),
                selectedImageUri?.toString() ?: "",
                user
            )

            if (user == null) {
                Toast.makeText(this, "Please login again", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val list = PrefsManager.getBooks(this).toMutableList()
            list.add(book)
            PrefsManager.saveBooks(this, list)

            Toast.makeText(this, "Book Added", Toast.LENGTH_SHORT).show()

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        findViewById<Button>(R.id.nav_listing).setOnClickListener {
            startActivity(Intent(this, ListingActivity::class.java))
        }

        findViewById<Button>(R.id.nav_home).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
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
            profileBtn.clearColorFilter() // remove red dot effect after being clicked
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}