package com.example.bikezone.data.users

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "user_id") val id: Int,
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "email") val email: String = "",
    @ColumnInfo(name = "password") val password: String = "",
    @ColumnInfo(name = "address") val address: String = "",
    @ColumnInfo(name = "auth") val auth:Boolean = false
)


data class UserDetails(
    val id: Int = 0,
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val address: String = "",
    val auth:Boolean = false
)


fun UserDetails.toUser(): User = User(
    id = id,
    name = name,
    email = email   ,
    password = password,
    address = address,
    auth = auth
)

fun User.toUserDetails(): UserDetails = UserDetails(
    id = id,
    name = name,
    email = email   ,
    password = password,
    address = address,
    auth = auth
)