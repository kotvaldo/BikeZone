package com.example.bikezone.data.users

import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getAuthUserStream(auth : Boolean): Flow<User?>
    fun getUserByIdStream(id : Int): Flow<User?>
    fun getUserByEmailAndPasswordStream(email:String, password:String): Flow<User?>
    fun getUserByEmailStream(email:String): Flow<User?>

    suspend fun insert(user: User)

    suspend fun delete(user: User)

    suspend fun update(user: User)
}