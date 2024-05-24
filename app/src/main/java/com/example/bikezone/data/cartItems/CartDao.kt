package com.example.bikezone.data.cartItems

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartItem: CartItem)

    @Update
    suspend fun update(cartItem: CartItem)

    @Delete
    suspend fun delete(cartItem: CartItem)

    @Query("SELECT * FROM cart_items WHERE id = :id")
    fun getCartItembyItem(id: Int): CartItem?
    @Query("SELECT * FROM cart_items WHERE itemId = :itemId")
    fun getCartItemByItemId(itemId: Int): CartItem?

    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): Flow<List<CartItem>>
    @Delete
    fun deleteAllItems(): Flow<List<CartItem>>
}