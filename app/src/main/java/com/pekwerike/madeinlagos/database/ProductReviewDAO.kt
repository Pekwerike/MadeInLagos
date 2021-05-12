package com.pekwerike.madeinlagos.database


import kotlinx.coroutines.flow.Flow
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface ProductReviewDAO {
    @Insert
    suspend fun insertProductReviewEntity(productReviewEntity: ProductReviewEntity)

    @Insert
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