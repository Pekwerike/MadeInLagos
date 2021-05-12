package com.pekwerike.madeinlagos.mappers

import com.pekwerike.madeinlagos.database.ProductEntity
import com.pekwerike.madeinlagos.database.ProductReviewEntity
import com.pekwerike.madeinlagos.database.ProductWithReviews
import com.pekwerike.madeinlagos.model.Product
import com.pekwerike.madeinlagos.model.ProductReview

/*
This file contains extension functions that makes it easier to convert between RoomDB Entities
and normal kotlin data classes
*/

fun List<ProductEntity>.toProductList(): List<Product> = map { productEntity: ProductEntity ->
    Product(
        id = productEntity.productId,
        name = productEntity.name,
        description = productEntity.description,
        currency = productEntity.currency,
        price = productEntity.price
    )
}


fun List<Product>.toProductEntityList(): List<ProductEntity> = map { product: Product ->
    ProductEntity(
        productId = product.id,
        name = product.name,
        description = product.description,
        currency = product.currency,
        price = product.price
    )
}

fun List<ProductWithReviews>.reviewtoProductList(): List<Product> =
    map { productWithReviews: ProductWithReviews ->
        Product(
            id = productWithReviews.product.productId,
            name = productWithReviews.product.name,
            description = productWithReviews.product.description,
            currency = productWithReviews.product.currency,
            price = productWithReviews.product.price,
            productReviews = productWithReviews.reviews.toProductReviewList()
        )
    }

fun List<ProductReviewEntity>.toProductReviewList(): List<ProductReview> =
    map { productReviewEntity: ProductReviewEntity ->
        ProductReview(
            productId = productReviewEntity.productId,
            locale = productReviewEntity.locale,
            rating = productReviewEntity.rating,
            text = productReviewEntity.text
        )
    }

fun List<ProductReview>.productReviewToProductReviewEntityList(): List<ProductReviewEntity> =
    map { productReview: ProductReview ->
        ProductReviewEntity(
            productId = productReview.productId,
            rating = productReview.rating,
            text = productReview.text
        )
    }

fun ProductWithReviews.productWithReviewsToProduct(): Product =
    Product(
        id = product.productId,
        name = product.name,
        description = product.description,
        currency = product.currency,
        price = product.price,
        productReviews = reviews.toProductReviewList()
    )

fun ProductEntity.toProduct(): Product = Product(
    id = productId,
    name = name,
    description = description,
    currency = currency,
    price = price
)

fun ProductReview.productReviewToProductReviewEntity(): ProductReviewEntity =
    ProductReviewEntity(
        productId = productId,
        rating = rating,
        text = text
    )

fun Product.toProductEntity(): ProductEntity = ProductEntity(
    productId = id,
    name = name,
    description = description,
    currency = currency,
    price = price
)

