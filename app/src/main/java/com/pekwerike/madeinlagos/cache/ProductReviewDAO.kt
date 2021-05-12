package com.pekwerike.madeinlagos.cache

import androidx.room.Insert

interface ProductReviewDAO {

    @Insert
    fun insertProductReview(productReviewEntity: ProductReviewEntity)

}