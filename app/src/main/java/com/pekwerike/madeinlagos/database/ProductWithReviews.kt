package com.pekwerike.madeinlagos.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

/*
ProductWithReview data class defines the one-many relationship between a product and its corresponding
list of reviews
*/
@Entity(tableName = "product_with_reviews_table")
data class ProductWithReviews(
    @Embedded val product: ProductEntity,
    @Relation(
        parentColumn = "product_id",
        entityColumn = "product_review_id"
    )
    val reviews: List<ProductReviewEntity>
)
