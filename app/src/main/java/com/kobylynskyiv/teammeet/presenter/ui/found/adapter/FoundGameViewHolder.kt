package com.kobylynskyiv.teammeet.presenter.ui.found.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.kobylynskyiv.teammeet.R
import com.kobylynskyiv.teammeet.domain.enties.FoundGameItem

class FoundGameViewHolder(
    val view: View,
    val lifecycle: LifecycleOwner
): FoundGameAdapter.FoundGameDetailViewHolder(view) {

    //private var typeGameModel: TypeGameViewHolderViewModel = TypeGameViewHolderViewModel()
    private val title: TextView = view.findViewById(R.id.title)
    private val imageView: ImageView = view.findViewById(R.id.imageView)
    private val selected: View = view.findViewById(R.id.selected)

    override fun bind(item: FoundGameItem) {
        with(item) {
            title.text = name
            Glide
                .with(view)
                .load(image)
                .into(imageView)

            selected.setOnClickListener {
                findNavController(view).navigate(R.id.action_foundGameFragment_to_cardFragment)
            }
        }
    }
}