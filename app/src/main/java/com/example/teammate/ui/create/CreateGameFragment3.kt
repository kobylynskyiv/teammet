package com.example.teammate.ui.create

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.teammate.R
import com.example.teammate.databinding.FragmentCreateGame3Binding
import com.example.teammate.ui.find.adapter.FoundItems
import com.google.firebase.firestore.FirebaseFirestore


class CreateGameFragment3 : Fragment() {

    private var _binding: FragmentCreateGame3Binding? = null
    private val binding get() = _binding!!
    private lateinit var items : FoundItems

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateGame3Binding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title : TextView? = activity?.findViewById(R.id.toolbar_title)
        title?.text = "Создание игры. Шаг 3"

        items = requireArguments().getSerializable("array") as FoundItems

        binding.goTo4.setOnClickListener {
            items.comment = binding.tyName.text.toString()
            val db = FirebaseFirestore.getInstance()
            db.collection("games").document(items.gameId).set(items)
            view.findNavController().navigate(R.id.action_createGameFragment3_to_profileFragment)
        }

        binding.constrainLayout.setOnClickListener {
            val imm: InputMethodManager =
                requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            var view = requireActivity().currentFocus
            if (view == null) {
                view = View(activity)
            }

            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}