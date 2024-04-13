package com.example.testtaskavito.domain

import androidx.paging.PagingData
import com.example.testtaskavito.data.server.Actors
import com.example.testtaskavito.data.server.Review
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetActorsUseCase @Inject constructor(
    private val repository: Repository
) {
    fun getActorsForID(idMovie: Int): Flow<PagingData<Actors>> {
        return repository.getActorsForID(idMovie)
    }
}