package com.pekwerike.madeinlagos.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.pekwerike.madeinlagos.model.Product

/*
ProductWithReview data class defines the one-many relationship between a product and its corresponding
list of reviews
*/
data class ProductWithReviews(
    @Embedded val product: ProductEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "product_id"
    )
    val reviews: List<ProductReviewEntity>
)
