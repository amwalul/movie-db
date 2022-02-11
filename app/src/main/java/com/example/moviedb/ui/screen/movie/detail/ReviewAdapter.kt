package com.example.moviedb.ui.screen.movie.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.R
import com.example.moviedb.data.remote.model.movie.review.MovieReviewListData
import com.example.moviedb.databinding.ItemReviewBinding
import com.example.moviedb.extension.visibleOrGone

class ReviewAdapter :
    PagingDataAdapter<MovieReviewListData, RecyclerView.ViewHolder>(ReviewDiffer) {

    object ReviewDiffer : DiffUtil.ItemCallback<MovieReviewListData>() {
        override fun areItemsTheSame(
            oldItem: MovieReviewListData,
            newItem: MovieReviewListData
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MovieReviewListData,
            newItem: MovieReviewListData
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieViewHolder(
            ItemReviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
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
        private val binding: ItemReviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MovieReviewListData) = binding.apply {
            tvName.text = item.author
            tvRating.apply {
                visibleOrGone(item.authorDetails.rating != null)
                text = item.authorDetails.rating?.let {
                    root.context.getString(
                        R.string.rating_format,
                        it
                    )
                }
            }
            tvReview.text = item.content
        }
    }
}