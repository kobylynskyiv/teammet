package com.kobylynskyiv.teammeet.presenter.ui.find

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kobylynskyiv.teammeet.databinding.FragmentEditGameBinding


class ItemGameEditFragment : Fragment() {

    private val binding: FragmentEditGameBinding by lazy {
        FragmentEditGameBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}