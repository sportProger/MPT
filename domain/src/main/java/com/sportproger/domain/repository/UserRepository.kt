package com.sportproger.domain.repository

import com.sportproger.domain.module.*

interface UserRepository {
    fun isFirstStart(): Boolean

    fun setNumberOfCoins(numberOfCoins: Int)
    fun getNumberOfCoins(): Int

    fun setCurrentTheme(currentTheme: String)
    fun getCurrentTheme(): String

    fun setLevel(level: String)
    fun getLevel(): String

    fun setSounds(sounds: Boolean)
    fun getSounds(): Boolean

    fun setPurchaseStatusForTheme(themeName: String)
    fun getPurchaseStatusForTheme(themeName: String): Boolean

    fun setTypeNumbers(typeNumbersData: TypeNumbersData)
    fun getTypeNumbers(): TypeNumbersData

    fun setNotificationTime(notificationTime: NotificationTime)
    fun getNotificationTime(): NotificationTime

    fun setNotificationState(flag: Boolean)
    fun getNotificationState(): Boolean

    fun setIntegersExample(integersExample: IntegersExampleSaveData)
    fun getIntegersExample(): List<IntegersExampleSaveData>
    fun removeIntegersExample()

    fun setModulesExample(modulesExample: ModulesExampleSaveData)
    fun getModulesExample(): List<ModulesExampleSaveData>
    fun removeModulesExample()

    fun setFractionExample(fractionExample: FractionExampleSaveData)
    fun getFractionExample(): List<FractionExampleSaveData>
    fun removeFractionExample()

    fun setEquationExample(fractionExample: EquationExampleSaveData)
    fun getEquationExample(): List<EquationExampleSaveData>
    fun removeEquationExample()

    fun setDegreeExample(degreeExample: DegreeExampleSaveData)
    fun getDegreeExample(): List<DegreeExampleSaveData>
    fun removeDegreeExample()

    fun setLinearFunctionExample(linearExample: LinearFunctionExampleSaveData)
    fun getLinearFunctionExample(): List<LinearFunctionExampleSaveData>
    fun removeLinearFunctionExample()

    fun setLogarithmExample(logarithmExample: LogarithmExampleSaveData)
    fun getLogarithmExample(): List<LogarithmExampleSaveData>
    fun removeLogarithmExample()

    fun setNumberOfCorrectAnswers(count: Int, level: String)
    fun getNumberOfCorrectAnswers(level: String): Int
    fun getNumberOfAllAnswers(level: String): Int
    fun removeNumberOfCorrectAnswer(level: String)

    fun setNumberOfWrongAnswers(count: Int, level: String)
    fun getNumberOfWrongAnswers(level: String): Int
    fun removeNumberOfWrongAnswers(level: String)

    fun setPurchaseStatusForLevel(purchaseStatusForLevel: PurchaseStatusForLevelData)
    fun getPurchaseStatusForLevel(levelName: String): Boolean
}