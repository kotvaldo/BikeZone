package com.example.bikezone.data.orders

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "order_id") val id: Int = 0,
    @ColumnInfo(name = "price") val price: Double,
    @ColumnInfo(name = "item_count") val count: Int,
    @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "order_date") val date: Date
)