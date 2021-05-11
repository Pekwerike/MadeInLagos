package com.pekwerike.madeinlagos.network.impl


import com.pekwerike.madeinlagos.model.NetworkResult
import com.pekwerike.madeinlagos.model.Product
import com.pekwerike.madeinlagos.network.ProductServiceAPI
import com.squareup.moshi.JsonAdapter
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import javax.inject.Inject

class ProductService @Inject constructor(
    private val httpClient: HttpClient,
    private val moshiProductListAdapter: JsonAdapter<List<Product>>
) : ProductServiceAPI {

    override suspend fun getAllProduct(): NetworkResult {
        return try {
            val response = httpClient.get<HttpResponse>(ProductServiceAPI.ALL_PRODUCTS_URL) {
                headers {
                    append(HttpHeaders.AcceptLanguage, "en-US")
                }
            }
            when (response.status) {
                HttpStatusCode.OK -> {
                    val products = moshiProductListAdapter.fromJson(response.readText()) ?: listOf()
                    NetworkResult.Success.AllProducts(products)
                }
                else -> NetworkResult.NoInternetConnection
            }

        } catch (exception: Exception) {
            NetworkResult.NoInternetConnection
        }
    }

    override suspend fun createProduct(product: Product): NetworkResult {
        return NetworkResult.NoInternetConnection
    }

    override suspend fun getProductById(productId: String): NetworkResult {
        return NetworkResult.NoInternetConnection
    }

    override suspend fun updateProduct(productId: String, product: Product): NetworkResult {
        return NetworkResult.NoInternetConnection
    }

    override suspend fun deleteProduct(productId: String): NetworkResult {
        return NetworkResult.NoInternetConnection
    }

}