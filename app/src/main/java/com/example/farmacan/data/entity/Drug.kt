package com.example.farmacan.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Drug(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private val id: Int,
    @ColumnInfo(name = "name")
    private val name: String,
    @ColumnInfo(name = "genericName")
    private val genericName: String,
    @ColumnInfo(name = "brand")
    private val brand: String,
    @ColumnInfo(name = "type")
    private val type: String,
    @ColumnInfo(name = "description")
    private val description: String,
    @ColumnInfo(name = "price")
    private val price: String
) {
    fun getId(): Int {
        return id
    }
    fun getName(): String {
        return name
    }
    fun getGenericName(): String {
        return genericName
    }
    fun getBrand(): String {
        return brand
    }
    fun getType(): String {
        return type
    }
    fun getDescription(): String {
        return description
    }
    fun getPrice(): String {
        return price
    }
}
