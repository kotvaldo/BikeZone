package com.example.bikezone.data.items

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bikezone.R


@Entity(tableName = "items")

data class Item(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "price") val price: Double,
    @ColumnInfo(name = "picture") val picture: Int,
    @ColumnInfo(name = "desc") val desc: Int = R.string.str_desc_of_item,
)