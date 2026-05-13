package com.example.proj.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proj.R
import com.example.proj.data.DataStore
import com.example.proj.data.PrefsManager
import com.example.proj.models.Textbook

class AddTextbookActivity : AppCompatActivity() {

    //private var selectedImageUri: Uri? = null

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

        val btnAdd = findViewById<Button>(R.id.btn_add)
        val btnUpload = findViewById<Button>(R.id.btn_upload_image)

       // btnUpload.setOnClickListener {
         //   val intent = Intent(Intent.ACTION_PICK)
          //  intent.type = "image/*"
           // startActivityForResult(intent, 100)
        //}

        btnAdd.setOnClickListener {

            // ✅ CHECK IMAGE FIRST
           // if (selectedImageUri == null) {

             //   Toast.makeText(
             //       this,
                   // "Please select image",
             //       Toast.LENGTH_SHORT
             //   ).show()

              //  return@setOnClickListener
            //}

            val user = DataStore.currentUser

            // ✅ CHECK USER
            if (user == null) {

                Toast.makeText(
                    this,
                    "Please login again",
                    Toast.LENGTH_SHORT
                ).show()

                startActivity(
                    Intent(this, LoginActivity::class.java)
                )

                finish()

                return@setOnClickListener
            }

            // ✅ CREATE BOOK
            val book = Textbook(

                etName.text.toString(),
                etModule.text.toString(),
                etCode.text.toString(),
                etIsbn.text.toString(),
                etAuthor.text.toString(),
                etPrice.text.toString(),

                // IMAGE URI
               // selectedImageUri.toString(),

                user
            )

            // ✅ LOAD SAVED BOOKS
            val list =
                PrefsManager.getBooks(this).toMutableList()

            // ✅ ADD BOOK
            list.add(book)

            // ✅ SAVE BOOKS
            PrefsManager.saveBooks(this, list)

            Toast.makeText(
                this,
                "Book Added",
                Toast.LENGTH_SHORT
            ).show()

            startActivity(
                Intent(this, MainActivity::class.java)
            )

            finish()
        }

        findViewById<Button>(R.id.nav_listing).setOnClickListener {
            startActivity(Intent(this, ListingActivity::class.java))
        }

        findViewById<Button>(R.id.nav_appointment).setOnClickListener {
            startActivity(Intent(this, AppointmentActivity::class.java))
        }
        findViewById<Button>(R.id.nav_home).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        val profileBtn = findViewById<ImageButton>(R.id.imageButton)
    }

    //override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
       // super.onActivityResult(requestCode, resultCode, data)

        //if (requestCode == 100 && resultCode == RESULT_OK) {
           // selectedImageUri = data?.data
            //Toast.makeText(this, "Image Selected", Toast.LENGTH_SHORT).show()
        //}
   // }
}