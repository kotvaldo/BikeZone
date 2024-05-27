package com.example.bikezone.data.orders

import kotlinx.coroutines.flow.Flow


class OfflineOrderRepository(private val orderDao: OrderDao) : OrderRepository{
    override suspend fun insert(order: Order) = orderDao.insert(order)

    override suspend fun delete(order: Order) = orderDao.delete(order)

    override suspend fun deleteAllByUserId(userId: Int) = orderDao.deleteAllByUserId(userId)

    override fun getOrdersByUserIdStream(userId: Int): Flow<List<Order>> = orderDao.getOrdersByUserId(userId)

}