package com.example.testtaskavito.di

import com.example.testtaskavito.data.RepositoryImpl
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