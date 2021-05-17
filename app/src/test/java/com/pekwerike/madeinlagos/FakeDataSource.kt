package com.pekwerike.madeinlagos

import com.pekwerike.madeinlagos.model.Product
import com.pekwerike.madeinlagos.model.ProductReview

object FakeDataSource {

    val productList: List<Product> = listOf(
        Product(
            id = "F323",
            name = "product",
            description = "description",
            currency = "$",
            price = 20,
            productImageUrl = "https://github.com",
            productReviews = listOf(
                ProductReview(
                    productId = "F323",
                    locale = "en-US",
                    rating = 5,
                    text = "Amazing"
                ),
                ProductReview(
                    productId = "F323",
                    locale = "en-US",
                    rating = 4,
                    text = "Great"
                )
            )
        ),
        Product(
            id = "F3231",
            name = "product",
            description = "description",
            currency = "$",
            price = 20,
            productImageUrl = "https://github.com",
            productReviews = listOf(
                ProductReview(
                    productId = "F3231",
                    locale = "en-US",
                    rating = 5,
                    text = "Amazing"
                ),
                ProductReview(
                    productId = "F3231",
                    locale = "en-US",
                    rating = 4,
                    text = "Great"
                )
            )
        ),
        Product(
            id = "F3232",
            name = "product",
            description = "description",
            currency = "$",
            price = 20,
            productImageUrl = "https://github.com",
            productReviews = listOf(
                ProductReview(
                    productId = "F3232",
                    locale = "en-US",
                    rating = 5,
                    text = "Lovely product"
                ),
                ProductReview(
                    productId = "F3232",
                    locale = "en-US",
                    rating = 1,
                    text = "Terrible experience"
                )
            )
        ),
        Product(
            id = "F32",
            name = "product",
            description = "description",
            currency = "$",
            price = 20,
            productImageUrl = "https://github.com",
            productReviews = listOf(
                ProductReview(
                    productId = "F32",
                    locale = "en-US",
                    rating = 5,
                    text = "Leemao"
                ),
                ProductReview(
                    productId = "F32",
                    locale = "en-US",
                    rating = 4,
                    text = "Great stuff"
                )
            )
        )
    )

    val productReviewList : List<ProductReview> = listOf(
        ProductReview(
            productId = "F32",
            locale = "en-US",
            rating = 3,
            text = "Great stuff"
        ),
        ProductReview(
            productId = "F32",
            locale = "en-US",
            rating = 5,
            text = "Leemao"
        ),
        ProductReview(
            productId = "F3232",
            locale = "en-US",
            rating = 5,
            text = "Lovely product"
        ),
        ProductReview(
            productId = "F3232",
            locale = "en-US",
            rating = 1,
            text = "Terrible experience"
        ),
        ProductReview(
            productId = "F3231",
            locale = "en-US",
            rating = 5,
            text = "Amazing"
        ),
        ProductReview(
            productId = "F3231",
            locale = "en-US",
            rating = 4,
            text = "Great"
        ),
        ProductReview(
            productId = "F323",
            locale = "en-US",
            rating = 5,
            text = "Amazing"
        ),
        ProductReview(
            productId = "F323",
            locale = "en-US",
            rating = 4,
            text = "Great"
        ),
        ProductReview(
            productId = "G435",
            locale = "en-US",
            rating = 4,
            text = "Cool"
        )
    )
}