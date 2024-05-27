package com.example.bikezone.data.cartItems

import kotlinx.coroutines.flow.Flow


class OfflineCartRepository(private val cartDao: CartDao) : CartRepository {
    override suspend fun insert(cartItem: CartItem) = cartDao.insert(cartItem)

    override suspend fun update(cartItem: CartItem) = cartDao.update(cartItem)

    override suspend fun delete(cartItem: CartItem) = cartDao.delete(cartItem)

    override suspend fun deleteAllItems(listOfItems: List<CartItem>) = cartDao.deleteAllItems(listOfItems)

    override fun getCartItemByIdStream(id: Int): Flow<CartItem?> = cartDao.getCartItembyId(id)

    override fun getCartItemByItemIdStream(itemId: Int): Flow<CartItem?> = cartDao.getCartItemByItemId(itemId)
    override fun getAllCartItemsStream(userId: Int): Flow<List<CartItem>> = cartDao.getAllCartItems(userId)

}