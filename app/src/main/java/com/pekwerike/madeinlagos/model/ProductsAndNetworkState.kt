package com.pekwerike.madeinlagos.model

data class ProductsAndNetworkState(
    val productList: List<Product>,
    val networkState: NetworkResult
)