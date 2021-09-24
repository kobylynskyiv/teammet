package com.kobylynskyiv.teammeet.presenter.ui.profile

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kobylynskyiv.teammeet.R
import com.kobylynskyiv.teammeet.databinding.FragmentProfileBinding
import com.kobylynskyiv.teammeet.domain.viewModel.MainActivityViewModel
import com.kobylynskyiv.teammeet.presenter.utils.FragmentUtils

class ProfileFragment : Fragment(), FragmentUtils {

    private val binding: FragmentProfileBinding by lazy { FragmentProfileBinding.inflate(layoutInflater) }
    private val activityMainModel: MainActivityViewModel by viewModels({ requireActivity() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        activityMainModel.updateActionBarTitle(getTitle())

        binding.findGame.setOnClickListener{
            findNavController().navigate(R.id.action_profile_to_findGame)
        }

        binding.createGame.setOnClickListener{
            findNavController().navigate(R.id.action_profileFragment_to_createGameFragment)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.edit -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun getTitle(): String {
        return resources.getString(R.string.profile_main)
    }
}