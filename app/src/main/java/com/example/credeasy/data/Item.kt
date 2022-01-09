package com.example.credeasy.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val itemName: String,
    @ColumnInfo(name = "credit")
    val itemCredit: Double,
    @ColumnInfo(name = "address")
    val itemAddress: String,
    @ColumnInfo(name = "phone_no")
    var itemNumber: String
)

fun Item.getFormattedPrice(): String = "₹$itemCredit"