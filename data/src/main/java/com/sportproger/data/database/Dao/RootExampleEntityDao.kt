package com.sportproger.data.database.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sportproger.data.database.Entity.IntegersExampleEntity
import com.sportproger.data.database.Entity.RootExampleEntity

@Dao
interface RootExampleEntityDao {
    @Query("SELECT * FROM RootExampleEntity")
    fun getAll(): List<RootExampleEntity>

    @Insert
    fun insertAll(vararg users: RootExampleEntity)

    @Query("DELETE FROM RootExampleEntity")
    fun deleteAll()
}