package com.example.bikezone.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "user_id") val id: Int,
    @ColumnInfo(name = "name") val name: String? = "",
    @ColumnInfo(name = "email") val email: String? = "",
    @ColumnInfo(name = "password") val password: String? = "",
    @ColumnInfo(name = "address") val address: String? = ""
)