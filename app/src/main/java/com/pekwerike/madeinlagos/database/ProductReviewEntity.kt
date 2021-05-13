package com.pekwerike.madeinlagos.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.pekwerike.madeinlagos.model.ProductReview

@Entity(
    tableName = "product_review_table", foreignKeys = [ForeignKey(
        entity = ProductEntity::class, parentColumns = ["id"],
        childColumns = ["product_id"],
        onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE
    )]
)
data class ProductReviewEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "product_review_id")
    val productReviewId: Long = 0L,
    @ColumnInfo(name = "product_id", index = true)
    val productId: String,
    val locale: String = "en-US",
    val rating: Int = ProductReview.ProductRating.ZERO.ratingValue,
    val text: String
)
