package com.example.bikezone.data.cartItems

import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun insert(cartItem: CartItem)
    suspend fun update(cartItem: CartItem)
    suspend fun delete(cartItem: CartItem)
    suspend fun deleteAllItems(listOfItems: List<CartItem>)
    fun getCartItemByIdStream(id: Int): Flow<CartItem?>
    fun getCartItemByItemIdStream(itemId: Int): Flow<CartItem?>
    fun getAllCartItemsStream(userId:Int): Flow<List<CartItem>>

}