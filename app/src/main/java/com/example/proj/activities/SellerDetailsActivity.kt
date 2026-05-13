package com.example.proj.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proj.R
import com.example.proj.data.DataStore

class SellerDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_seller_details)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->

            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        // ----------------------------
        // TEXTVIEW
        // ----------------------------
        val tvSellerName =
            findViewById<TextView>(R.id.tv_seller_name)

        // ----------------------------
        // GET SELLER NAME
        // ----------------------------
        val sellerName =
            intent.getStringExtra("sellerName")
                ?: "Unknown Seller"

        // ----------------------------
        // DISPLAY SELLER NAME
        // ----------------------------
        tvSellerName.text = sellerName

        // ----------------------------
        // NAVIGATION
        // ----------------------------

        findViewById<Button>(R.id.nav_home)
            .setOnClickListener {

                startActivity(
                    Intent(this, MainActivity::class.java)
                )
            }

        findViewById<Button>(R.id.nav_listing)
            .setOnClickListener {
                startActivity(Intent(this, ListingActivity::class.java))
            }

        findViewById<Button>(R.id.nav_add)
            .setOnClickListener { startActivity(Intent(this, AddTextbookActivity::class.java))
            }

        findViewById<Button>(R.id.nav_appointment)
            .setOnClickListener { startActivity(Intent(this, AppointmentActivity::class.java))
            }

        val profileBtn =
            findViewById<ImageButton>(R.id.imageButton)

        if (DataStore.hasNotification) {

            profileBtn.setColorFilter(
                android.graphics.Color.RED
            )
        }

        profileBtn.setOnClickListener { DataStore.hasNotification = false
            profileBtn.clearColorFilter()
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}