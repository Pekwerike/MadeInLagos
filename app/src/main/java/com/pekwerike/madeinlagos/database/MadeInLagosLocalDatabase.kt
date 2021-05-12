package com.pekwerike.madeinlagos.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ProductEntity::class, ProductReviewEntity::class], version = 1, exportSchema = false)
abstract class MadeInLagosLocalDatabase : RoomDatabase() {
    abstract fun productDAO(): ProductDAO
    abstract fun productReviewDAO(): ProductReviewDAO
}