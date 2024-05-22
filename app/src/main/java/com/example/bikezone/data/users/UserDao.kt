package com.example.bikezone.data.users

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.bikezone.data.items.Item
import kotlinx.coroutines.flow.Flow

interface UserDao {

    @Query("SELECT * from items WHERE id = :id")
    fun getItemById(id: Int): Flow<Item>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)
}