package com.sportproger.data.repository

import com.sportproger.data.storage.ExampleStorage
import com.sportproger.data.storage.UserStorage
import com.sportproger.data.storage.module.*
import com.sportproger.domain.module.*
import com.sportproger.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userStorage: UserStorage,
    private val exampleStorage: ExampleStorage
): UserRepository {
    override fun isFirstStart(): Boolean { return userStorage.isFirstStart() }

    override fun setNumberOfCoins(numberOfCoins: Int) { userStorage.setNumberOfCoins(numberOfCoins = numberOfCoins) }

    override fun getNumberOfCoins(): Int { return userStorage.getNumberOfCoins() }

    override fun setCurrentTheme(currentTheme: String) { userStorage.setCurrentTheme(currentTheme = currentTheme) }

    override fun getCurrentTheme(): String { return userStorage.getCurrentTheme() }

    override fun setLevel(level: String) { userStorage.setLevel(levelName = level) }

    override fun setSounds(sounds: Boolean) { userStorage.setSounds(sounds = sounds) }

    override fun getSounds(): Boolean { return userStorage.getSounds() }

    override fun setPurchaseStatusForTheme(themeName: String) { userStorage.setPurchaseStatusForTheme(themeName) }

    override fun getPurchaseStatusForTheme(themeName: String): Boolean { return userStorage.getPurchaseStatusForTheme(themeName) }

    override fun setTypeNumbers(typeNumbersData: TypeNumbersData) {
        val data = TypeNumbersDataStorage(type = typeNumbersData.type, from = typeNumbersData.from, to = typeNumbersData.to)
        userStorage.setTypeNumbers(data)
    }

    override fun getTypeNumbers(): TypeNumbersData {
        val data = userStorage.getTypeNumbers()
        return TypeNumbersData(type = data.type, from = data.from, to = data.to)
    }

    override fun setNotificationTime(notificationTime: NotificationTime) {
        userStorage.setNotificationTime(NotificationTimeDataStorage(
            minute = notificationTime.minute,
            hour = notificationTime.hour,
            dayOfMonth = notificationTime.dayOfMonth,
            month = notificationTime.month,
            year = notificationTime.year
        ))
    }

    override fun getNotificationTime(): NotificationTime {
        val data = userStorage.getNotificationTime()
        return NotificationTime(
            minute = data.minute,
            hour = data.hour,
            dayOfMonth = data.dayOfMonth,
            month = data.month,
            year = data.year
        )
    }

    override fun setNotificationState(flag: Boolean) {
        userStorage.setNotificationState(flag)
    }

    override fun getNotificationState(): Boolean {
        return userStorage.getNotificationState()
    }

    override fun getLevel(): String {
        return userStorage.getLevel()
    }

    override fun setIntegersExample(integersExample: IntegersExampleSaveData) {
        val data = IntegersExampleSaveDataStorage(
            integersExample.number1,
            integersExample.sign,
            integersExample.number2,
            integersExample.result,
            integersExample.userAnswer,
            integersExample.stateExample
        )

        exampleStorage.setIntegersExample(data)
    }

    override fun getIntegersExample(): List<IntegersExampleSaveData> {
        val data = exampleStorage.getIntegersExample()
        val resultData = mutableListOf<IntegersExampleSaveData>()
        data.forEach {
            val example = IntegersExampleSaveData(
                number1 = it.number1,
                sign = it.sign,
                number2 = it.number2,
                result = it.result,
                userAnswer = it.userAnswer,
                stateExample = it.stateExample,
            )

            resultData.add(example)
        }

        return resultData
    }

    override fun removeIntegersExample() {
        exampleStorage.removeIntegersExample()
    }

    override fun setModulesExample(modulesExample: ModulesExampleSaveData) {
        exampleStorage.setModulesExample(ModulesExampleSaveDataStorage(
            number1 = modulesExample.number1,
            sign = modulesExample.sign,
            number2 = modulesExample.number2,
            result = modulesExample.result,
            userAnswer = modulesExample.userAnswer,
            stateExample = modulesExample.stateExample
        ))
    }

    override fun getModulesExample(): List<ModulesExampleSaveData> {
        val data = exampleStorage.getModulesExample()
        val resultData = mutableListOf<ModulesExampleSaveData>()
        data.forEach {
            val example = ModulesExampleSaveData(
                number1 = it.number1,
                sign = it.sign,
                number2 = it.number2,
                result = it.result,
                userAnswer = it.userAnswer,
                stateExample = it.stateExample,
            )

            resultData.add(example)
        }

        return resultData
    }

    override fun removeModulesExample() {
        exampleStorage.removeModulesExample()
    }

    override fun setFractionExample(fractionExample: FractionExampleSaveData) {
        exampleStorage.setFractionExample(FractionExampleSaveDataStorage(
            type = fractionExample.type,
            numerator1 = fractionExample.numerator1,
            denominator1 = fractionExample.denominator1,
            sign = fractionExample.sign,
            numerator2 = fractionExample.numerator2,
            denominator2 = fractionExample.denominator2,
            number1 = fractionExample.number1,
            number2 = fractionExample.number2,
            floatResult = fractionExample.floatResult,
            result = fractionExample.result,
            userAnswer = fractionExample.userAnswer,
            stateExample = fractionExample.stateExample,
        ))
    }

    override fun getFractionExample(): List<FractionExampleSaveData> {
        val data = exampleStorage.getFractionExample()
        val resultData = mutableListOf<FractionExampleSaveData>()
        data.forEach {
            val example = FractionExampleSaveData(
                type = it.type,
                numerator1 = it.numerator1,
                denominator1 = it.denominator1,
                sign = it.sign,
                numerator2 = it.numerator2,
                denominator2 = it.denominator2,
                number1 = it.number1,
                number2 = it.number2,
                floatResult = it.floatResult,
                result = it.result,
                userAnswer = it.userAnswer,
                stateExample = it.stateExample,
            )

            resultData.add(example)
        }

        return resultData
    }

    override fun removeFractionExample() {
        exampleStorage.removeFractionExample()
    }

    override fun setDegreeExample(degreeExample: DegreeExampleSaveData) {
        exampleStorage.setDegreeExample(DegreeExampleSaveDataStorage(
            base1 = degreeExample.base1,
            exponent1 = degreeExample.exponent1,
            sign = degreeExample.sign,
            base2 = degreeExample.base2,
            exponent2 = degreeExample. exponent2,
            result = degreeExample. result,
            userAnswer = degreeExample. userAnswer,
            stateExample = degreeExample. stateExample,
        ))
    }

    override fun getDegreeExample(): List<DegreeExampleSaveData> {
        val data = exampleStorage.getDegreeExample()
        val resultData = mutableListOf<DegreeExampleSaveData>()
        data.forEach {
            val example = DegreeExampleSaveData(
                base1 = it.base1,
                exponent1 = it.exponent1,
                sign = it.sign,
                base2 = it.base2,
                exponent2 = it. exponent2,
                result = it. result,
                userAnswer = it. userAnswer,
                stateExample = it. stateExample,
            )

            resultData.add(example)
        }

        return resultData
    }

    override fun removeDegreeExample() {
        exampleStorage.removeDegreeExample()
    }

    override fun setLinearFunctionExample(linearExample: LinearFunctionExampleSaveData) {
        exampleStorage.setLinearFunctionExample(
            LinearFunctionExampleSaveDataStorage(
            x = linearExample.x,
            a = linearExample.a,
            b = linearExample.b,
            sign = linearExample.sign,
            result = linearExample.result,
            userAnswer = linearExample.userAnswer,
            stateExample = linearExample.stateExample
        ))
    }

    override fun getLinearFunctionExample(): List<LinearFunctionExampleSaveData> {
        val data = exampleStorage.getLinearFunctionExample()
        val result = mutableListOf<LinearFunctionExampleSaveData>()
        data.forEach {
            val example = LinearFunctionExampleSaveData(
                x = it.x,
                a = it.a,
                b = it.b,
                sign = it.sign,
                result = it.result,
                userAnswer = it.userAnswer,
                stateExample = it.stateExample
            )

            result.add(example)
        }

        return result
    }

    override fun removeLinearFunctionExample() {
        exampleStorage.removeLinearFunctionExample()
    }

    override fun setLogarithmExample(logarithmExample: LogarithmExampleSaveData) {
        exampleStorage.setLogarithmExample(
            LogarithmExampleSaveDataStorage(
            baseOfLogarithm = logarithmExample.baseOfLogarithm,
            logarithmicNumber = logarithmExample.logarithmicNumber,
            result = logarithmExample.result,
            userAnswer = logarithmExample.userAnswer,
            stateExample = logarithmExample.stateExample
        )
        )
    }

    override fun getLogarithmExample(): List<LogarithmExampleSaveData> {
        val data = exampleStorage.getLogarithmExample()
        val result = mutableListOf<LogarithmExampleSaveData>()
        data.forEach {
            val example = LogarithmExampleSaveData(
                baseOfLogarithm = it.baseOfLogarithm,
                logarithmicNumber = it.logarithmicNumber,
                result = it.result,
                userAnswer = it.userAnswer,
                stateExample = it.stateExample
            )

            result.add(example)
        }

        return result
    }

    override fun removeLogarithmExample() {
        exampleStorage.removeLogarithmExample()
    }

    override fun setNumberOfCorrectAnswers(count: Int, level: String) { exampleStorage.setNumberOfCorrectAnswers(count, level) }

    override fun getNumberOfCorrectAnswers(level: String): Int { return exampleStorage.getNumberOfCorrectAnswers(level) }

    override fun getNumberOfAllAnswers(level: String): Int {
        return exampleStorage.getNumberOfCorrectAnswers(level) + exampleStorage.getNumberOfWrongAnswers(level)
    }

    override fun removeNumberOfCorrectAnswer(level: String) { exampleStorage.removeNumberOfCorrectAnswers(level) }

    override fun setNumberOfWrongAnswers(count: Int, level: String) { exampleStorage.setNumberOfWrongAnswers(count, level) }

    override fun getNumberOfWrongAnswers(level: String): Int { return exampleStorage.getNumberOfWrongAnswers(level) }

    override fun removeNumberOfWrongAnswers(level: String) { exampleStorage.removeNumberOfWrongAnswers(level) }

    override fun setPurchaseStatusForLevel(purchaseStatusForLevel: PurchaseStatusForLevelData) {
        userStorage.setPurchaseStatusForLevel(PurchaseStatusForLevelDataStorage(
            nameLevel = purchaseStatusForLevel.nameLevel,
            status = purchaseStatusForLevel.status
        ))
    }

    override fun getPurchaseStatusForLevel(levelName: String): Boolean { return userStorage.getPurchaseStatusForLevel(levelName) }
}