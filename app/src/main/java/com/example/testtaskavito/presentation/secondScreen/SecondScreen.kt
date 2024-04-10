package com.example.testtaskavito.presentation.secondScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.testtaskavito.App
import com.example.testtaskavito.R
import com.example.testtaskavito.presentation.ViewModelFactory
import com.example.testtaskavito.presentation.firstScreen.MoviesViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class SecondScreen : Fragment() {
    private var idItem = UNDEFINED_ID

    private lateinit var viewModel: MovieItemViewModel


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
        return inflater.inflate(R.layout.movie_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component.injectSecond(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[MovieItemViewModel::class.java]
        viewModel.getMovie(idItem)


        val poster = view.findViewById<ImageView>(R.id.posterView)
        val nameMovie = view.findViewById<TextView>(R.id.nameMovie)
        lifecycleScope.launch {
            viewModel.movie.collect {
                nameMovie.text = it.nameFilm
                Picasso.get()
                    .load(it.posters)
                    .error(R.drawable.default_poster)
                    .resize(poster.width, poster.height)
                    .into(poster)
            }
        }


        /*       val ratingKp = view.findViewById<TextView>(R.id.ratingKp)
               val ratingImdb = view.findViewById<TextView>(R.id.ratingImdb)
               val ratingTmdb = view.findViewById<TextView>(R.id.ratingTmdb)
               val filmDuration = view.findViewById<TextView>(R.id.filmDuration)*/


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