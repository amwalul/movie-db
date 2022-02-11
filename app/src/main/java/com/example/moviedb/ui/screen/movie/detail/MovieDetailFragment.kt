package com.example.moviedb.ui.screen.movie.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.moviedb.base.BaseFragment
import com.example.moviedb.base.PagingLoadStateAdapter
import com.example.moviedb.data.Loading
import com.example.moviedb.data.remote.model.movie.detail.MovieResponse
import com.example.moviedb.data.remote.model.movie.video.MovieVideoListResponse
import com.example.moviedb.data.withFailure
import com.example.moviedb.data.withSuccess
import com.example.moviedb.databinding.FragmentMovieDetailBinding
import com.example.moviedb.extension.visibleOrGone
import com.example.moviedb.ui.screen.genre.list.GenreAdapter
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding>() {

    private val viewModel by viewModels<MovieDetailViewModel>()

    private val args by navArgs<MovieDetailFragmentArgs>()

    private val genreAdapter = GenreAdapter()

    private val reviewAdapter = ReviewAdapter()

    override fun getBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        attachToParent: Boolean
    ) = FragmentMovieDetailBinding.inflate(inflater, parent, attachToParent)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()

        args.movieId.let {
            viewModel.getMovie(it)
            viewModel.getMovieVideoList(it)
            viewModel.getMovieReviewList(it)
        }
    }

    private fun initViews() = withBinding {
        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

        lifecycle.addObserver(youtubeViewTrailer)

        rvGenre.apply {
            adapter = genreAdapter
            layoutManager = FlexboxLayoutManager(requireContext(), FlexDirection.ROW, FlexWrap.WRAP)
        }

        rvReview.apply {
            val footerAdapter = PagingLoadStateAdapter { reviewAdapter.retry() }
            adapter = reviewAdapter.withLoadStateFooter(footerAdapter)
        }
    }

    private fun initMovieData(data: MovieResponse) = withBinding {
        tvTitle.text = data.title
        tvReleaseDate.text = data.releaseDate
        tvRating.text = data.voteAverage.toString()

        genreAdapter.submitList(data.genres)

        tvOverview.text = data.overview
    }

    private fun initMovieVideoData(data: MovieVideoListResponse) = withBinding {
        data.results.takeIf { it.isNotEmpty() }?.let {
            youtubeViewTrailer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.cueVideo(it.first().key, 0f)
                }
            })
        }
    }

    private fun showLoading(show: Boolean) = withBinding {
        progressLoading.visibleOrGone(show)
        rvGenre.visibleOrGone(!show)
    }

    private fun initObservers() = with(viewModel) {
        movie.observe(viewLifecycleOwner) { resource ->
            showLoading(resource == Loading)
            resource.withSuccess {
                initMovieData(data)
            }.withFailure {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }

        movieVideoList.observe(viewLifecycleOwner) { resource ->
            resource.withSuccess {
                initMovieVideoData(data)
            }.withFailure {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }

        movieReviewList.observe(viewLifecycleOwner) { data ->
            launchOnViewLifecycleScope {
                reviewAdapter.submitData(data)
            }
        }
    }

    override fun onDestroyView() {
        withBinding { lifecycle.removeObserver(youtubeViewTrailer) }
        super.onDestroyView()
    }
}