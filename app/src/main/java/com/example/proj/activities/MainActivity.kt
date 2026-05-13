package com.example.proj.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proj.R
import com.example.proj.adapters.TextbookAdapter
import com.example.proj.data.DataStore
import com.example.proj.data.PrefsManager
import com.example.proj.models.Textbook
import android.widget.SearchView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TextbookAdapter
    private lateinit var textbookList: MutableList<Textbook>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->

            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }


        recyclerView =
            findViewById(R.id.recycler_books)

        recyclerView.layoutManager =
            LinearLayoutManager(this)

        // ----------------------------
        // ADAPTER
        // ----------------------------
        textbookList = PrefsManager.getBooks(this)

        adapter = TextbookAdapter(textbookList) { book ->

            val intent =
                Intent(this, ListingActivity::class.java)

            startActivity(intent)
        }

        recyclerView.adapter = adapter

        val searchView =
            findViewById<SearchView>(R.id.searchView)

        searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    val filteredList = textbookList.filter {

                        it.name.contains(
                            newText ?: "",
                            ignoreCase = true
                        )

                                ||

                                it.module.contains(
                                    newText ?: "",
                                    ignoreCase = true
                                )

                                ||

                                it.code.contains(
                                    newText ?: "",
                                    ignoreCase = true
                                )
                    }

                    adapter = TextbookAdapter(filteredList) { book ->

                        val intent =
                            Intent(
                                this@MainActivity,
                                ListingActivity::class.java
                            )

                        startActivity(intent)
                    }

                    recyclerView.adapter = adapter

                    return true
                }
            }
        )

        findViewById<Button>(R.id.nav_home)
            .setOnClickListener { startActivity(Intent(this, MainActivity::class.java))
            }

        findViewById<Button>(R.id.nav_listing)
            .setOnClickListener { startActivity(Intent(this, ListingActivity::class.java))
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

        profileBtn.setOnClickListener {

            DataStore.hasNotification = false

            profileBtn.clearColorFilter()

            startActivity(
                Intent(this, ProfileActivity::class.java)
            )
        }
    }

    override fun onResume() {
        super.onResume()

        recyclerView.adapter?.notifyDataSetChanged()
    }
}