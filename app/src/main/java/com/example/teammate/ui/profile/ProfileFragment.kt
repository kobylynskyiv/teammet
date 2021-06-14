package com.example.teammate.ui.profile

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.teammate.R
import com.example.teammate.databinding.FragmentProfileBinding
import com.example.teammate.utils.CreateUserName
import com.google.android.material.snackbar.Snackbar

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        val title : TextView? = activity?.findViewById(R.id.toolbar_title)
        title?.text = CreateUserName().checkUser()

        binding.findGame.setOnClickListener{
            findNavController().navigate(R.id.action_profile_to_findGame)
        }

        binding.createGame.setOnClickListener{
            Snackbar.make(binding.createGame, "Create Game", Snackbar.LENGTH_SHORT).show()
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