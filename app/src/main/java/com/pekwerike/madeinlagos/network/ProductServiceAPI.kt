package com.pekwerike.madeinlagos.network

import com.pekwerike.madeinlagos.model.NetworkResult
import com.pekwerike.madeinlagos.model.Product

interface ProductServiceAPI {
    companion object {
        const val BASE_PRODUCT_URL = "https://tour.silent.ws/product/"
        const val SINGLE_PRODUCT_URL = "https://tour.silent.ws/product/%s/"
    }

    suspend fun getAllProduct(): NetworkResult
    suspend fun createProduct(product: Product): NetworkResult
    suspend fun getProductById(productId: String): NetworkResult
    suspend fun updateProduct(productId: String, product: Product): NetworkResult
    suspend fun deleteProduct(productId: String): NetworkResult
}