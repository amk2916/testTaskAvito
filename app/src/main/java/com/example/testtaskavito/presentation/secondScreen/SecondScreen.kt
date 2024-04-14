package com.example.testtaskavito.presentation.secondScreen

import ReviewsAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import com.example.testtaskavito.App
import com.example.testtaskavito.R
import com.example.testtaskavito.data.Broacast
import com.example.testtaskavito.presentation.firstScreen.MoviesLoadStateAdapter
import com.example.testtaskavito.presentation.ViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class SecondScreen : Fragment() {
    private var idItem = UNDEFINED_ID

    private lateinit var viewModel: MovieDetailViewModel
    private var actorAdapter: ActorsAdapter = ActorsAdapter()
    private var commentAdapter: ReviewsAdapter = ReviewsAdapter()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as App).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParam()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component.injectSecond(this)

        viewModel = ViewModelProvider(this, viewModelFactory)[MovieDetailViewModel::class.java]
        viewModel.getMovie(idItem)


        val recyclerActor = view.findViewById<RecyclerView>(R.id.listActors)
        val recyclerComment = view.findViewById<RecyclerView>(R.id.listComments)
        val poster = view.findViewById<ImageView>(R.id.posterView)
        val ratingKp = view.findViewById<TextView>(R.id.ratingKp)
        val ratingImdb = view.findViewById<TextView>(R.id.ratingImdb)
        val ratingTmdb = view.findViewById<TextView>(R.id.ratingTmdb)
        val filmDuration = view.findViewById<TextView>(R.id.filmDuration)
        val description = view.findViewById<TextView>(R.id.Description)
        val nameMovie = view.findViewById<TextView>(R.id.nameMovie)
        val progressBar = view.findViewById<View>(R.id.progress_bar)
        val error_message = view.findViewById<View>(R.id.error_message)

        val emptyActorTextView = view.findViewById<TextView>(R.id.emptyActorTextView)
        val emptyCommentTextView = view.findViewById<TextView>(R.id.emptyCommentTextView)

        Broacast.errorUpdates
            .onEach {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG)
                    .show()
            }
            .launchIn(lifecycleScope)

        recyclerActor.adapter = actorAdapter

        recyclerComment.adapter = commentAdapter

        //TODO: поправить если успею
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.review
                    .onEach {
                        commentAdapter.submitData(PagingData.empty())
                        if (commentAdapter.itemCount == 0) {
                            emptyCommentTextView.isVisible = true
                            recyclerComment.isVisible = false
                        } else {
                            emptyCommentTextView.isVisible = false
                            recyclerComment.isVisible = true
                        }
                    }
                    .launchIn(this)

                viewModel.actors
                    .onEach {
                        actorAdapter.submitData(it)
                        if (actorAdapter.itemCount == 0) {
                            emptyActorTextView.isVisible = true
                            recyclerActor.isVisible = false
                        } else {
                            emptyActorTextView.isVisible = false
                            recyclerActor.isVisible = true
                        }
                    }
                    .launchIn(this)
            }
        }


        viewModel.isLoadingFlow.onEach {
            progressBar.isVisible = it
        }.launchIn(lifecycleScope)

        viewModel.isErrorFlow.onEach {
            error_message.isVisible = it
        }.launchIn(lifecycleScope)

        viewModel.movie
            .onEach {
                nameMovie.text = it.nameFilm

                Picasso.get()
                    .load(it.posters)
                    .placeholder(R.drawable.no_data)
                    .error(R.drawable.default_poster)
                    .into(poster)

                ratingKp.text = it.ratingKp
                ratingImdb.text = it.ratingIMDB
                ratingTmdb.text = it.ratingTMDB
                filmDuration.text = it.movieLength
                description.text = it.description

            }.launchIn(lifecycleScope)

    }


    private fun parseParam() {
        val args = requireArguments()
        idItem = args.getInt(KEY)
    }

    companion object {
        fun instance(idItem: Int): SecondScreen {
            return SecondScreen().apply {
                arguments = Bundle().apply {
                    putInt(KEY, idItem)
                }
            }
        }

        private const val KEY = "key"
        private const val UNDEFINED_ID = -1000
    }
}