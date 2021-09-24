package com.kobylynskyiv.teammeet.presenter.ui.find

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kobylynskyiv.teammeet.R
import com.kobylynskyiv.teammeet.databinding.FragmentFindBinding
import com.kobylynskyiv.teammeet.domain.viewModel.FindGameFragmentViewModel
import com.kobylynskyiv.teammeet.domain.viewModel.MainActivityViewModel
import com.kobylynskyiv.teammeet.presenter.ui.find.adapter.type.TypeGameAdapter
import com.kobylynskyiv.teammeet.presenter.utils.FragmentUtils

class FindGameFragment : Fragment(), FragmentUtils {

    private val binding: FragmentFindBinding by lazy {  FragmentFindBinding.inflate(layoutInflater) }
    private val findGameModel: FindGameFragmentViewModel by viewModels()
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

        findGameModel.data.observe(viewLifecycleOwner, {
            binding.recyclerView.adapter = TypeGameAdapter(it, viewLifecycleOwner)
        })

        binding.back.setOnClickListener {
            onBackPressed(activity)
        }
        binding.findGame.setOnClickListener {
            findNavController().navigate(R.id.action_findGame_to_foundGame)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_find, menu)
    }

    override fun getTitle(): String {
        return resources.getString(R.string.find_game)
    }
}