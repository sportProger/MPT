package com.sportproger.data.database.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sportproger.data.database.Entity.IntegersExampleEntity
import com.sportproger.data.database.Entity.LinearFunctionExampleEntity

@Dao
interface LinearFunctionExampleEntityDao {
    @Query("SELECT * FROM LinearFunctionExampleEntity")
    fun getAll(): List<LinearFunctionExampleEntity>

    @Insert
    fun insertAll(vararg users: LinearFunctionExampleEntity)

    @Query("DELETE FROM LinearFunctionExampleEntity")
    fun deleteAll()
}