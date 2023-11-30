package com.example.farmacan.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private val id: Int,
    @ColumnInfo(name = "name")
    private val name: String,
    @ColumnInfo(name = "password")
    private val password: String
){
    fun getId(): Int {
        return id
    }
    fun getName(): String {
        return name
    }
    fun getPassword(): String {
        return password
    }
}
