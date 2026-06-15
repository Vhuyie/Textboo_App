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
import java.io.File

class DetailedTextbookAdapter(
    private var list: List<Textbook>
) : RecyclerView.Adapter<DetailedTextbookAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val bookImage: ImageView = view.findViewById(R.id.book_image)
        val name: TextView = view.findViewById(R.id.txt_name)
        val module: TextView = view.findViewById(R.id.txt_module)
        val code: TextView = view.findViewById(R.id.txt_code)
        val isbn: TextView = view.findViewById(R.id.txt_isbn)
        val author: TextView = view.findViewById(R.id.txt_author)
        val price: TextView = view.findViewById(R.id.txt_price)
        val status: TextView = view.findViewById(R.id.txt_status)
        val btnSeller: Button = view.findViewById(R.id.btn_seller)
        val btnSold: Button = view.findViewById(R.id.btn_sold)
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val book = list[position]

        // ================= IMAGE FIX =================
        val path = book.imageUri

        if (!path.isNullOrEmpty()) {
            try {
                val file = File(path)

                if (file.exists()) {
                    holder.bookImage.setImageURI(Uri.fromFile(file))
                } else {
                    holder.bookImage.setImageResource(R.drawable.placeholder_image)
                }

            } catch (e: Exception) {
                holder.bookImage.setImageResource(R.drawable.placeholder_image)
            }
        } else {
            holder.bookImage.setImageResource(R.drawable.placeholder_image)
        }

        // ================= TEXT DATA =================
        holder.name.text = "Textbook name: ${book.name}"
        holder.module.text = "Module name: ${book.module}"
        holder.code.text = "Code: ${book.code}"
        holder.isbn.text = "ISBN: ${book.isbn}"
        holder.author.text = "Author: ${book.author}"
        holder.price.text = "Price: ${book.price}"

        holder.status.text =
            if (book.isSold) "Sold" else "Available"

        val currentUser = DataStore.currentUser

        // ================= SELLER BUTTON =================
        holder.btnSeller.setOnClickListener {
            val intent = Intent(holder.itemView.context, SellerDetailsActivity::class.java)
            intent.putExtra("sellerName", book.seller.name)
            holder.itemView.context.startActivity(intent)
        }

        // ================= OWNERSHIP BUTTON =================
        if (currentUser?.name == book.seller.name) {
            holder.btnSold.visibility = View.VISIBLE
        } else {
            holder.btnSold.visibility = View.GONE
        }

        holder.btnSold.text =
            if (book.isSold) "Mark as Available"
            else "Mark as Sold"

        holder.btnSold.setOnClickListener {

            book.isSold = !book.isSold

            PrefsManager.saveBooks(
                holder.itemView.context,
                list
            )

            holder.status.text =
                if (book.isSold) "Sold"
                else "Available"

            holder.btnSold.text =
                if (book.isSold) "Mark as Available"
                else "Mark as Sold"
        }
    }
}