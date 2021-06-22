package com.example.teammate.ui.find.adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.teammate.R
import java.text.SimpleDateFormat
import java.util.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class FoundAdapter(private val inflater: LayoutInflater, private val items: ArrayList<FoundItems>, private val view : View) : RecyclerView.Adapter<FoundAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_found_game, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("ResourceAsColor", "SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.name.text = item.title
        holder.school.text = item.location

        val convert = SimpleDateFormat("dd MMMM\nHH:mm")
        val date : String = when (item.date?.toDate()?.day) {
            Date().day -> {
                "Сегодня\n${item.date?.toDate()?.hours}:" +
                        when(item.date?.toDate()?.minutes) {
                            0,1,2,3,4,5,6,7,8,9 -> "0${item.date?.toDate()?.minutes}"
                            else -> "${item.date?.toDate()?.minutes}"
                        }
            }
            Date().day + 1 -> {
                "Завтра\n${item.date?.toDate()?.hours}:" +
                        when(item.date?.toDate()?.minutes) {
                            0,1,2,3,4,5,6,7,8,9 -> "0${item.date?.toDate()?.minutes}"
                            else -> "${item.date?.toDate()?.minutes}"
                        }
            }
            else -> {
                convert.format(item.date?.toDate())
            }
        }


        holder.time.text = date
        holder.selected.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("array", item)
            view.findNavController().navigate(R.id.action_foundGameFragment_to_itemGameFragment, bundle)
        }


    }


    fun sort() {
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val school: TextView = view.findViewById(R.id.school)
        val time: TextView = view.findViewById(R.id.time)
        val selected: ConstraintLayout = view.findViewById(R.id.selected)
    }
}