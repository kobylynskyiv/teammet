package com.kobylynskyiv.teammeet.presenter.ui.find

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.kobylynskyiv.teammeet.databinding.FragmentItemGameBinding
import java.util.*


class ItemGameFragment : Fragment() {

    private val binding: FragmentItemGameBinding by lazy {  FragmentItemGameBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.title){
            "Edit" -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}