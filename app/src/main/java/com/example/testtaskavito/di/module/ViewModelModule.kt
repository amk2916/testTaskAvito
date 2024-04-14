package com.example.testtaskavito.di.module

import androidx.lifecycle.ViewModel
import com.example.testtaskavito.di.ViewModelKey
import com.example.testtaskavito.presentation.firstScreen.MoviesViewModel
import com.example.testtaskavito.presentation.secondScreen.MovieDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(MoviesViewModel::class)
    @Binds
    fun bindViewModel(impl: MoviesViewModel): ViewModel

    @IntoMap
    @ViewModelKey(MovieDetailViewModel::class)
    @Binds
    fun bindViewModelMovieItem(impl: MovieDetailViewModel): ViewModel

}