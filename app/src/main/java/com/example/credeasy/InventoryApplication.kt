package com.example.credeasy

import android.app.Application
import com.example.credeasy.data.ItemRoomDatabase

class InventoryApplication : Application() {

    val database: ItemRoomDatabase by lazy { ItemRoomDatabase.getDatabase(this) }
}
