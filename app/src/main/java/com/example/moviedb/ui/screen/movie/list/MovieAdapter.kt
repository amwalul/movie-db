package com.example.moviedb.ui.screen.movie.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedb.BuildConfig
import com.example.moviedb.R
import com.example.moviedb.data.remote.model.movie.list.MovieListData
import com.example.moviedb.databinding.ItemMovieBinding

class MovieAdapter(
    private val interaction: Interaction
) : PagingDataAdapter<MovieListData, RecyclerView.ViewHolder>(MovieDiffer) {

    object MovieDiffer : DiffUtil.ItemCallback<MovieListData>() {
        override fun areItemsTheSame(oldItem: MovieListData, newItem: MovieListData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieListData, newItem: MovieListData): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieViewHolder -> {
                getItem(position)?.let { holder.bind(it) }
            }
        }
    }

    private class MovieViewHolder(
        private val binding: ItemMovieBinding,
        private val interaction: Interaction
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MovieListData) = binding.apply {
            root.setOnClickListener {
                interaction.onItemSelected(item)
            }

            Glide.with(root)
                .load(BuildConfig.BASE_IMAGE_URL + item.posterPath)
                .placeholder(R.drawable.ic_baseline_movie_24)
                .into(ivPoster)

            tvTitle.text = item.title
        }
    }

    interface Interaction {
        fun onItemSelected(item: MovieListData)
    }
}