package com.pekwerike.madeinlagos.network.impl

import com.pekwerike.madeinlagos.model.NetworkResult
import com.pekwerike.madeinlagos.model.ProductReview
import com.pekwerike.madeinlagos.network.ProductReviewAPI

class ProductReview: ProductReviewAPI {
    override suspend fun getProductReviews(productId: String): NetworkResult {
        return NetworkResult.NoInternetConnection
    }

    override suspend fun postProductReview(productReview: ProductReview): NetworkResult {
        return NetworkResult.NoInternetConnection
    }
}