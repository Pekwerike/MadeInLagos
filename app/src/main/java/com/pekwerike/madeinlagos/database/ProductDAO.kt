package com.pekwerike.madeinlagos.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDAO {

    @Transaction
    suspend fun refreshProductList(productEntityList: List<ProductEntity>){
        deleteAllProductsWithReviews()
        insertAllProducts(productEntityList)
    }

    @Insert
    suspend fun insertAllProducts(productEntityList: List<ProductEntity>)

    @Query("SELECT * FROM product_table WHERE product_id = :productId")
    fun getProductById(productId: String): Flow<ProductEntity>

    @Query("SELECT * FROM product_table")
    fun getAllProducts(): Flow<List<ProductEntity>>

    @Transaction
    @Query("SELECT * FROM product_with_reviews_table")
    fun getAllProductWithReviews(): Flow<List<ProductWithReviews>>

    @Transaction
    @Query("SELECT * FROM product_with_reviews_table WHERE product_id = :productId")
    fun getProductWithReviews(productId: String): Flow<ProductWithReviews>

    @Transaction
    @Query("DELETE FROM product_with_reviews_table")
    suspend fun deleteAllProductsWithReviews()
}