package com.example.farmacan.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.farmacan.data.entity.User

@Dao
interface UserDao {
    @Query("select * from User where id = :id")
    fun getById(id: Int): User

    @Query("select * from User where name = :name")
    fun getByName(name: String?): User

    @Query("select * from User")
    fun getAll(): List<User>

    @Insert
    fun insert(user: User): Unit

    @Query("delete from User where id = :id")
    fun deleteById(id: Int): Unit

    @Query("delete from User where name = :name")
    fun deleteByName(name: String?): Unit
}