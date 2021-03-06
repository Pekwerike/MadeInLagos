package com.pekwerike.madeinlagos.database

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDAO {

    @Transaction
    suspend fun refreshProductList(productEntityList: List<ProductEntity>) {
        deleteAllProductsWithReviews()
        insertAllProducts(productEntityList)
    }

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProducts(productEntityList: List<ProductEntity>)

    @Query("SELECT * FROM product_table WHERE id = :productId")
    fun getProductById(productId: String): Flow<ProductEntity>

    @Query("SELECT * FROM product_table")
    fun getAllProducts(): Flow<List<ProductEntity>>

    @Transaction
    @Query("SELECT * FROM product_table")
    fun getAllProductWithReviewsAsFlow(): Flow<List<ProductWithReviews>>

    @Transaction
    @Query("SELECT * FROM product_table")
    fun getAllProductWithReviewsAsLiveData(): LiveData<List<ProductWithReviews>>

    @Transaction
    @Query("SELECT * FROM product_table")
    fun getAllProductsWithReviews(): List<ProductWithReviews>

    @Transaction
    @Query("SELECT * FROM product_table WHERE id = :productId")
    fun getProductWithReviewsByProductId(productId: String): ProductWithReviews

    @Transaction
    @Query("DELETE FROM product_table")
    suspend fun deleteAllProductsWithReviews()
}