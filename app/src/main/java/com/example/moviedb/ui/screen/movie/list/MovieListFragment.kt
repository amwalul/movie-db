package com.example.moviedb.ui.screen.movie.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviedb.base.BaseFragment
import com.example.moviedb.base.PagingLoadStateAdapter
import com.example.moviedb.data.remote.model.movie.list.MovieListData
import com.example.moviedb.databinding.FragmentMovieListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : BaseFragment<FragmentMovieListBinding>() {

    private val viewModel by viewModels<MovieListViewModel>()

    private val args by navArgs<MovieListFragmentArgs>()

    private val movieAdapter = MovieAdapter(object : MovieAdapter.Interaction {
        override fun onItemSelected(item: MovieListData) {
            navigateToMovieDetailScreen(item.id)
        }
    })

    override fun getBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        attachToParent: Boolean
    ) = FragmentMovieListBinding.inflate(inflater, parent, attachToParent)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()

        viewModel.getMovieList(args.genreId)
    }

    private fun initViews() = withBinding {
        toolbar.apply {
            setNavigationOnClickListener { findNavController().navigateUp() }
            toolbar.title = args.genreName
        }

        rvMovie.apply {
            val footerAdapter = PagingLoadStateAdapter { movieAdapter.retry() }
            adapter = movieAdapter.withLoadStateFooter(footerAdapter)

            (layoutManager as GridLayoutManager).spanSizeLookup =
                object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int =
                        if (position == movieAdapter.itemCount && footerAdapter.itemCount > 0) 2 else 1
                }
        }
    }

    private fun navigateToMovieDetailScreen(id: Int) {
        val directions =
            MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(id)
        findNavController().navigate(directions)
    }

    private fun initObservers() = with(viewModel) {
        movieList.observe(viewLifecycleOwner) { data ->
            launchOnViewLifecycleScope {
                movieAdapter.submitData(data)
            }
        }
    }
}