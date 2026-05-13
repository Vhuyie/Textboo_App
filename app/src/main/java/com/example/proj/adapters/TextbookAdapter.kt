package com.example.proj.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proj.R
import com.example.proj.models.Textbook

class TextbookAdapter(
    private val list: List<Textbook>,
    private val onClick: (Textbook) -> Unit
) : RecyclerView.Adapter<TextbookAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val name =
            view.findViewById<TextView>(R.id.tv_name)

        val module =
            view.findViewById<TextView>(R.id.txt_module)

        val code =
            view.findViewById<TextView>(R.id.txt_code)

        val status =
            view.findViewById<TextView>(R.id.txt_status)

        val btnView =
            view.findViewById<Button>(R.id.btn_view)


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_textbook,
                parent,
                false
            )

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val book = list[position]

        // BOOK DETAILS
        holder.name.text = book.name

        holder.module.text = book.module

        holder.code.text = book.code

        holder.status.text =
            if (book.isSold)
                "Sold"
            else
                "Available"


        // BUTTON TEXT
        holder.btnView.text =
            "View More Details"

        // BUTTON CLICK
        holder.btnView.setOnClickListener {

            onClick(book)
        }
    }
}