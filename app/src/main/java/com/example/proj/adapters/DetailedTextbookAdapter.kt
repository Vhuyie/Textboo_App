package com.example.proj.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proj.R
import com.example.proj.activities.SellerDetailsActivity
import com.example.proj.data.DataStore
import com.example.proj.data.PrefsManager
import com.example.proj.models.Textbook

class DetailedTextbookAdapter(
    private var list: List<Textbook>
) : RecyclerView.Adapter<DetailedTextbookAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val image = view.findViewById<ImageView>(R.id.img_book)

        val name = view.findViewById<TextView>(R.id.tv_name)
        val module = view.findViewById<TextView>(R.id.tv_module)
        val code = view.findViewById<TextView>(R.id.tv_code)
        val isbn = view.findViewById<TextView>(R.id.tv_isbn)
        val author = view.findViewById<TextView>(R.id.tv_author)
        val price = view.findViewById<TextView>(R.id.tv_price)
        val status = view.findViewById<TextView>(R.id.tv_status)
        val btnSeller = view.findViewById<Button>(R.id.btn_seller)
        val btnSold = view.findViewById<Button>(R.id.btn_sold)
    }

    fun updateList(newList: List<Textbook>) {
        list = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_listing_detail, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int) {

        val book = list[position]
        val currentUser = DataStore.currentUser

        holder.name.text = book.name
        holder.module.text = book.module
        holder.code.text = book.code
        holder.isbn.text = book.isbn
        holder.author.text = book.author
        holder.price.text = book.price
        holder.status.text =
            if (book.isSold) "Sold" else "Available"


        if (book.imageUri.isNotEmpty()) {
            holder.image.setImageURI(Uri.parse(book.imageUri))
        }else {
            holder.image.setImageResource(R.drawable.profile)
        }

        holder.btnSeller.setOnClickListener {

            val intent = Intent(
                holder.itemView.context,
                SellerDetailsActivity::class.java
            )

            intent.putExtra("sellerName", book.seller.name)

            holder.itemView.context.startActivity(intent)
        }

        // =========================
        // SHOW SOLD BUTTON ONLY
        // FOR BOOK OWNER
        // =========================
        if (currentUser?.name == book.seller.name) {

            holder.btnSold.visibility = View.VISIBLE

        } else {

            holder.btnSold.visibility = View.GONE
        }

        // =========================
        // BUTTON TEXT
        // =========================
        holder.btnSold.text =
            if (book.isSold)
                "Mark as Available"
            else
                "Mark as Sold"

        // =========================
        // CHANGE STATUS
        // =========================
        holder.btnSold.setOnClickListener {

            // TOGGLE STATUS
            book.isSold = !book.isSold

            // SAVE UPDATED LIST
            PrefsManager.saveBooks(
                holder.itemView.context,
                list
            )

            // UPDATE STATUS TEXT
            holder.status.text =
                if (book.isSold)
                    "Sold"
                else
                    "Available"

            // UPDATE BUTTON TEXT
            holder.btnSold.text =
                if (book.isSold)
                    "Mark as Available"
                else
                    "Mark as Sold"
        }
    }
    }