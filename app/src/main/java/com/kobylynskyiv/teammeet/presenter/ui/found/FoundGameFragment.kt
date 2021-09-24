package com.kobylynskyiv.teammeet.presenter.ui.found

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.kobylynskyiv.teammeet.R
import com.kobylynskyiv.teammeet.data.db.Database
import com.kobylynskyiv.teammeet.databinding.FragmentFoundBinding
import com.kobylynskyiv.teammeet.domain.enties.FoundGameItem
import com.kobylynskyiv.teammeet.domain.viewModel.FoundGameViewModel
import com.kobylynskyiv.teammeet.domain.viewModel.MainActivityViewModel
import com.kobylynskyiv.teammeet.presenter.ui.found.adapter.FoundGameAdapter
import com.kobylynskyiv.teammeet.presenter.utils.FragmentUtils


class FoundGameFragment : Fragment(), FragmentUtils {

    private val binding: FragmentFoundBinding by lazy { FragmentFoundBinding.inflate(layoutInflater) }
    private val activityMainModel: MainActivityViewModel by viewModels({ requireActivity() })
    private val foundGameViewModel: FoundGameViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        foundGameViewModel.loadData()
        activityMainModel.updateActionBarTitle(getTitle())
        foundGameViewModel.data.observe(viewLifecycleOwner, {
            with(it) {
                if (items != null && (status != Database.Status.ERROR || status != Database.Status.NOT_FOUND)) {
                    binding.recyclerView.adapter =
                        FoundGameAdapter(items as List<FoundGameItem>, viewLifecycleOwner)
                } else {
                    Snackbar.make(view, textError.toString(), Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun getTitle(): String {
        return resources.getString(R.string.found_game)
    }
}