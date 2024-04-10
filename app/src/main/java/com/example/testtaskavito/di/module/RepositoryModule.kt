package com.example.testtaskavito.di.module

import com.example.testtaskavito.data.RepositoryImpl
import com.example.testtaskavito.di.ApplicationScope
import com.example.testtaskavito.domain.Repository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {
    @ApplicationScope
    @Binds
    fun bindRepository(
        repositoryImpl: RepositoryImpl
    ) : Repository
}