package com.example.testtaskavito.presentation.firstScreen

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
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
import com.example.testtaskavito.data.Broacast
import com.example.testtaskavito.presentation.ViewModelFactory
import com.example.testtaskavito.presentation.secondScreen.SecondScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviesListFragment : Fragment() {

    private var nameCountry: String? = null
    private var ageRating: Int? = null
    private var year: Int? = null

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MoviesViewModel::class.java]
    }

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

    val whather = object : TextWatcher {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            viewModel.search(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)= Unit
        override fun afterTextChanged(s: Editable?) = Unit
    }

private var search: EditText? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component.inject(this)

        search = view.findViewById<EditText>(R.id.searchET)


        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerFilms)
        val progress = view.findViewById<ProgressBar>(R.id.progress)

        val imageView = view.findViewById<ImageView>(R.id.filterImage)



        imageView.setOnClickListener {
            val fragment = FilterFragment.instance(nameCountry, year, ageRating)

            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack("")
                .commit()

        }

        setupAdapter(recyclerView)

        moviesAdapter.addLoadStateListener { state ->
            recyclerView.isVisible = state.refresh != LoadState.Loading
            progress.isVisible = state.refresh == LoadState.Loading
        }


        lifecycleScope.launch(Dispatchers.IO) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movies
                    .onEach(moviesAdapter::submitData)
                    .launchIn(this)
            }
        }

        Broacast.errorUpdates
            .onEach {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG)
                    .show()
            }
            .launchIn(lifecycleScope)


        // временное решение для фильтров
        parentFragmentManager.setFragmentResultListener(
            "requestKey",
            viewLifecycleOwner
        ) { requestKey, bundle ->
            Log.e("setFragmentResultListener", "1")
            val nameCountryET = bundle.getString("nameCountry")
            val ageRatingET = bundle.getString("ageRating")
            val yearET = bundle.getString("year")

            val newValueYear = if (yearET == null || yearET == "") {
                null
            } else {
                yearET.toInt()
            }
            val newValueAgeRating = ageRatingET?.toIntOrNull()

            val newValueNameCountry = if (nameCountryET == null || nameCountryET == "") {
                null
            } else {
                nameCountryET
            }

            if (newValueYear != year || newValueAgeRating != ageRating || newValueNameCountry != nameCountry) {
                nameCountry = newValueNameCountry
                year = newValueYear
                ageRating = newValueAgeRating
                viewModel.getWithFilter(Filter(nameCountry, ageRating, year))
            }

        }
    }

    override fun onStart() {
        super.onStart()
        search?.addTextChangedListener(whather)
    }

    override fun onPause() {
        super.onPause()
        search?.removeTextChangedListener(whather)
    }

    private fun setupAdapter(recyclerView: RecyclerView) {
        val dimenCornerRatingTV = resources.getDimension(R.dimen.cornerTextViewRating)
        val displayMetrics = resources.displayMetrics

        moviesAdapter = MoviesAdapter(displayMetrics, dimenCornerRatingTV)
        moviesAdapter.onClickListenerItem = {
            val fragment = SecondScreen.instance(it.id)
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack("")
                .commit()
        }
        recyclerView.adapter = moviesAdapter.withLoadStateHeaderAndFooter(
            footer = MoviesLoadStateAdapter(),
            header = MoviesLoadStateAdapter()
        )

    }

    companion object {
        fun instanceMoviesListFragment() = MoviesListFragment()
    }

}