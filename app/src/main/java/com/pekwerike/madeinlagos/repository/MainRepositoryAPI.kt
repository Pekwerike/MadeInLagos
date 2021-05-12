package com.pekwerike.madeinlagos.repository

import com.pekwerike.madeinlagos.database.ProductWithReviews
import com.pekwerike.madeinlagos.model.NetworkResult
import kotlinx.coroutines.flow.Flow

interface MainRepositoryAPI {
    val allProductsWithReviews: Flow<List<ProductWithReviews>>
    suspend fun refreshProductList(): NetworkResult
    suspend fun getProductReviewsByProductId(productId: String): NetworkResult
}