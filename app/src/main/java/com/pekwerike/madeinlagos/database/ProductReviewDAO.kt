package com.pekwerike.madeinlagos.database

import androidx.room.Insert

interface ProductReviewDAO {

    @Insert
    fun insertProductReview(productReviewEntity: ProductReviewEntity)

}