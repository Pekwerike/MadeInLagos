package com.pekwerike.madeinlagos.network.retrofit

import com.pekwerike.madeinlagos.model.NetworkResult
import com.pekwerike.madeinlagos.model.Product
import com.pekwerike.madeinlagos.model.ProductReview
import com.pekwerike.madeinlagos.network.ProductReviewAPI
import com.pekwerike.madeinlagos.network.ProductServiceAPI
import java.net.UnknownHostException
import javax.inject.Inject

class ProductServiceRetrofitImpl @Inject constructor(
    private val productServiceRetrofitAPI:
    ProductServiceRetrofitAPI
) : ProductServiceAPI, ProductReviewAPI {

    override suspend fun getAllProduct(): NetworkResult {
        return try {
            val response = productServiceRetrofitAPI.getAllProduct()
            if (response.isSuccessful) {
                val body: List<Product> = response.body() ?: listOf()
                NetworkResult.Success.AllProducts(body)
            } else {
                NetworkResult.HttpError(response.code())
            }
        } catch (unknownHostException: UnknownHostException) {
            NetworkResult.NoInternetConnection
        } catch (exception: Exception) {
            NetworkResult.NoInternetConnection
        }
    }

    override suspend fun createProduct(product: Product): NetworkResult {
        return try {
            val response = productServiceRetrofitAPI.createProduct(
                ProductServiceAPI.BASE_PRODUCT_URL,
                product
            )
            if (response.isSuccessful) {
                val createdProduct = response.body()
                if (createdProduct == null) {
                    NetworkResult.Success.NoResponse
                } else {
                    NetworkResult.Success.SingleProduct(createdProduct)
                }
            } else {
                NetworkResult.HttpError(response.code())
            }
        } catch (unknownHostException: UnknownHostException) {
            NetworkResult.NoInternetConnection
        } catch (exception: Exception) {
            NetworkResult.NoInternetConnection
        }
    }

    override suspend fun getProductById(productId: String): NetworkResult {
        return try {
            val response = productServiceRetrofitAPI.getProductById(
                String.format(
                    ProductServiceAPI.SINGLE_PRODUCT_URL,
                    productId
                )
            )
            if (response.isSuccessful) {
                val product = response.body()
                if (product == null) {
                    NetworkResult.Success.NoResponse
                } else {
                    NetworkResult.Success.SingleProduct(product)
                }
            } else {
                NetworkResult.HttpError(response.code())
            }
        } catch (unknownHostException: UnknownHostException) {
            NetworkResult.NoInternetConnection
        } catch (exception: Exception) {
            NetworkResult.NoInternetConnection
        }
    }

    override suspend fun updateProduct(productId: String, product: Product): NetworkResult {
        return NetworkResult.Success.NoResponse
    }

    override suspend fun deleteProduct(productId: String): NetworkResult {
        return NetworkResult.Success.NoResponse
    }

    override suspend fun getProductReviews(productId: String): NetworkResult {
        return try {
            val response = productServiceRetrofitAPI.getReviewsForProduct(
                String.format(
                    ProductReviewAPI.PRODUCT_REVIEW_BASE_URL,
                    productId
                )
            )
            if (response.isSuccessful) {
                val productReviews: List<ProductReview> = response.body() ?: listOf()
                NetworkResult.Success.ProductReviews(
                    productReviews
                )
            } else {
                NetworkResult.HttpError(response.code())
            }
        } catch (unknownHostException: UnknownHostException) {
            NetworkResult.NoInternetConnection
        } catch (exception: Exception) {
            NetworkResult.NoInternetConnection
        }
    }

    override suspend fun postProductReview(productReview: ProductReview): NetworkResult {
        return try {
            val response = productServiceRetrofitAPI.postProductReview(
                String.format(
                    ProductReviewAPI.PRODUCT_REVIEW_BASE_URL,
                    productReview.productId
                ),
                productReview
            )
            if (response.isSuccessful) {
                val review = response.body()
                if (review != null) {
                    NetworkResult.Success.SingleProductReview(review)
                } else {
                    NetworkResult.Success.NoResponse
                }
            } else {
                NetworkResult.HttpError(response.code())
            }
        } catch (unknownHostException: UnknownHostException) {
            NetworkResult.NoInternetConnection
        } catch (exception: Exception) {
            NetworkResult.NoInternetConnection
        }
    }
}