package com.pekwerike.madeinlagos.model

data class ProductReview(
    val productId: String,
    val locale: String = "en-US",
    val rating: Int = ProductRating.ZERO.ratingValue,
    val text: String
)

enum class ProductRating(val ratingValue: Int) {
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5)
}
