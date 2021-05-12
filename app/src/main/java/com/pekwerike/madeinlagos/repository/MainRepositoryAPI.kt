package com.pekwerike.madeinlagos.repository


import com.pekwerike.madeinlagos.model.NetworkResult
import com.pekwerike.madeinlagos.model.Product
import kotlinx.coroutines.flow.Flow

interface MainRepositoryAPI {
    val allProductsWithReviews: Flow<List<Product>>
    suspend fun refreshProductList(): NetworkResult
    suspend fun getProductReviewsByProductId(productId: String): NetworkResult
}