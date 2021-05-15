package com.pekwerike.madeinlagos.repository


import androidx.lifecycle.LiveData
import com.pekwerike.madeinlagos.model.NetworkResult
import com.pekwerike.madeinlagos.model.Product
import kotlinx.coroutines.flow.Flow

interface MainRepositoryAPI {
    val allProductsWithReviewsAsLiveData: LiveData<List<Product>>
    suspend fun refreshProductList(): NetworkResult
    suspend fun getProductReviewsByProductId(productId: String): NetworkResult
    suspend fun postProductReview(userRating: Float, userReviewText: String): NetworkResult
}