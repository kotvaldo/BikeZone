package com.example.bikezone.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bikezone.data.items.Item
import com.example.bikezone.data.items.ItemDao
import com.example.bikezone.data.users.User
import com.example.bikezone.data.users.UserDao

@Database(entities = [Item::class, User::class], version = 1, exportSchema = false)
abstract class BikeZoneDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: BikeZoneDatabase? = null

        fun getDatabase(context: Context): BikeZoneDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BikeZoneDatabase::class.java,
                    "bikezone_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}