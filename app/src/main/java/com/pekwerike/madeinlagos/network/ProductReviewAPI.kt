package com.pekwerike.madeinlagos.network

import com.pekwerike.madeinlagos.model.NetworkResult
import com.pekwerike.madeinlagos.model.ProductReview

interface ProductReviewAPI {
    companion object {
        const val PRODUCT_REVIEW_BASE_URL = "reviews/%s"
    }
    suspend fun getProductReviews(productId: String): NetworkResult
    suspend fun postProductReview(productReview: ProductReview): NetworkResult
}