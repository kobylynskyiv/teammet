package com.kobylynskyiv.teammeet.data.db

import com.google.firebase.firestore.FirebaseFirestore
import com.kobylynskyiv.teammeet.data.db.listeners.DatabaseOnCanceledListener
import com.kobylynskyiv.teammeet.data.db.listeners.DatabaseOnFailureListener
import com.kobylynskyiv.teammeet.data.db.listeners.OnCompleteLoadDatabase
import com.kobylynskyiv.teammeet.domain.enties.FoundGameItem

class Database() {

    private val database: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    fun getValues(
        path: String,
        type: String,
        value: String,
        listener: OnCompleteLoadDatabase
    ){
        database
            .collection(path)
            .whereEqualTo(type, value)
            .get()
            .addOnCompleteListener {
                try {
                    val finalItems : ArrayList<FoundGameItem> = ArrayList()
                    if (it.isSuccessful) {
                        for (document in it.result!!) {
                            finalItems.add(
                                FoundGameItem(
                                    document.getString("id").toString(),
                                    document.getString("image").toString(),
                                    document.getString("name").toString(),
                                    document.getString("type").toString()
                                )
                            )
                        }
                        listener.onComplete(finalItems, Status.SUCCESS)
                    }
                }catch (e: Exception){

                }
            }
            .addOnCanceledListener(DatabaseOnCanceledListener(listener))
            .addOnFailureListener(DatabaseOnFailureListener(listener))
    }
    enum class Status { SUCCESS, ERROR, NOT_FOUND }
}
