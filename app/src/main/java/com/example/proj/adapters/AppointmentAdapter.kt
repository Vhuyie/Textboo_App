package com.example.proj.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.proj.R
import com.example.proj.models.Appointment

class AppointmentAdapter(
    private val list: List<Appointment>,
    private val currentUser: String,
    private val onAction: (Appointment, String) -> Unit
) : RecyclerView.Adapter<AppointmentAdapter.VH>() {

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tvTitle)
        val details: TextView = view.findViewById(R.id.tvDetails)
        val status: TextView = view.findViewById(R.id.tvStatus)
        val accept: Button = view.findViewById(R.id.btnAccept)
        val reject: Button = view.findViewById(R.id.btnReject)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_appointment, parent, false)
        return VH(v)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: VH, position: Int) {

        val appt = list[position]

        holder.title.text = "${appt.bookedByName} → ${appt.bookedWithName}"
        holder.details.text = "${appt.date} ${appt.time}\n${appt.notes}"
        holder.status.text = "Status: ${appt.status}"

        val isReceiver = appt.bookedWith == currentUser

        if (isReceiver && appt.status == "PENDING") {
            holder.accept.visibility = View.VISIBLE
            holder.reject.visibility = View.VISIBLE
        } else {
            holder.accept.visibility = View.GONE
            holder.reject.visibility = View.GONE
        }

        holder.accept.setOnClickListener {
            onAction(appt, "ACCEPT")
        }

        holder.reject.setOnClickListener {
            onAction(appt, "REJECT")
        }
    }
}