package com.pekwerike.madeinlagos.repository

import com.pekwerike.madeinlagos.model.NetworkResult

interface MainRepositoryAPI {
    suspend fun refreshProductList(): NetworkResult
    suspend fun getProductReviewsByProductId(productId: String): NetworkResult
}