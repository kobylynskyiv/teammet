package com.example.teammate.ui.find

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.teammate.R
import com.example.teammate.databinding.FragmentFoundBinding
import com.example.teammate.ui.find.adapter.FoundAdapter
import com.example.teammate.ui.find.adapter.FoundItems
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList


@Suppress("UNCHECKED_CAST")
class FoundGameFragment : Fragment() {

    private var _binding: FragmentFoundBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoundBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ClickableViewAccessibility", "SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = FirebaseFirestore.getInstance()
        val items : ArrayList<FoundItems> = ArrayList()

        val title : TextView = requireActivity().findViewById(R.id.toolbar_title)
        title.text = "Игры"

        val sport = requireArguments().getString("sport")
        val city = requireArguments().getString("city")
        val currentDate = Calendar.getInstance()
        currentDate.add(Calendar.DATE, -1)
        currentDate.set(Calendar.HOUR_OF_DAY, 23)
        currentDate.set(Calendar.MINUTE, 59)
        currentDate.set(Calendar.SECOND, 59)

        db.collection("games")
            .whereGreaterThan("date", Timestamp(Date(currentDate.timeInMillis)))
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {

                        val arrayMembers: ArrayList<String> = ArrayList()
                        val cityItems = document.data["city"] as HashMap<*, *>
                        val stamp : Timestamp = document.data["date"] as Timestamp

                        if(document.data["sport"] == sport && cityItems["city"] == city) {

                            val members : ArrayList<String> = document.data["members"] as ArrayList<String>

                            for(i in members){
                                arrayMembers.add(i)
                            }

                            items.add(FoundItems(cityItems,
                                document.data["comment"].toString(),
                                document.data["creatorId"].toString(),
                                stamp,
                                document.data["gameId"].toString(),
                                document.data["location"].toString(),
                                arrayMembers,
                                document.data["sport"].toString(),
                                document.data["title"].toString()))
                        }
                    }

                    if(task.isComplete){
                        items.sortWith { o1, o2 -> o1.date.compareTo(o2.date) }

                        val adapterFound = FoundAdapter(inflater = layoutInflater, items, view)
                        binding.recyclerView.adapter = adapterFound
                    }


                    if(items.size < 1){
                        binding.img.visibility = View.VISIBLE
                        binding.notFound.visibility = View.VISIBLE
                        binding.createGame.visibility = View.VISIBLE
                    }else{
                        binding.img.visibility = View.GONE
                        binding.notFound.visibility = View.GONE
                        binding.createGame.visibility = View.GONE
                    }

                } else {
                    Log.w("FoundGameFragment", "Error getting documents.", task.exception)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}