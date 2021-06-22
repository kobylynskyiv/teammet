package com.example.teammate.ui.find.adapter


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.teammate.R
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class GameAdapter(private val inflater: LayoutInflater, private val items: ArrayList<FoundItems>, private val view: View) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_games, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("ResourceAsColor", "SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.name.text = item.title
        holder.place.text = item.location

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


        holder.timeDate.text = parserData(date, "DATE")
        holder.time.text = parserData(date, "TIME")

        holder.selected.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("array", item)
            view.findNavController().navigate(R.id.action_profileFragment_to_itemGameFragment, bundle)
        }
    }


    private fun parserData(date: String, tag : String ): String{
        return if(tag == "DATE"){
            date.substringBefore("\n")
        }else{
            date.substringAfter("\n")
        }
    }


    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val place: TextView = view.findViewById(R.id.place)
        val timeDate: TextView = view.findViewById(R.id.timeDate)
        val time: TextView = view.findViewById(R.id.time)
        val selected: ConstraintLayout = view.findViewById(R.id.selected)
    }
}