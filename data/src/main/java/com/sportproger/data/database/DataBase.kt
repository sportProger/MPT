package com.sportproger.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sportproger.data.database.Dao.*
import com.sportproger.data.database.Entity.*

@Database(entities = [
    IntegersExampleEntity::class,
    ModulesExampleEntity::class,
    FractionsExampleEntity::class,
    EquationExampleEntity::class,
    DegreeExampleEntity::class,
    RootExampleEntity::class,
    LinearFunctionExampleEntity::class,
    LogarithmExampleEntity::class
], version = 1)
abstract class DataBase : RoomDatabase() {
    abstract fun integersExampleDao(): IntegersExampleEntityDao
    abstract fun modulesExampleDao(): ModulesExampleEntityDao
    abstract fun fractionsExampleDao(): FractionsExampleEntityDao
    abstract fun equationExampleDao(): EquationExampleEntityDao
    abstract fun degreeExampleDao(): DegreeExampleEntityDao
    abstract fun rootExampleDao(): RootExampleEntityDao
    abstract fun linearFunctionDao(): LinearFunctionExampleEntityDao
    abstract fun logarithmExampleDao(): LogarithmExampleEntityDao
}