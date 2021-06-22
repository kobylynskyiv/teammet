package com.example.teammate.ui.city.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.teammate.R
import com.example.teammate.ui.create.CreateGameFragment2
import com.example.teammate.ui.find.FindGameFragment
import com.example.teammate.ui.find.ItemGameEditFragment
import java.util.*
import kotlin.collections.ArrayList


class CityAdapter(private val inflater: LayoutInflater, private val items: ArrayList<CityItems>, private val view: View, private val fragment: Fragment?, private val previousItems: ArrayList<CityItems>) : RecyclerView.Adapter<CityAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_city, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.title.text = item.city
        holder.subTitle.text = item.region

        holder.selected.setOnClickListener {
            view.findNavController().navigateUp()

            when (fragment) {
                is CreateGameFragment2 -> {
                    fragment.selectedCity(item, fragment.view)
                }
                is ItemGameEditFragment -> {
                    fragment.selectedCity(item, fragment.view)
                }
                else -> {
                    (fragment as FindGameFragment).selectedCity(item, fragment.view)
                }
            }
        }

    }

    fun changeText(newText: String){
        items.removeAll(items)
        items.addAll(previousItems)

        if(newText.isNotEmpty()){
            val iterator = items.iterator()
            while(iterator.hasNext()){
                val item = iterator.next()
                if(!item.city.lowercase().startsWith(newText.lowercase())) {
                    iterator.remove()
                }
            }
        }

        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.city)
        val subTitle: TextView = view.findViewById(R.id.descrCity)
        val selected: ConstraintLayout = view.findViewById(R.id.selected)
    }
}