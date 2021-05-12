package com.pekwerike.madeinlagos.mappers

import com.pekwerike.madeinlagos.database.ProductEntity
import com.pekwerike.madeinlagos.model.Product

/*
This file contains extension functions that it easier to convert between ProductEntity
and Product types
*/

fun List<ProductEntity>.toProductList(): List<Product> {
    return map { productEntity: ProductEntity ->
        Product(
            id = productEntity.productId,
            name = productEntity.name,
            description = productEntity.description,
            currency = productEntity.currency,
            price = productEntity.price
        )
    }
}

fun List<Product>.toProductEntityList(): List<ProductEntity> {
    return map { product: Product ->
        ProductEntity(
            productId = product.id,
            name = product.name,
            description = product.description,
            currency = product.currency,
            price = product.price
        )
    }
}

fun ProductEntity.toProduct(): Product {
    return Product(
        id = productId,
        name = name,
        description = description,
        currency = currency,
        price = price
    )
}

fun Product.toProductEntity(): ProductEntity {
    return ProductEntity(
        productId = id,
        name = name,
        description = description,
        currency = currency,
        price = price
    )
}

