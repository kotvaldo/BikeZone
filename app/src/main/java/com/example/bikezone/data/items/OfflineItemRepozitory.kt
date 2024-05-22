package com.example.bikezone.data.items

import kotlinx.coroutines.flow.Flow

class OfflineItemRepozitory(private val itemDao: ItemDao) : ItemRepository {
    override fun getAllItemsStream(): Flow<List<Item>> = itemDao.getAllItems()

    override fun getItemByIdStream(id: Int): Flow<Item?>  = itemDao.getItemById(id)

    override fun getItemsBySubString(subString : String): Flow<List<Item>> = itemDao.getItemsBySubString(subString)

    override suspend fun insertItem(item: Item) = itemDao.insert(item)

    override suspend fun deleteItem(item: Item) = itemDao.delete(item)

    override suspend fun updateItem(item: Item) = itemDao.update(item)
}