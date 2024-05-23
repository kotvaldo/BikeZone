package com.example.bikezone.data.items

import kotlinx.coroutines.flow.Flow

interface ItemRepository {

    fun getAllItemsStream(): Flow<List<Item>>


    fun getItemByIdStream(id: Int): Flow<Item?>
    suspend fun getItemsBySubString(subString : String): Flow<List<Item>>

    suspend fun insertItem(item: Item)

    suspend fun deleteItem(item: Item)

    suspend fun updateItem(item: Item)
}
