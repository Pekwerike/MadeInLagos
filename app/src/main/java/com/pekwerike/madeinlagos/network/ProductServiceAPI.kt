package com.pekwerike.madeinlagos.network

import com.pekwerike.madeinlagos.model.NetworkResult
import com.pekwerike.madeinlagos.model.Product

interface ProductServiceAPI {
    suspend fun getAllProduct(): NetworkResult
    suspend fun createProduct(product: Product): NetworkResult
    suspend fun getProductById(productId: String): NetworkResult
    suspend fun updateProduct(productId: String, product: Product): NetworkResult
    suspend fun deleteProduct(productId: String): NetworkResult
}