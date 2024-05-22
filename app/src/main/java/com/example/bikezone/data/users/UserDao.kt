package com.example.bikezone.data.users

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.bikezone.data.items.Item
import kotlinx.coroutines.flow.Flow
@Dao
interface UserDao {

    @Query("SELECT * from users WHERE auth = :auth")
    fun getAuthUser(auth: Boolean): Flow<User?>
    @Query("SELECT * from users WHERE user_id = :id")
    fun getUserById(id: Int): Flow<User>
    @Query("SELECT * from users WHERE email = :email and password = :password")
    fun getUserByEmailAndPassword(email:String, password:String): Flow<User?>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)
}