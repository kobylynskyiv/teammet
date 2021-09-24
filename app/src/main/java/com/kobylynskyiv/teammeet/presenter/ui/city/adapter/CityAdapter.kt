package com.kobylynskyiv.teammeet.presenter.ui.city.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.kobylynskyiv.teammeet.R
import com.kobylynskyiv.teammeet.presenter.data.enties.CityItems


class CityAdapter(private val inflater: LayoutInflater, private val items: ArrayList<CityItems>, private val view: View, private val fragment: Fragment?, private val previousItems: ArrayList<CityItems>) : RecyclerView.Adapter<CityAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_city, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
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