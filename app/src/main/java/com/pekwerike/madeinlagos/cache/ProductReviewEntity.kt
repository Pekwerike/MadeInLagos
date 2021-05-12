package com.pekwerike.madeinlagos.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pekwerike.madeinlagos.model.ProductRating

@Entity(tableName = "product_review_table")
data class ProductReviewEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "product_review_id")
    val productReviewId: Long = 0L,
    @ColumnInfo(name = "product_id")
    val productId: String,
    val locale: String = "en-US",
    val rating: Int = ProductRating.ZERO.ratingValue,
    val text: String
)
