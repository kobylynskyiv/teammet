package com.kobylynskyiv.teammeet.presenter.ui.city

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kobylynskyiv.teammeet.databinding.FragmentCityBinding


class CityFragment : Fragment() {

    private val binding: FragmentCityBinding by lazy {
        FragmentCityBinding.inflate(layoutInflater)
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
