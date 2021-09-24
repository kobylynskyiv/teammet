package com.kobylynskyiv.teammeet.presenter.ui.found.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.kobylynskyiv.teammeet.R
import com.kobylynskyiv.teammeet.domain.enties.FoundGameItem
import com.kobylynskyiv.teammeet.presenter.data.enties.TypeGameItem
import com.kobylynskyiv.teammeet.presenter.ui.find.adapter.type.TypeGameAdapter

class FoundGameAdapter(
    private val items: List<FoundGameItem>,
    private val lifecycle: LifecycleOwner)
    : RecyclerView.Adapter<FoundGameAdapter.FoundGameDetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoundGameDetailViewHolder
            = FoundGameViewHolder(
        view = parent.makeView(R.layout.item_found_game),
        lifecycle = lifecycle
    )

    override fun onBindViewHolder(holder: FoundGameDetailViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    private fun ViewGroup.makeView(@LayoutRes layoutResId: Int): View =
        LayoutInflater.from(this.context).inflate(layoutResId, this, false)

    abstract class FoundGameDetailViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(item: FoundGameItem)
    }
}