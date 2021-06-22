package com.example.teammate.ui.profile

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.teammate.R
import com.example.teammate.databinding.FragmentProfileBinding
import com.example.teammate.ui.find.adapter.FoundItems
import com.example.teammate.ui.find.adapter.GameAdapter
import com.example.teammate.utils.CreateUserName
import com.example.teammate.utils.User
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var items: ArrayList<FoundItems> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.title){
            "Edit" -> {
                val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
                val inflater = this.layoutInflater
                val dialogView: View = inflater.inflate(R.layout.item_profile_edit_nickname, null)
                dialogBuilder.setView(dialogView)
                val nickname = dialogView.findViewById<EditText>(R.id.tySport)
                val db = FirebaseFirestore.getInstance()
                db.collection("users")
                    .get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            for (document in task.result!!) {
                                if (document.data["userId"] == CreateUserName().loadUUID(requireContext())) {
                                    nickname.setText(document.data["nickname"] as String)
                                }
                            }
                        }
                    }

                dialogBuilder.setPositiveButton("Изменить") { dialog, _ ->

                    val user = User("", nickname.text.toString(), CreateUserName().loadUUID(requireContext()))

                    db
                        .collection("users")
                        .document(CreateUserName().loadUUID(requireContext()))
                        .set(user)
                        .addOnSuccessListener {
                            val title : TextView? = activity?.findViewById(R.id.toolbar_title)
                            title?.text = nickname.text.toString()
                        }

                    dialog.dismiss()
                }
                dialogBuilder.setNegativeButton("Отменить") { dialog, _ ->
                    dialog.dismiss()
                }
                dialogBuilder.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        val title : TextView? = activity?.findViewById(R.id.toolbar_title)

        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        if (document.data["userId"] == CreateUserName().loadUUID(requireContext())) {
                            title?.text = document.data["nickname"] as String
                        }
                    }
                }
            }
        if(items.isNotEmpty()){
            items.clear()
        }


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
                        val members : List<String> = document.data["members"] as List<String>

                        val arrayMembers: ArrayList<String> = ArrayList()
                        val cityItems = document.data["city"] as HashMap<*, *>
                        val stamp : Timestamp = document.data["date"] as Timestamp

                        if (document.data["creatorId"] == CreateUserName().loadUUID(requireContext())) {

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
                    if(items.isEmpty()){
                       binding.myGame.visibility = View.GONE
                       binding.img.visibility = View.VISIBLE
                       binding.notFound.visibility = View.VISIBLE
                    }else{
                        binding.myGame.visibility = View.VISIBLE
                        binding.img.visibility = View.GONE
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.notFound.visibility = View.GONE
                    }

                    binding.recyclerView.adapter = GameAdapter(layoutInflater, items, view)
                } else {
                    Log.w("MembersAdapter", "Error getting documents.", task.exception)
                }
            }


        binding.findGame.setOnClickListener{
            findNavController().navigate(R.id.action_profile_to_findGame)
        }

        binding.createGame.setOnClickListener{
            findNavController().navigate(R.id.action_profileFragment_to_createGameFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}