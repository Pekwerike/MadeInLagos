package com.pekwerike.madeinlagos.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.PrimaryKey

@Entity(tableName = "product_table")
data class ProductEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "product_id")
    val productId: String = "MIL12",
    val name: String = "product",
    val description: String = "description",
    val currency: String,
    val price: Int
)