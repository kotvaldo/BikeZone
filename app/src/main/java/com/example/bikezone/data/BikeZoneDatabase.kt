package com.example.bikezone.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bikezone.data.items.Item
import com.example.bikezone.data.items.ItemDao
import com.example.bikezone.data.orders.Order
import com.example.bikezone.data.orders.OrderDao
import com.example.bikezone.data.users.User
import com.example.bikezone.data.users.UserDao


@Database(
    entities = [Item::class, User::class, Order::class],
    version = 1,
    exportSchema = false
)
abstract class BikeZoneDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao
    abstract fun userDao(): UserDao
    abstract fun orderDao(): OrderDao

    companion object {
        @Volatile
        private var Instance: BikeZoneDatabase? = null

        fun getDatabase(context: Context): BikeZoneDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, BikeZoneDatabase::class.java, "item_database")
                    /**
                     * Setting this option in your app's database builder means that Room
                     * permanently deletes all data from the tables in your database when it
                     * attempts to perform a migration with no defined migration path.
                     */
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
