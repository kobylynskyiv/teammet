package com.example.teammate.ui.find.adapter


import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.teammate.R
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MembersAdapter(private val inflater: LayoutInflater, private val items: ArrayList<String>) : RecyclerView.Adapter<MembersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_members, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val db = FirebaseFirestore.getInstance()
        println("here")
        db.collection("users")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        if(document.data["userId"] == item){
                            holder.user.text = document.data["nickname"].toString()
                        }
                    }

                    if(holder.user.text.equals("")){
                        holder.user.text = "Не найдено"
                    }
                } else {
                    Log.w("MembersAdapter", "Error getting documents.", task.exception)
                }
            }



    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        val user: TextView = view.findViewById(R.id.user)
    }
}