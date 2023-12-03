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
    @ColumnInfo(name = "type")
    private val type: String,
    @ColumnInfo(name = "description")
    private val description: String,
    @ColumnInfo(name = "packaging")
    private val packaging: String,
    @ColumnInfo(name = "price")
    private val price: String
) {
    fun getId(): Int {
        return id
    }
    fun getName(): String {
        return name
    }
    fun getType(): String {
        return type
    }
    fun getDescription(): String {
        return description
    }
    fun getPackaging(): String {
        return packaging
    }
    fun getPrice(): String {
        return price
    }
}
