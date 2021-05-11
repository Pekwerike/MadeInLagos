package com.pekwerike.madeinlagos.network

import com.pekwerike.madeinlagos.model.NetworkResult
import com.pekwerike.madeinlagos.model.ProductReview

interface ProductReviewAPI {
    suspend fun getProductReviews(productId: String): NetworkResult
    suspend fun postProductReview(productReview: ProductReview): NetworkResult
}