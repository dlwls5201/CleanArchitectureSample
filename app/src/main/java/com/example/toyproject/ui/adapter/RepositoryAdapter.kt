package com.example.toyproject.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.toyproject.R
import com.example.toyproject.base.BaseViewHolder
import com.example.toyproject.databinding.ItemRepositoryBinding
import com.example.toyproject.ui.model.RepoItem

class RepositoryAdapter : RecyclerView.Adapter<RepositoryAdapter.RepositoryHolder>() {

    private var items: MutableList<RepoItem> = mutableListOf()

    var onItemClick: ((repoItem: RepoItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RepositoryHolder(parent).apply {
            itemView.setOnClickListener {
                val item = items[adapterPosition]
                onItemClick?.invoke(item)
            }
        }

    override fun onBindViewHolder(holder: RepositoryHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun setItems(items: List<RepoItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun clearItems() {
        this.items.clear()
        notifyDataSetChanged()
    }

    class RepositoryHolder(parent: ViewGroup) : BaseViewHolder<ItemRepositoryBinding, RepoItem>(
        parent, R.layout.item_repository
    ) {

        override fun bind(data: RepoItem) {
            binding?.item = data
        }
    }
}