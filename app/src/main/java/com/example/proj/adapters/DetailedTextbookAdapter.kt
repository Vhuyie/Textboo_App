package com.example.proj.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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



        val name = view.findViewById<TextView>(R.id.txt_name)
        val module = view.findViewById<TextView>(R.id.txt_module)
        val code = view.findViewById<TextView>(R.id.txt_code)
        val isbn = view.findViewById<TextView>(R.id.txt_isbn)
        val author = view.findViewById<TextView>(R.id.txt_author)
        val price = view.findViewById<TextView>(R.id.txt_price)
        val status = view.findViewById<TextView>(R.id.txt_status)
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

//list of the textbook details that will be displayed
        holder.name.text = "Textbook name: " + book.name

        holder.module.text = "Module name: " + book.module

        holder.code.text = "Code: " + book.code

        holder.isbn.text = "ISBN: " + book.isbn

        holder.author.text = "Author: " + book.author

        holder.price.text = "Price: " + book.price
        holder.status.text =
            if (book.isSold) "Sold" else "Available"


        holder.btnSeller.setOnClickListener {

            val intent = Intent(holder.itemView.context, SellerDetailsActivity::class.java)

            intent.putExtra("sellerName", book.seller.name)

            holder.itemView.context.startActivity(intent)
        }

        // this is the sold button that will be displayed only on the owner of the book
        if (currentUser?.name == book.seller.name) {
            holder.btnSold.visibility = View.VISIBLE
        } else {
            holder.btnSold.visibility = View.GONE
        }

        // control button to mark it as "Mark as Available" else "Mark as Sold"
        holder.btnSold.text =
            if (book.isSold)
                "Mark as Available"
            else
                "Mark as Sold"

        //on click listener for the button
        holder.btnSold.setOnClickListener {

            // Toggle status
            book.isSold = !book.isSold

            // Save the updated list
            PrefsManager.saveBooks(
                holder.itemView.context,
                list
            )

            // Update the status
            holder.status.text =
                if (book.isSold)
                    "Sold"
                else
                    "Available"

            // Update button
            holder.btnSold.text =
                if (book.isSold)
                    "Mark as Available"
                else
                    "Mark as Sold"
        }
    }
    }