package com.pekwerike.madeinlagos.network.impl


import com.pekwerike.madeinlagos.model.NetworkResult
import com.pekwerike.madeinlagos.model.Product
import com.pekwerike.madeinlagos.network.ProductServiceAPI
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import java.net.UnknownHostException
import javax.inject.Inject


@Suppress("BlockingMethodInNonBlockingContext")
class ProductService @Inject constructor(
    private val httpClient: HttpClient,
    private val moshi: Moshi,

    ) : ProductServiceAPI {

    @ExperimentalStdlibApi
    override suspend fun getAllProduct(): NetworkResult {
        return try {
            val response = httpClient.get<HttpResponse>(ProductServiceAPI.BASE_PRODUCT_URL) {
                headers {
                    append(HttpHeaders.AcceptLanguage, "en-US")
                }
            }
            when (response.status) {
                HttpStatusCode.OK -> {
                    val products =
                        moshi.adapter<List<Product>>().fromJson(response.readText()) ?: listOf()
                    NetworkResult.Success.AllProducts(products)
                }
                else -> NetworkResult.HttpError(response.status)
            }
        } catch (unknownHostException: UnknownHostException) {
            NetworkResult.NoInternetConnection
        } catch (exception: Exception) {
            NetworkResult.NoInternetConnection
        }
    }

    @ExperimentalStdlibApi
    override suspend fun createProduct(product: Product): NetworkResult {
        return try {
            val response = httpClient.post<HttpResponse>(ProductServiceAPI.BASE_PRODUCT_URL) {
                contentType(ContentType.Application.Json)
                body = product
                expectSuccess = true
            }

            when (response.status) {
                HttpStatusCode.OK -> {
                    NetworkResult.Success.NoResponse
                }
                HttpStatusCode.BadRequest -> {
                    NetworkResult.HttpError(HttpStatusCode.BadRequest)
                }
                else -> {
                    NetworkResult.NoInternetConnection
                }
            }
        } catch (unknownHostException: UnknownHostException) {
            NetworkResult.NoInternetConnection
        } catch (exception: Exception) {
            NetworkResult.NoInternetConnection
        }
    }

    @ExperimentalStdlibApi
    override suspend fun getProductById(productId: String): NetworkResult {
        return try {
            val response = httpClient.get<HttpResponse>(
                String.format(
                    ProductServiceAPI.SINGLE_PRODUCT_URL,
                    productId
                )
            ) {
                headers {
                    append(HttpHeaders.AcceptLanguage, "en-US")
                }
            }
            when (response.status) {
                HttpStatusCode.OK -> {
                    val product = moshi.adapter<Product>().fromJson(response.readText())
                    if (product != null) {
                        NetworkResult.Success.SingleProduct(product)
                    } else {
                        NetworkResult.Success.NoResponse
                    }
                }
                else -> {
                    NetworkResult.HttpError(response.status)
                }
            }
        } catch (unknownHostException: UnknownHostException) {
            NetworkResult.NoInternetConnection
        } catch (exception: Exception) {
            NetworkResult.NoInternetConnection
        }
    }

    @ExperimentalStdlibApi
    override suspend fun updateProduct(productId: String, product: Product): NetworkResult {
        return try {
            val response = httpClient.put<HttpResponse>(
                String.format(
                    ProductServiceAPI.SINGLE_PRODUCT_URL,
                    productId
                )
            ) {
                contentType(ContentType.Application.Json)
                body = product
                expectSuccess = true
            }
            when (response.status) {
                HttpStatusCode.OK -> {
                    val updatedProduct = moshi.adapter<Product>().fromJson(response.readText())
                    if (updatedProduct != null) {
                        NetworkResult.Success.SingleProduct(updatedProduct)
                    } else {
                        NetworkResult.Success.NoResponse
                    }
                }
                HttpStatusCode.BadRequest -> {
                    NetworkResult.HttpError(response.status)
                }
                else -> {
                    NetworkResult.NoInternetConnection
                }
            }
        } catch (unknownHostException: UnknownHostException) {
            NetworkResult.NoInternetConnection
        } catch (exception: Exception) {
            NetworkResult.NoInternetConnection

        }
    }

    override suspend fun deleteProduct(productId: String): NetworkResult {
        return try {
            val response = httpClient.delete<HttpResponse>(
                String.format(
                    ProductServiceAPI.SINGLE_PRODUCT_URL, productId
                )
            )
            when (response.status) {
                HttpStatusCode.OK -> {
                    NetworkResult.Success.DeletedProduct
                }
                HttpStatusCode.BadRequest -> {
                    NetworkResult.HttpError(response.status)
                }
                else -> {
                    NetworkResult.NoInternetConnection
                }
            }
        } catch (unknownHostException: UnknownHostException) {
            NetworkResult.NoInternetConnection
        } catch (exception: Exception) {
            NetworkResult.NoInternetConnection
        }
    }

}