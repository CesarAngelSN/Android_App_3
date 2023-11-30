package com.example.farmacan.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.farmacan.data.dao.DrugDao
import com.example.farmacan.data.dao.UserDao
import com.example.farmacan.data.entity.Drug
import com.example.farmacan.data.entity.User

@Database(
    version = 1,
    entities = [User::class, Drug::class]
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getDrugDao(): DrugDao
}