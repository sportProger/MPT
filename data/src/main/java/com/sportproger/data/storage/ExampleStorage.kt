package com.sportproger.data.storage

import com.sportproger.data.database.Entity.*
import com.sportproger.data.storage.module.*
import com.sportproger.domain.module.DegreeExampleSaveData
import com.sportproger.domain.module.FractionExampleSaveData
import com.sportproger.domain.module.LinearFunctionExampleSaveData
import com.sportproger.domain.module.ModulesExampleSaveData

interface ExampleStorage {
    fun setIntegersExample(integersExample: IntegersExampleSaveDataStorage)
    fun getIntegersExample(): List<IntegersExampleEntity>
    fun removeIntegersExample()

    fun setModulesExample(modulesExample: ModulesExampleSaveDataStorage)
    fun getModulesExample(): List<ModulesExampleEntity>
    fun removeModulesExample()

    fun setFractionExample(fractionExample: FractionExampleSaveDataStorage)
    fun getFractionExample(): List<FractionsExampleEntity>
    fun removeFractionExample()

    fun setDegreeExample(degreeExample: DegreeExampleSaveDataStorage)
    fun getDegreeExample(): List<DegreeExampleEntity>
    fun removeDegreeExample()

    fun setLinearFunctionExample(linearExample: LinearFunctionExampleSaveDataStorage)
    fun getLinearFunctionExample(): List<LinearFunctionExampleEntity>
    fun removeLinearFunctionExample()

    fun setLogarithmExample(logarithmExample: LogarithmExampleSaveDataStorage)
    fun getLogarithmExample(): List<LogarithmExampleEntity>
    fun removeLogarithmExample()

    fun setNumberOfCorrectAnswers(count: Int, level: String)
    fun getNumberOfCorrectAnswers(level: String): Int
    fun removeNumberOfCorrectAnswers(level: String)

    fun setNumberOfWrongAnswers(count: Int, level: String)
    fun getNumberOfWrongAnswers(level: String): Int
    fun removeNumberOfWrongAnswers(level: String)
}