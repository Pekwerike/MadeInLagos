package com.pekwerike.madeinlagos.network.impl

import com.pekwerike.madeinlagos.model.NetworkResult
import com.pekwerike.madeinlagos.model.ProductReview
import com.pekwerike.madeinlagos.network.ProductReviewAPI
import com.squareup.moshi.Moshi
import io.ktor.client.*
import javax.inject.Inject

class ProductReviewImpl @Inject constructor(private val httpClient: HttpClient,
                                            private val moshi: Moshi) : ProductReviewAPI {
    override suspend fun getProductReviews(productId: String): NetworkResult {
       return try{ val response = httpClient.get<HttpResponse>(String.format(ProductReviewAPI.PRODUCT_REVIEW_BASE_URL, productId))
        when(response.status){
            HttpStatusCode.OK -> {
                NetworkResult.Success.ProductReveiws(moshi.adapter<List<ProductReviewImpl>>().fromJson(response.readText()) ?: listOf())
            }

            HttpStatusCode.BadRequest -> {
                NetworkResult.HttpError(response.status)
            }

            else -> {
                NetworkResult.HttpError(response.status)
            }
        }}catch (unknownHostException: java.net.UnknownHostException){
            NetworkResult.NoInternetConnection
        }catch(exception: Exception){
            NetworkResult.NoInternetConnection
        }
    }

    override suspend fun postProductReview(productReviewImpl: ProductReviewImpl): NetworkResult {
        return try{
           val response = httpClient.post<HttpResponse>(String.format(ProductReviewImpl.PRODUCT_REVIEW_BASE_URL, productReviewImpl.productId)){
                contentType(Content.Application.Json)
                body = productReviewImpl
                expectSuccess = true
            }
            when(response.status){
                HttpStatusCode.OK -> {
                    val postedReview = moshi.adapter<ProductReviewImpl>().fromJson(response.readText())
                    if(postedReview != null){
                     NetworkResult.Success.SingleProductReview(postedReview)
                    }else {
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
        }catch (unknownHostException: java.net.UnknownHostException){
            NetworkResult.NoInternetConnection
        }catch(exception: Exception){
            NetworkResult.NoInternetConnection
        }
    }
}