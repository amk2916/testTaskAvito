package com.example.testtaskavito.di

import android.app.Application
import com.example.testtaskavito.MainActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [
    InternetModule::class
])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance application: Application
        ):AppComponent
    }
}