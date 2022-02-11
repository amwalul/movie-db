package com.example.moviedb.ui.screen.genre.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.data.remote.model.genre.GenreData
import com.example.moviedb.databinding.ItemGenreBinding

class GenreAdapter(
    private val interaction: Interaction? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<GenreData>() {
        override fun areItemsTheSame(oldItem: GenreData, newItem: GenreData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GenreData, newItem: GenreData): Boolean {
            return oldItem == newItem
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GenreViewHolder(
            ItemGenreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GenreViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<GenreData>) {
        differ.submitList(list)
    }


    private class GenreViewHolder(
        private val binding: ItemGenreBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GenreData) = binding.apply {
            root.setOnClickListener {
                interaction?.onItemSelected(item)
            }

            tvName.text = item.name
        }
    }

    interface Interaction {
        fun onItemSelected(item: GenreData)
    }
}