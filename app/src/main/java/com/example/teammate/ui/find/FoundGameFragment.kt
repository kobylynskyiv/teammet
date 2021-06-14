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
import com.example.teammate.ui.find.adapter.MembersGames
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


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
        val arrayMembers: ArrayList<MembersGames> = ArrayList()

        val title : TextView = requireActivity().findViewById(R.id.toolbar_title)
        title.text = "Игры"

        val sport = requireArguments().getString("sport")
        val city = requireArguments().getString("city")

        db.collection("games")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {

                        val cityItems = document.data["city"] as HashMap<*, *>
                        val stamp : Timestamp = document.data["date"] as Timestamp
                        val convert = SimpleDateFormat("dd MMMM \n HH:mm")
                        if(document.data["sport"] == sport && cityItems["city"] == city ) {

                            arrayMembers.add(MembersGames("adsads"))

                            val date = when (stamp.toDate().day) {
                                Date().day -> {
                                    "Сегодня \n ${stamp.toDate().hours}:${stamp.toDate().minutes}0 "
                                }
                                Date().day + 1 -> {
                                    "Завтра \n ${stamp.toDate().hours}:${stamp.toDate().minutes}0 "
                                }
                                else -> {
                                    convert.format(stamp.toDate())
                                }
                            }
                            items.add(FoundItems(cityItems,
                                document.data["comment"].toString(),
                                document.data["createdId"].toString(),
                                date,
                                document.data["gameId"].toString(),
                                document.data["location"].toString(),
                                arrayMembers,
                                document.data["sport"].toString(),
                                document.data["title"].toString()))
                            binding.recyclerView.adapter = FoundAdapter(inflater = layoutInflater, items)
                        }
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