package com.example.testtaskavito.di

import android.app.Application
import com.example.testtaskavito.di.module.InternetModule
import com.example.testtaskavito.di.module.RepositoryModule
import com.example.testtaskavito.di.module.ViewModelModule
import com.example.testtaskavito.presentation.firstScreen.MoviesListFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [
    InternetModule::class,
    ViewModelModule::class,
    RepositoryModule::class
])
interface AppComponent {
    fun inject(fragment: MoviesListFragment)
    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance application: Application
        ):AppComponent
    }
}