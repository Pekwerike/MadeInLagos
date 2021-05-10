package com.pekwerike.madeinlagos.model

data class ProductReview(
    val productId: String,
    val locale: String,
    val rating: Int,
    val text: String
)
