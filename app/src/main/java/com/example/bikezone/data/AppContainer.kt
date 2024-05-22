package com.example.bikezone.data

import android.content.Context
import com.example.bikezone.data.items.ItemRepository
import com.example.bikezone.data.items.OfflineItemRepozitory

interface AppContainer {
    val itemRepository : ItemRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val itemRepository: ItemRepository by lazy {
        OfflineItemRepozitory(BikeZoneDatabase.getDatabase(context).itemDao())
    }

}
