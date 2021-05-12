package com.pekwerike.madeinlagos.network.impl

import com.pekwerike.madeinlagos.model.NetworkResult
import com.pekwerike.madeinlagos.model.Product
import com.pekwerike.madeinlagos.model.ProductReview
import com.pekwerike.madeinlagos.network.ProductReviewAPI
import com.pekwerike.madeinlagos.network.ProductReviewAPI.Companion.PRODUCT_REVIEW_BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import javax.inject.Inject

class ProductReviewImpl @Inject constructor(
    private val httpClient: HttpClient,
    private val moshi: Moshi
) : ProductReviewAPI {
    @ExperimentalStdlibApi
    override suspend fun getProductReviews(productId: String): NetworkResult {
        return try {
            val response =
                httpClient.get<HttpResponse>(String.format(PRODUCT_REVIEW_BASE_URL, productId))
            when (response.status) {
                HttpStatusCode.OK -> {
                    NetworkResult.Success.ProductReviews(
                        moshi.adapter<List<ProductReview>>().fromJson(response.readText())
                            ?: listOf()
                    )
                }

                HttpStatusCode.BadRequest -> {
                    NetworkResult.HttpError(response.status)
                }

                else -> {
                    NetworkResult.HttpError(response.status)
                }
            }
        } catch (unknownHostException: java.net.UnknownHostException) {
            NetworkResult.NoInternetConnection
        } catch (exception: Exception) {
            NetworkResult.NoInternetConnection
        }
    }

    @ExperimentalStdlibApi
    override suspend fun postProductReview(productReview: ProductReview): NetworkResult {
        return try {
            val response = httpClient.post<HttpResponse>(
                String.format(
                    PRODUCT_REVIEW_BASE_URL,
                    productReview.productId
                )
            ) {
                contentType(ContentType.Application.Json)
                body = productReview
                expectSuccess = true
            }
            when (response.status) {
                HttpStatusCode.OK -> {
                    val postedReview = moshi.adapter<ProductReview>().fromJson(response.readText())
                    if (postedReview != null) {
                        NetworkResult.Success.SingleProductReview(postedReview)
                    } else {
                        NetworkResult.Success.NoResponse
                    }
                }
                HttpStatusCode.BadRequest -> {
                    NetworkResult.HttpError(HttpStatusCode.BadRequest)
                }
                else -> {
                    NetworkResult.HttpError(response.status)
                }
            }
        } catch (unknownHostException: java.net.UnknownHostException) {
            NetworkResult.NoInternetConnection
        } catch (exception: Exception) {
            NetworkResult.NoInternetConnection
        }
    }
}