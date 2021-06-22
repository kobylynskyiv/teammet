package com.example.teammate.ui.create

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.NumberPicker
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.teammate.R
import com.example.teammate.databinding.FragmentCreateGame1Binding
import com.example.teammate.ui.find.adapter.FoundItems
import com.example.teammate.ui.find.adapter.MembersGames
import com.example.teammate.utils.CreateUserName
import com.google.firebase.Timestamp
import java.util.*
import kotlin.collections.ArrayList

class CreateGameFragment : Fragment() {

    private var _binding: FragmentCreateGame1Binding? = null
    private val binding get() = _binding!!
    private lateinit var item : FoundItems
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateGame1Binding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SimpleDateFormat", "ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title : TextView? = activity?.findViewById(R.id.toolbar_title)
        title?.text = "Создание игры. Шаг 1"

        binding.tySport.setOnClickListener {
            val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            val inflater = this.layoutInflater
            val dialogView: View = inflater.inflate(R.layout.item_sport, null)
            dialogBuilder.setView(dialogView)
            val cats = arrayOf("Футбол", "Волейбол", "Баскетбол", "Хоккей")
            val numberPicker = dialogView.findViewById<View>(R.id.numberPicker) as NumberPicker
            numberPicker.minValue = 0
            numberPicker.maxValue = 3
            numberPicker.displayedValues = cats

            dialogBuilder.setPositiveButton("Выбрать") { dialog, _ ->
                binding.tySport.setText(cats[numberPicker.value])
                dialog.dismiss()
            }
            dialogBuilder.show()
        }

        binding.goTo2.setOnClickListener {
            if(binding.tyName.text?.isNotEmpty() == true &&
                binding.tySport.text?.isNotEmpty() == true)
            {
                binding.error.visibility = View.GONE
                val emptyMembers : ArrayList<String> = ArrayList()
                emptyMembers.add(CreateUserName().loadUUID(requireContext()))

                item = FoundItems(
                    null,
                    "",
                    CreateUserName().loadUUID(requireContext()),
                    Timestamp(Date()),
                    CreateUserName().generateUUID(),
                    "",
                    emptyMembers,
                    binding.tySport.text.toString(),
                    binding.tyName.text.toString(),
                )

                val bundle = Bundle()
                bundle.putSerializable("array", item)
                view.findNavController()
                    .navigate(R.id.action_createGameFragment_to_createGameFragment2, bundle)
            }
            else {
                binding.error.visibility = View.VISIBLE
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}