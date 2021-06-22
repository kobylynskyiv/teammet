package com.example.teammate.ui.find

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.teammate.R
import com.example.teammate.databinding.FragmentItemGameBinding
import com.example.teammate.ui.find.adapter.FoundItems
import com.example.teammate.ui.find.adapter.MembersAdapter
import com.example.teammate.utils.CreateUserName
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ItemGameFragment : Fragment() {

    private var _binding: FragmentItemGameBinding? = null
    private val binding get() = _binding!!
    private lateinit var items : FoundItems


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.title){
            "Edit" -> {
                val bundle = Bundle()
                bundle.putSerializable("array", items)
                view?.findNavController()?.navigate(R.id.action_itemGameFragment_to_itemGameEditFragment, bundle)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if(items.creatorId == CreateUserName().loadUUID(requireContext())) {
            inflater.inflate(R.menu.menu_main, menu)
        }
    }

    private fun parserData(date: String, tag : String ): String{
        return if(tag == "DATE"){
            date.substringBefore("\n")
        }else{
            date.substringAfter("\n")
        }
    }

    private fun updateRecyclerView(){
        if(items.members.isNotEmpty()){
            binding.recyclerView.visibility = View.VISIBLE
            binding.tyNotFound.visibility = View.GONE
        }else{
            binding.recyclerView.visibility = View.GONE
            binding.tyNotFound.visibility = View.VISIBLE
        }
    }
    @SuppressLint("ClickableViewAccessibility", "ShowToast", "SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        items = requireArguments().getSerializable("array") as FoundItems
        val title : TextView = requireActivity().findViewById(R.id.toolbar_title)
        title.text = items.title
        if(items.comment.equals("null") || items.comment.equals("")) {
            binding.tyComments.text = "Нет комментариев"
        }else{
            binding.tyComments.text = items.comment
        }
        if(items.members.isNotEmpty()){
            binding.recyclerView.visibility = View.VISIBLE
        }


        for(i in items.members){
            if(CreateUserName().loadUUID(requireContext()) == i){
                binding.goTo.text = "Отказаться"
            }else{
                binding.goTo.text = "Присоединиться"
            }
        }

        binding.goTo.setOnClickListener {
            val db = FirebaseFirestore.getInstance()
            if(binding.goTo.text == "Присоединиться"){
                binding.goTo.text = "Отказаться"
                items.members.add(CreateUserName().loadUUID(requireContext()))
            }else {
                for(i in items.members){
                    if(i == CreateUserName().loadUUID(requireContext())){
                        items.members.remove(i)
                    }
                }
                binding.goTo.text = "Присоединиться"
            }

            db.collection("games").document(items.gameId).set(items)
                .addOnCompleteListener {
                    updateRecyclerView()
                    binding.recyclerView.adapter?.notifyDataSetChanged()
                }

        }

        val convert = SimpleDateFormat("dd MMMM\nHH:mm")


        val date : String = when (items.date?.toDate()?.day) {
            Date().day -> {
                "Сегодня\n${items.date?.toDate()?.hours}:" +
                        when(items.date?.toDate()?.minutes) {
                            0,1,2,3,4,5,6,7,8,9 -> "0${items.date?.toDate()?.minutes}"
                            else -> "${items.date?.toDate()?.minutes}"
                        }
            }
            Date().day + 1 -> {
                "Завтра\n${items.date?.toDate()?.hours}:" +
                        when(items.date?.toDate()?.minutes) {
                            0,1,2,3,4,5,6,7,8,9 -> "0${items.date?.toDate()?.minutes}"
                            else -> "${items.date?.toDate()?.minutes}"
                        }
            }
            else -> {
                convert.format(items.date?.toDate())
            }
        }

        binding.tyDate.text = parserData(date, "DATE")
        binding.tyPlace.text = items.location
        binding.tyCity.text = items.city?.get("city").toString()
        binding.tySports.text = items.sport
        binding.tyTime.text = parserData(date, "TIME")

        if(items.members.isNotEmpty()){
            binding.tyNotFound.visibility = View.GONE
        }

        binding.recyclerView.adapter = MembersAdapter(layoutInflater, items.members)

        binding.tShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "https://teammate-app.ru/?eid=${items.gameId}")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}