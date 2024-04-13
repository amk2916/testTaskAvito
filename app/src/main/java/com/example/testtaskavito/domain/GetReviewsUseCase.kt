package com.example.testtaskavito.domain

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.testtaskavito.data.server.Reviews
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetReviewsUseCase @Inject constructor(
    private val repository: Repository
) {
    fun getReviewForID(idMovie: Int): PagingSource<Int, Review>{
        return repository.getReviewForID(idMovie)
    }
}