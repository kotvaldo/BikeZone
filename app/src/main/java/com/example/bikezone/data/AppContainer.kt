package com.example.bikezone.data

import android.content.Context
import com.example.bikezone.data.items.ItemRepository
import com.example.bikezone.data.items.OfflineItemRepozitory
import com.example.bikezone.data.users.OfflineUserRepository
import com.example.bikezone.data.users.UserRepository

interface AppContainer {
    val itemRepository : ItemRepository
    val userRepository : UserRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val itemRepository: ItemRepository by lazy {
        OfflineItemRepozitory(BikeZoneDatabase.getDatabase(context).itemDao())
    }
    override val userRepository: UserRepository by lazy {
        OfflineUserRepository(BikeZoneDatabase.getDatabase(context).userDao())
    }

}
