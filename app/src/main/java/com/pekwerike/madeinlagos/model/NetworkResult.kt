package com.pekwerike.madeinlagos.model

import io.ktor.http.*

sealed class NetworkResult {
    sealed class Success : NetworkResult() {
        data class AllProducts(val products: List<Product>) : Success()
        data class SingleProduct(val product: Product) : Success()
        object DeletedProduct : Success()
        object NoResponse : Success()
    }

    data class HttpError(val httpErrorStatusCode: HttpStatusCode) : NetworkResult()
    object NoInternetConnection : NetworkResult()
}
