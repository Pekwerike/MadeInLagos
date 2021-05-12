package com.pekwerike.madeinlagos.database


import kotlinx.coroutines.flow.Flow
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.pekwerike.madeinlagos.model.ProductReview

@Dao
interface ProductReviewDAO {
    @Insert
    suspend fun insertProductReviewEntity(productReviewEntity: ProductReviewEntity)

    @Insert
    suspend fun insertProductReviewEntityList(productReviewEntity: List<ProductReviewEntity>)

    @Query("SELECT * FROM product_review_table ORDER BY product_review_id DESC")
    fun getAllProductReviews(): Flow<List<ProductReviewEntity>>
}