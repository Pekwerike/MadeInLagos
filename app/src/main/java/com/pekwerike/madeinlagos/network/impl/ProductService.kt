package com.pekwerike.madeinlagos.network.impl

import com.pekwerike.madeinlagos.model.NetworkResult
import com.pekwerike.madeinlagos.model.Product
import com.pekwerike.madeinlagos.network.ProductServiceAPI

class ProductService : ProductServiceAPI {
    override suspend fun getAllProduct(): NetworkResult {
        return NetworkResult.NoInterntConnection
    }

    override suspend fun createProduct(product: Product): NetworkResult {
        return NetworkResult.NoInterntConnection
    }

    override suspend fun getProductById(productId: String): NetworkResult {
        return NetworkResult.NoInterntConnection
    }

    override suspend fun updateProduct(productId: String, product: Product): NetworkResult {
        return NetworkResult.NoInterntConnection
    }

    override suspend fun deleteProduct(productId: String): NetworkResult {
        return NetworkResult.NoInterntConnection
    }

}