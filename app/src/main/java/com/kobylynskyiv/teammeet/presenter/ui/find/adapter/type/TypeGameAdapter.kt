package com.kobylynskyiv.teammeet.presenter.ui.find.adapter.type

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.kobylynskyiv.teammeet.R
import com.kobylynskyiv.teammeet.presenter.data.enties.TypeGameItem

class TypeGameAdapter(
    private val items: List<TypeGameItem>,
    private val lifecycle: LifecycleOwner)
    : RecyclerView.Adapter<TypeGameAdapter.TypeGameDetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeGameDetailViewHolder
    = TypeGameViewHolder(
        view = parent.makeView(R.layout.item_type_games),
        lifecycle = lifecycle
    )

    override fun onBindViewHolder(holder: TypeGameDetailViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    private fun ViewGroup.makeView(@LayoutRes layoutResId: Int): View =
        LayoutInflater.from(this.context).inflate(layoutResId, this, false)

    abstract class TypeGameDetailViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(item: TypeGameItem)
    }
}