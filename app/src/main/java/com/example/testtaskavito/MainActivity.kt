package com.example.testtaskavito

import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.testtaskavito.presentation.MoviesAdapter
import com.example.testtaskavito.presentation.MoviesLoadStateAdapter
import com.example.testtaskavito.presentation.MoviesViewModel
import com.example.testtaskavito.presentation.ViewModelFactory
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MoviesViewModel

    private lateinit var moviesAdapter: MoviesAdapter


    private val recyclerView by lazy {
        findViewById<RecyclerView>(R.id.recyclerFilms)
    }

    private val progress by lazy {
        findViewById<ProgressBar>(R.id.progress)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as App).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        setContentView(R.layout.movies_list)
        component.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[MoviesViewModel::class.java]
        moviesAdapter = MoviesAdapter(this)
        recyclerView.adapter = moviesAdapter.withLoadStateHeaderAndFooter(
            footer = MoviesLoadStateAdapter(),
            header = MoviesLoadStateAdapter()
        )

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
}