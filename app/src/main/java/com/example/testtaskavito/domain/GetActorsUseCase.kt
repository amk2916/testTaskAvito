package com.example.testtaskavito.domain

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.testtaskavito.data.server.Actors
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetActorsUseCase @Inject constructor(
    private val repository: Repository
) {
    fun getActorsForID(idMovie: Int): PagingSource<Int, Actor> {
        return repository.getActorsForID(idMovie)
    }
}