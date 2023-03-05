package com.sportproger.data.database.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sportproger.data.database.Entity.ModulesExampleEntity

@Dao
interface ModulesExampleEntityDao {
    @Query("SELECT * FROM ModulesExampleEntity")
    fun getAll(): List<ModulesExampleEntity>

    @Insert
    fun insertAll(vararg users: ModulesExampleEntity)

    @Query("DELETE FROM ModulesExampleEntity")
    fun deleteAll()
}
