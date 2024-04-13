package com.example.testtaskavito.domain

import androidx.paging.PagingData
import com.example.testtaskavito.data.local.ModelForListLocal
import com.example.testtaskavito.data.server.Actors
import com.example.testtaskavito.data.server.Review
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetReviewsUseCase @Inject constructor(
    private val repository: Repository
) {
    fun getReviewForID(idMovie: Int): Flow<PagingData<Review>>{
        return repository.getReviewForID(idMovie)
    }
}