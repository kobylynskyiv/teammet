package com.kobylynskyiv.teammeet.presenter.ui.find.adapter.type

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.kobylynskyiv.teammeet.R
import com.kobylynskyiv.teammeet.domain.viewModel.TypeGameViewHolderViewModel
import com.kobylynskyiv.teammeet.presenter.data.enties.TypeGameItem

class TypeGameViewHolder(
    view: View,
    val lifecycle: LifecycleOwner
    ): TypeGameAdapter.TypeGameDetailViewHolder(view), ViewModelStoreOwner {

    private var typeGameModel: TypeGameViewHolderViewModel = TypeGameViewHolderViewModel()
    private val title: TextView = view.findViewById(R.id.title)
    private val image: ImageView = view.findViewById(R.id.image)
    private val view: View = view.findViewById(R.id.view)
    private val checkBox: AppCompatCheckBox = view.findViewById(R.id.button)

    override fun bind(item: TypeGameItem) {
        with(item) {
            title.text = nameGame
            image.setImageResource(drawableRes)
            view.setOnClickListener {
                typeGameModel.onClickTypeGameItemView(checkBox.isChecked)
            }
        }
    }

    override fun getViewModelStore(): ViewModelStore = viewModelStore
}