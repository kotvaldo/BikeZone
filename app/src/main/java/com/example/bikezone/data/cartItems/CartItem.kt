package com.example.bikezone.data.cartItems

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.bikezone.data.items.Item
import com.example.bikezone.data.users.User

@Entity(
    tableName = "cart_items",
    foreignKeys = [ForeignKey(
        entity = Item::class,
        parentColumns = ["id"],
        childColumns = ["itemId"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = User::class,
        parentColumns = ["user_id"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class CartItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val itemId: Int,
    val count: Int,
    val userId: Int
)