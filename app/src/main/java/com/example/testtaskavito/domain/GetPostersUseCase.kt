package com.example.testtaskavito.domain

import javax.inject.Inject

class GetPostersUseCase @Inject constructor(private val repository: Repository) {

    suspend fun getPostersByID(
        idServer: Int,
        countPosters: Int
    ): List<Poster> = repository.getPostersByID(idServer, countPosters)

}