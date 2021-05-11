package com.pekwerike.madeinlagos.model

import io.ktor.http.*

sealed class NetworkResult {
    sealed class Success : NetworkResult() {
        data class AllProducts(val products: List<Product>) : Success()
        data class SingleProduct(val product: Product) : Success()
        data class ProductReviews(val productReviews: List<ProductReview>) : Success()
        data class SingleProductReview(val productReview: ProductReview) : Success()
        object DeletedProduct : Success()
        object NoResponse : Success()
    }

    data class HttpError(val httpErrorStatusCode: HttpStatusCode) : NetworkResult()
    object NoInternetConnection : NetworkResult()
}
