package com.example.bikezone.data.orders

import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    suspend fun insert(order: Order)
    suspend fun delete(order: Order)
    suspend fun deleteAllByUserId(userId: Int)
    fun getOrdersByUserIdStream(userId: Int): Flow<List<Order>>
}