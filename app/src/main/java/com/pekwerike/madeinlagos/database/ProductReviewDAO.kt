package com.pekwerike.madeinlagos.database


import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductReviewDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductReviewEntity(productReviewEntity: ProductReviewEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductReviewEntityList(productReviewEntity: List<ProductReviewEntity>)

    @Query("SELECT * FROM product_review_table ORDER BY product_review_id DESC")
    fun getAllProductReviews(): Flow<List<ProductReviewEntity>>

    @Query("DELETE FROM product_review_table WHERE product_id =:productId")
    fun deleteAllProductReviewsEntityByProductId(productId: String)

    @Transaction
    suspend fun refreshProductReviewsByProductId(
        productId: String,
        productReviewEntity: List<ProductReviewEntity>
    ) {
        deleteAllProductReviewsEntityByProductId(productId)
        insertProductReviewEntityList(productReviewEntity)
    }
}