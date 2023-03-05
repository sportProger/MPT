package com.sportproger.data.database.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sportproger.data.database.Entity.DegreeExampleEntity
import com.sportproger.data.database.Entity.FractionsExampleEntity

@Dao
interface DegreeExampleEntityDao {
    @Query("SELECT * FROM DegreeExampleEntity")
    fun getAll(): List<DegreeExampleEntity>

    @Insert
    fun insertAll(vararg users: DegreeExampleEntity)

    @Query("DELETE FROM DegreeExampleEntity")
    fun deleteAll()
}

