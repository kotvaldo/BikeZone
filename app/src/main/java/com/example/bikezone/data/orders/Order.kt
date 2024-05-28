package com.example.bikezone.data.orders

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.bikezone.data.users.User
import java.util.Date

@Entity(tableName = "orders",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["user_id"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.CASCADE
    )])


data class Order(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "order_id") val id: Int = 0,
    @ColumnInfo(name = "price") val price: Double,
    @ColumnInfo(name = "item_count") val count: Int,
    @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "order_date") val date: Date
)