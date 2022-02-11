package com.example.moviedb.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.databinding.ItemNetworkStateBinding
import com.example.moviedb.extension.visibleOrGone

class PagingLoadStateAdapter(
    private val onRetryClicked: () -> Unit
) : LoadStateAdapter<PagingLoadStateAdapter.PagingLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: PagingLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): PagingLoadStateViewHolder {
        return PagingLoadStateViewHolder(
            ItemNetworkStateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onRetryClicked
        )
    }

    class PagingLoadStateViewHolder(
        private val binding: ItemNetworkStateBinding,
        private val onRetryClicked: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {
            with(binding) {
                tvError.apply {
                    visibleOrGone(!(loadState as? LoadState.Error)?.error?.message.isNullOrBlank())
                    text = (loadState as? LoadState.Error)?.error?.message
                }
                progressLoading.visibleOrGone(loadState is LoadState.Loading)
                btnRetry.apply {
                    visibleOrGone(loadState is LoadState.Error)
                    setOnClickListener {
                        onRetryClicked()
                    }
                }
            }
        }
    }
}