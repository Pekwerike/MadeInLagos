package com.pekwerike.madeinlagos.model

data class Product(
    val id: String = "MIL12",
    val name: String = "product",
    val description: String = "description",
    val currency: String,
    val price: Int
)
