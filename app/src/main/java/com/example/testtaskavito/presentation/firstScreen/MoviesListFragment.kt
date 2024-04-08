package com.example.testtaskavito.presentation.firstScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.testtaskavito.App
import com.example.testtaskavito.R
import com.example.testtaskavito.presentation.MoviesAdapter
import com.example.testtaskavito.presentation.MoviesLoadStateAdapter
import com.example.testtaskavito.presentation.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviesListFragment : Fragment() {

    private lateinit var viewModel: MoviesViewModel

    private lateinit var moviesAdapter: MoviesAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as App).component
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movies_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component.inject(this)

        val recyclerView  = view.findViewById<RecyclerView>(R.id.recyclerFilms)
        val progress = view.findViewById<ProgressBar>(R.id.progress)
        viewModel = ViewModelProvider(this, viewModelFactory)[MoviesViewModel::class.java]

        setupAdapter(recyclerView)

        moviesAdapter.addLoadStateListener { state ->
            recyclerView.isVisible = state.refresh != LoadState.Loading
            progress.isVisible = state.refresh == LoadState.Loading
        }


        lifecycleScope.launch(Dispatchers.IO) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movies
                    .collectLatest(moviesAdapter::submitData)
            }
        }



    }


    private fun setupAdapter(recyclerView: RecyclerView){
        val dimenCornerRatingTV = resources.getDimension(R.dimen.cornerTextViewRating)
        val displayMetrics = resources.displayMetrics

        moviesAdapter = MoviesAdapter(displayMetrics, dimenCornerRatingTV)
        recyclerView.adapter = moviesAdapter.withLoadStateHeaderAndFooter(
            footer = MoviesLoadStateAdapter(),
            header = MoviesLoadStateAdapter()
        )
    }
    companion object{
        fun instanceMoviesListFragment()  = MoviesListFragment()
    }
}