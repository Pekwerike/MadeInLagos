package com.pekwerike.madeinlagos.database

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface ProductReviewDAO {

    @Insert
    suspend fun insertProductReview(productReviewEntity: ProductReviewEntity)

}