package com.example.bikezone.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "orders", foreignKeys = [
    ForeignKey(entity = User::class, parentColumns = ["user_id"], childColumns = ["user_id"], onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)
])
data class Order(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "order_id") val id: Int = 0,
    @ColumnInfo(name = "price") val price: Float = 0.0f,
    @ColumnInfo(name = "item_count") val count:Int = 0,
    @ColumnInfo(name = "user_id") val userId:Int,
)