package com.pekwerike.madeinlagos.repository


import androidx.lifecycle.LiveData
import com.pekwerike.madeinlagos.model.NetworkResult
import com.pekwerike.madeinlagos.model.Product
import com.pekwerike.madeinlagos.model.ProductReview
import com.pekwerike.madeinlagos.model.ProductsAndNetworkState
import kotlinx.coroutines.flow.Flow

interface MainRepositoryAPI {
    suspend fun refreshProductList(): NetworkResult
    suspend fun getProductReviewsByProductId(productId: String): NetworkResult
    suspend fun postProductReview(
        productId: String,
        userRating: Int,
        userReviewText: String
    ): Pair<ProductReview?, NetworkResult>

    suspend fun getProductById(productId: String): Product
    suspend fun fetchAllProducts(): ProductsAndNetworkState
    suspend fun getProductWithReviewsById(productId: String): Pair<Product?, NetworkResult>
    suspend fun getCachedProducts(): List<Product>
    suspend fun getCachedProductWithReviewsById(productId: String): Product
}