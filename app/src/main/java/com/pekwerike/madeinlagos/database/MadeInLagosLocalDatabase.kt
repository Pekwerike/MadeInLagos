package com.pekwerike.madeinlagos.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ProductEntity::class, ProductReviewEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MadeInLagosLocalDatabase : RoomDatabase() {
    abstract fun productDAO(): ProductDAO
    abstract fun productReviewDAO(): ProductReviewDAO

    companion object {
        @Volatile
        var INSTANCE: MadeInLagosLocalDatabase? = null

        fun getMadeInLagosLocalDatabaseInstance(context: Context): MadeInLagosLocalDatabase {
            return INSTANCE ?: synchronized(this) {
                val createdDb = Room.databaseBuilder(
                    context,
                    MadeInLagosLocalDatabase::class.java,
                    "made_in_lagos_local_database"
                ).build()
                INSTANCE = createdDb
                createdDb
            }
        }
    }
}