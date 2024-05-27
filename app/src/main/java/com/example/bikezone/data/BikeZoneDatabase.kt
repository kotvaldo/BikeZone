package com.example.bikezone.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bikezone.data.cartItems.CartDao
import com.example.bikezone.data.cartItems.CartItem
import com.example.bikezone.data.items.Item
import com.example.bikezone.data.items.ItemDao
import com.example.bikezone.data.orders.Converters
import com.example.bikezone.data.orders.Order
import com.example.bikezone.data.orders.OrderDao
import com.example.bikezone.data.users.User
import com.example.bikezone.data.users.UserDao

@Database(entities = [Item::class, User::class, CartItem::class, Order::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class BikeZoneDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
    abstract fun userDao(): UserDao
    abstract fun cartDao(): CartDao
    abstract fun orderDao(): OrderDao

    companion object {
        @Volatile
        private var INSTANCE: BikeZoneDatabase? = null

        fun getDatabase(context: Context): BikeZoneDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BikeZoneDatabase::class.java,
                    "bikezone_dtb"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}