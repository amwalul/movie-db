package com.example.moviedb.ui.screen.genre.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.moviedb.base.BaseFragment
import com.example.moviedb.data.Loading
import com.example.moviedb.data.remote.model.genre.GenreData
import com.example.moviedb.data.withFailure
import com.example.moviedb.data.withSuccess
import com.example.moviedb.databinding.FragmentGenreListBinding
import com.example.moviedb.extension.visibleOrGone
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenreListFragment : BaseFragment<FragmentGenreListBinding>() {

    private val viewModel by viewModels<GenreListViewModel>()

    private val genreAdapter = GenreAdapter(object : GenreAdapter.Interaction {
        override fun onItemSelected(item: GenreData) {
            navigateToMovieListScreen(item)
        }
    })

    override fun getBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        attachToParent: Boolean
    ) = FragmentGenreListBinding.inflate(inflater, parent, attachToParent)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()
    }

    private fun initViews() = withBinding {
        rvGenre.apply {
            adapter = genreAdapter
            layoutManager = FlexboxLayoutManager(requireContext(), FlexDirection.ROW, FlexWrap.WRAP)
        }
    }

    private fun showLoading(show: Boolean) = withBinding {
        progressLoading.visibleOrGone(show)
        rvGenre.visibleOrGone(!show)
    }

    private fun navigateToMovieListScreen(genre: GenreData) {
        val directions = GenreListFragmentDirections.actionGenreListFragmentToMovieListFragment(
            genre.id,
            genre.name
        )
        findNavController().navigate(directions)
    }

    private fun initObservers() = with(viewModel) {
        genreList.observe(viewLifecycleOwner) { resource ->
            showLoading(resource == Loading)
            resource.withSuccess {
                genreAdapter.submitList(data.genres)
            }.withFailure {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}