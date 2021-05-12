package com.pekwerike.madeinlagos.cache

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

interface ProductDAO {

    @Transaction
    fun refreshProductList(productEntityList: List<ProductEntity>){
        deleteAllProductsWithReviews()
        insertAllProducts(productEntityList)
    }

    @Insert
    fun insertAllProducts(productEntityList: List<ProductEntity>)

    @Query("SELECT * FROM product_table WHERE product_id = :productId")
    fun getProductById(productId: String): LiveData<ProductEntity>

    @Query("SELECT * FROM product_table")
    fun getAllProducts(): LiveData<List<ProductEntity>>

    @Transaction
    @Query("SELECT * FROM product_with_reviews_table")
    fun getAllProductWithReviews(): LiveData<List<ProductWithReviews>>

    @Transaction
    @Query("SELECT * FROM product_with_reviews_table WHERE product_id = :productId")
    fun getProductWithReviews(productId: String): LiveData<ProductWithReviews>

    @Transaction
    @Query("DELETE FROM product_with_reviews_table")
    fun deleteAllProductsWithReviews()
}