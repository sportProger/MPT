package com.sportproger.data.database.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sportproger.data.database.Entity.EquationExampleEntity

@Dao
interface EquationExampleEntityDao {
    @Query("SELECT * FROM EquationExampleEntity")
    fun getAll(): List<EquationExampleEntity>

    @Insert
    fun insertAll(vararg users: EquationExampleEntity)

    @Query("DELETE FROM EquationExampleEntity")
    fun deleteAll()
}