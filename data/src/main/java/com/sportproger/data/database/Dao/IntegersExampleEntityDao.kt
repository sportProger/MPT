package com.sportproger.data.database.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.sportproger.data.database.Entity.IntegersExampleEntity

@Dao
interface IntegersExampleEntityDao {
    @Query("SELECT * FROM IntegersExampleEntity")
    fun getAll(): List<IntegersExampleEntity>

    @Insert
    fun insertAll(vararg users: IntegersExampleEntity)

    @Query("DELETE FROM IntegersExampleEntity")
    fun deleteAll()
}

