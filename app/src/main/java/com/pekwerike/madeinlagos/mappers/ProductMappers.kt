package com.pekwerike.madeinlagos.mappers

import com.pekwerike.madeinlagos.database.ProductEntity
import com.pekwerike.madeinlagos.model.Product

/*
This file contains extension functions that makes it easier to convert between ProductEntity
and Product types
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


fun ProductEntity.toProduct(): Product = Product(
    id = productId,
    name = name,
    description = description,
    currency = currency,
    price = price
)


fun Product.toProductEntity(): ProductEntity = ProductEntity(
    productId = id,
    name = name,
    description = description,
    currency = currency,
    price = price
)

