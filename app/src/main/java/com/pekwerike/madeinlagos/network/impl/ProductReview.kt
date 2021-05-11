package com.pekwerike.madeinlagos.network.impl

import com.pekwerike.madeinlagos.model.NetworkResult
import com.pekwerike.madeinlagos.model.ProductReview
import com.pekwerike.madeinlagos.network.ProductReviewAPI
import com.squareup.moshi.Moshi
import io.ktor.client.*
import javax.inject.Inject

class ProductReview @Inject constructor ( private val httpClient: HttpClient,
private val moshi: Moshi): ProductReviewAPI {
    override suspend fun getProductReviews(productId: String): NetworkResult {

        return NetworkResult.NoInternetConnection
    }

    override suspend fun postProductReview(productReview: ProductReview): NetworkResult {
        return NetworkResult.NoInternetConnection
    }
}