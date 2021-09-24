package com.kobylynskyiv.teammeet.presenter.ui.create

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kobylynskyiv.teammeet.databinding.FragmentCreateGame1Binding
import com.kobylynskyiv.teammeet.R
import com.kobylynskyiv.teammeet.domain.viewModel.MainActivityViewModel
import com.kobylynskyiv.teammeet.presenter.utils.FragmentUtils

class CreateGameFragment : Fragment(), FragmentUtils {

    private val binding: FragmentCreateGame1Binding by lazy {  FragmentCreateGame1Binding.inflate(layoutInflater) }
    private val activityMainModel: MainActivityViewModel by viewModels({ requireActivity() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activityMainModel.updateActionBarTitle(getTitle())

        binding.back.setOnClickListener {
            onBackPressed(activity)
        }
    }

    override fun getTitle(): String {
        return resources.getString(R.string.profile_create_game)
    }
}