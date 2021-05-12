package com.pekwerike.madeinlagos.database


import kotlinx.coroutines.flow.Flow
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductReviewDAO {
    @Insert
    suspend fun insertProductReview(productReviewEntity: ProductReviewEntity)

    @Query("SELECT * FROM product_review_table")
    fun getAllProductReview(): Flow<List<ProductReviewEntity>>
}