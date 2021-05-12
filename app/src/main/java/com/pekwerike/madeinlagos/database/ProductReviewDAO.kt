package com.pekwerike.madeinlagos.database

import androidx.room.Insert

interface ProductReviewDAO {

    @Insert
    suspend fun insertProductReview(productReviewEntity: ProductReviewEntity)

}