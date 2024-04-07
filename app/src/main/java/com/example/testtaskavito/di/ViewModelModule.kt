package com.example.testtaskavito.di

import androidx.lifecycle.ViewModel
import com.example.testtaskavito.presentation.MoviesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(MoviesViewModel::class)
    @Binds
    fun bindViewModel(impl: MoviesViewModel): ViewModel

}