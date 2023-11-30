package com.example.farmacan.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.farmacan.data.entity.Drug

@Dao
interface DrugDao {
    @Query("select * from Drug where id = :id")
    fun getById(id: Int): Drug

    @Query("select * from Drug where name = :name")
    fun getByName(name: String?): Drug

    @Query("select * from Drug")
    fun getAll(): List<Drug>

    @Insert
    fun insert(drug: Drug): Unit

    @Query("delete from Drug where id = :id")
    fun deleteById(id: Int): Unit

    @Query("delete from Drug where name = :name")
    fun deleteByName(name: String?): Unit
}