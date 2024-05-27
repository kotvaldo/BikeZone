package com.example.bikezone.data.orders

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {

    @Insert
    suspend fun insert(order: Order)

    @Delete
    suspend fun delete(order: Order)

    @Query("DELETE FROM orders WHERE user_id = :userId")
    suspend fun deleteAllByUserId(userId: Int)

    @Query("SELECT * FROM orders WHERE user_id = :userId")
    fun getOrdersByUserId(userId: Int): Flow<List<Order>>
}