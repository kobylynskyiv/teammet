package com.example.teammate.ui.find.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.teammate.R
import com.example.teammate.ui.find.FindGameFragment


class FoundAdapter(private val inflater: LayoutInflater, private val items: ArrayList<FoundItems>) : RecyclerView.Adapter<FoundAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_found_game, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.name.text = item.title
        holder.school.text = item.location
        holder.time.text = item.date
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val school: TextView = view.findViewById(R.id.school)
        val time: TextView = view.findViewById(R.id.time)
    }
}