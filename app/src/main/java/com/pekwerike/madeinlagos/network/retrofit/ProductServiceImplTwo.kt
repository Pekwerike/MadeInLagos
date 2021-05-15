package com.pekwerike.madeinlagos.network.retrofit

import com.pekwerike.madeinlagos.model.NetworkResult
import com.pekwerike.madeinlagos.model.Product
import com.pekwerike.madeinlagos.model.ProductReview
import com.pekwerike.madeinlagos.network.ProductReviewAPI
import com.pekwerike.madeinlagos.network.ProductServiceAPI

class ProductServiceImplTwo(productId: String): ProductServiceAPI, ProductReviewAPI{
    override suspend fun getAllProduct(): NetworkResult {

    }

    override suspend fun createProduct(product: Product): NetworkResult {
        TODO("Not yet implemented")
    }

    override suspend fun getProductById(productId: String): NetworkResult {
        TODO("Not yet implemented")
    }

    override suspend fun updateProduct(productId: String, product: Product): NetworkResult {
        TODO("Not yet implemented")
    }

    override suspend fun deleteProduct(productId: String): NetworkResult {
        TODO("Not yet implemented")
    }

    override suspend fun getProductReviews(productId: String): NetworkResult {
        TODO("Not yet implemented")
    }

    override suspend fun postProductReview(productReview: ProductReview): NetworkResult {
        TODO("Not yet implemented")
    }
}