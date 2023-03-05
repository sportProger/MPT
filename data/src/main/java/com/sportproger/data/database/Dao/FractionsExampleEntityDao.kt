package com.sportproger.data.database.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sportproger.data.database.Entity.FractionsExampleEntity
import com.sportproger.data.database.Entity.IntegersExampleEntity

@Dao
interface FractionsExampleEntityDao {
    @Query("SELECT * FROM FractionsExampleEntity")
    fun getAll(): List<FractionsExampleEntity>

    @Insert
    fun insertAll(vararg users: FractionsExampleEntity)

    @Query("DELETE FROM FractionsExampleEntity")
    fun deleteAll()
}

