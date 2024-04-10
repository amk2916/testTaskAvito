package com.example.testtaskavito.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testtaskavito.R
import com.example.testtaskavito.presentation.firstScreen.MoviesListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragment = MoviesListFragment.instanceMoviesListFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()

    }
}