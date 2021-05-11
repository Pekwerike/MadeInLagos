package com.pekwerike.madeinlagos.model

sealed class NetworkResult {
    sealed class Success : NetworkResult() {
        data class AllProducts(val products: List<Product>) : Success()
        data class SingleProduct(val product: Product) : Success()
        object NoResponse : Success()
    }

    data class HttpError(val errorCode: Int) : NetworkResult()
    object NoInternetConnection : NetworkResult()
}
