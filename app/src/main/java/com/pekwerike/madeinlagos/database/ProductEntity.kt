package com.pekwerike.madeinlagos.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "product_table"
)
data class ProductEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val productId: String = "MIL12",
    val name: String = "product",
    val description: String = "description",
    val currency: String,
    val price: Int,
    val productImageUrl: String = "https://assets.adidas.com/images/h_840,f_auto,q_auto:sensitive,fl_lossy/4185ecdd40674577a45fac07012ac1e4_9366/Superlite_Trainer_Hat_Pink_EW9710_01_standard.jpg"
)