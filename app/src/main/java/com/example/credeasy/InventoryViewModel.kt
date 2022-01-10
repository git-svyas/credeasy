package com.example.credeasy

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.credeasy.data.Item
import com.example.credeasy.data.ItemDao
import kotlinx.coroutines.launch


class InventoryViewModel(private val itemDao: ItemDao) : ViewModel() {

    val allItems: LiveData<List<Item>> = itemDao.getItems().asLiveData()

    fun updateItem(
        itemId: Int,
        itemName: String,
        itemCredit: String,
        itemAddress: String,
        itemNumber: String
    ) {
        val updatedItem = getUpdatedItemEntry(itemId, itemName, itemCredit, itemAddress,itemNumber)
        updateItem(updatedItem)
    }

    private fun updateItem(item: Item) {
        viewModelScope.launch {
            itemDao.update(item)
        }
    }

    fun addNewItem(itemName: String, itemCredit: String, itemAddress: String,itemNumber: String) {
        val newItem = getNewItemEntry(itemName, itemCredit, itemAddress,itemNumber)
        insertItem(newItem)
    }

    private fun insertItem(item: Item) {
        viewModelScope.launch {
            itemDao.insert(item)
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {
            itemDao.delete(item)
        }
    }

    fun retrieveItem(id: Int): LiveData<Item> {
        return itemDao.getItem(id).asLiveData()
    }

    fun isEntryValid(itemName: String, itemCredit: String, itemAddress: String,itemNumber: String): Boolean {
        if (itemName.isBlank() || itemCredit.isBlank() || itemAddress.isBlank() || itemNumber.isBlank()) {
            return false
        }
        return true
    }

    private fun getNewItemEntry(
        itemName: String,
        itemCredit: String,
        itemAddress: String,
        itemNumber: String
    ): Item {
        return Item(
            itemName = itemName,
            itemCredit = itemCredit.toDouble(),
            itemAddress = itemAddress,
            itemNumber = itemNumber,
        )
    }

    private fun getUpdatedItemEntry(
        itemId: Int,
        itemName: String,
        itemCredit: String,
        itemAddress: String,
        itemNumber: String
    ): Item {
        return Item(
            id = itemId,
            itemName = itemName,
            itemCredit = itemCredit.toDouble(),
            itemAddress = itemAddress,
            itemNumber = itemNumber,
        )
    }
}

class InventoryViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InventoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InventoryViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

