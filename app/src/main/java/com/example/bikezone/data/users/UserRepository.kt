package com.example.bikezone.data.users

import com.example.bikezone.data.items.Item
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getAuthUserStream(auth : Boolean): Flow<User?>
    fun getUserByIdStream(id : Int): Flow<User?>
    fun getUserByEmailAndPasswordStream(email:String, password:String): Flow<User?>
    fun getUserByEmailStream(email:String): Flow<User?>

    suspend fun insertItem(user: User)

    suspend fun deleteItem(user: User)

    suspend fun updateItem(user: User)
}