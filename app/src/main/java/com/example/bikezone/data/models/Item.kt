package com.example.bikezone.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "items", foreignKeys = [
    ForeignKey(entity = Category::class, parentColumns = ["category_id"], childColumns = ["category_id"], onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)
])

data class Item(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "item_id") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String? = "",
    @ColumnInfo(name = "price") val price: Float = 0.0f,
    @ColumnInfo(name = "category_id") val categoryId: Int
)