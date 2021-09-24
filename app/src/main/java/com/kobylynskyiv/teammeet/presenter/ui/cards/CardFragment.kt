package com.kobylynskyiv.teammeet.presenter.ui.cards

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kobylynskyiv.teammeet.R
import com.kobylynskyiv.teammeet.databinding.FragmentCardBinding
import com.kobylynskyiv.teammeet.domain.viewModel.MainActivityViewModel
import com.kobylynskyiv.teammeet.presenter.utils.FragmentUtils

class CardFragment : Fragment(), FragmentUtils {

    private val binding: FragmentCardBinding by lazy {  FragmentCardBinding.inflate(layoutInflater) }
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
    }

    override fun getTitle(): String {
        return resources.getString(R.string.card_fragment)
    }
}