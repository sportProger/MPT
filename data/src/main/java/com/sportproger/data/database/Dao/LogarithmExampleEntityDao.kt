package com.sportproger.data.database.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sportproger.data.database.Entity.LogarithmExampleEntity

@Dao
interface LogarithmExampleEntityDao {
    @Query("SELECT * FROM LogarithmExampleEntity")
    fun getAll(): List<LogarithmExampleEntity>

    @Insert
    fun insertAll(vararg users: LogarithmExampleEntity)

    @Query("DELETE FROM LogarithmExampleEntity")
    fun deleteAll()
}