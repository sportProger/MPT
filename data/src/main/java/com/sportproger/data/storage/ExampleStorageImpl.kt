package com.sportproger.data.storage

import android.content.Context
import androidx.room.Room
import com.sportproger.data.database.DataBase
import com.sportproger.data.database.Entity.*
import com.sportproger.data.storage.module.*

class ExampleStorageImpl(context: Context): ExampleStorage {
    private val db = Room.databaseBuilder(
        context,
        DataBase::class.java, "DataBase"
    )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    private val SHARED_PREF_NAME = "EXAMPLE_SHARED_PREF"
    private val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    private val edit = sharedPreferences.edit()
    private val maxExamples = 25

    override fun setIntegersExample(integersExample: IntegersExampleSaveDataStorage) {
        if (db.integersExampleDao().getAll().size < maxExamples) {
            db.integersExampleDao().insertAll(IntegersExampleEntity(
                null,
                number1      = integersExample.number1,
                sign         = integersExample.sign,
                number2      = integersExample.number2,
                result       = integersExample.result,
                userAnswer   = integersExample.userAnswer,
                stateExample = integersExample.stateExample
            ))
        }
    }

    override fun getIntegersExample(): List<IntegersExampleEntity> { return db.integersExampleDao().getAll() }

    override fun removeIntegersExample() { db.integersExampleDao().deleteAll() }

    override fun setModulesExample(modulesExample: ModulesExampleSaveDataStorage) {
        if (db.modulesExampleDao().getAll().size < maxExamples) {
            db.modulesExampleDao().insertAll(ModulesExampleEntity(
                null,
                number1      = modulesExample.number1,
                sign         = modulesExample.sign,
                number2      = modulesExample.number2,
                result       = modulesExample.result,
                userAnswer   = modulesExample.userAnswer,
                stateExample = modulesExample.stateExample
            ))
        }
    }

    override fun getModulesExample(): List<ModulesExampleEntity> { return db.modulesExampleDao().getAll() }

    override fun removeModulesExample() { db.modulesExampleDao().deleteAll() }

    override fun setFractionExample(fractionExample: FractionExampleSaveDataStorage) {
        if (db.fractionsExampleDao().getAll().size < maxExamples) {
            db.fractionsExampleDao().insertAll(FractionsExampleEntity(
                null,
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
                userAnswerFloat = fractionExample.userAnswerFloat,
                stateExample = fractionExample.stateExample,
            ))
        }
    }

    override fun getFractionExample(): List<FractionsExampleEntity> { return db.fractionsExampleDao().getAll() }

    override fun removeFractionExample() { db.fractionsExampleDao().deleteAll() }

    override fun setEquationExample(equationExample: EquationExampleSaveDataStorage) {
        if (db.equationExampleDao().getAll().size < maxExamples) {
            db.equationExampleDao().insertAll(EquationExampleEntity(
                null,
                type = equationExample.type,
                a = equationExample.a,
                b = equationExample.b,
                c = equationExample.c,
                sign1 = equationExample.sign1,
                sign2 = equationExample.sign2,
                linearResult = equationExample.linearResult,
                squareResult = equationExample.squareResult?.joinToString(" "),
                userLinearAnswer = equationExample.userLinearAnswer,
                userSquareAnswer = equationExample.userSquareAnswer,
                stateExample = equationExample.stateExample,
            ))
        }
    }

    override fun getEquationExample(): List<EquationExampleEntity> {
        return db.equationExampleDao().getAll()
    }

    override fun removeEquationExample() {
        db.equationExampleDao().deleteAll()
    }

    override fun setDegreeExample(degreeExample: DegreeExampleSaveDataStorage) {
        if (db.degreeExampleDao().getAll().size < maxExamples) {
            db.degreeExampleDao().insertAll(DegreeExampleEntity(
                null,
                base1 = degreeExample.base1,
                exponent1 = degreeExample.exponent1,
                sign = degreeExample.sign,
                base2 = degreeExample.base2,
                exponent2 = degreeExample.exponent2,
                result = degreeExample.result,
                userAnswer = degreeExample.userAnswer,
                stateExample = degreeExample.stateExample,
            ))
        }
    }

    override fun getDegreeExample(): List<DegreeExampleEntity> {
        return db.degreeExampleDao().getAll()
    }

    override fun removeDegreeExample() {
        db.degreeExampleDao().deleteAll()
    }

    override fun setRootExample(rootExample: RootExampleSaveDataStorage) {
        if (db.rootExampleDao().getAll().size < maxExamples) {
            db.rootExampleDao().insertAll(RootExampleEntity(
                null,
                type = rootExample.type,
                exponent1 = rootExample.exponent1,
                exponent2 = rootExample.exponent2,
                baseRoot1 = rootExample.baseRoot1,
                baseRoot2 = rootExample.baseRoot2,
                sign = rootExample.sign,
                result = rootExample.result,
                userAnswer = rootExample.userAnswer,
                stateExample = rootExample.stateExample
            ))
        }
    }

    override fun getRootExample(): List<RootExampleEntity> {
        return db.rootExampleDao().getAll()
    }

    override fun removeRootExample() {
        db.rootExampleDao().deleteAll()
    }

    override fun setLinearFunctionExample(linearExample: LinearFunctionExampleSaveDataStorage) {
        if (db.linearFunctionDao().getAll().size < maxExamples) {
            db.linearFunctionDao().insertAll(LinearFunctionExampleEntity(
                null,
                x = linearExample.x,
                a = linearExample.a,
                b = linearExample.b,
                sign = linearExample.sign,
                result = linearExample.result,
                userAnswer = linearExample.userAnswer,
                stateExample = linearExample.stateExample
            ))
        }
    }

    override fun getLinearFunctionExample(): List<LinearFunctionExampleEntity> {
        return db.linearFunctionDao().getAll()
    }

    override fun removeLinearFunctionExample() {
        db.linearFunctionDao().deleteAll()
    }

    override fun setLogarithmExample(logarithmExample: LogarithmExampleSaveDataStorage) {
        if (db.logarithmExampleDao().getAll().size < maxExamples) {
            db.logarithmExampleDao().insertAll(LogarithmExampleEntity(
                null,
                baseOfLogarithm = logarithmExample.baseOfLogarithm,
                logarithmicNumber = logarithmExample.logarithmicNumber,
                result = logarithmExample.result,
                userAnswer = logarithmExample.userAnswer,
                stateExample = logarithmExample.stateExample
            ))
        }
    }

    override fun getLogarithmExample(): List<LogarithmExampleEntity> {
        return db.logarithmExampleDao().getAll()
    }

    override fun removeLogarithmExample() {
        db.logarithmExampleDao().deleteAll()
    }

    override fun setNumberOfCorrectAnswers(count: Int, level: String) {
        val numberOfAllAnswers = getNumberOfCorrectAnswers(level) + getNumberOfWrongAnswers(level) + 1
        if (numberOfAllAnswers <= maxExamples) {
            edit.putInt("${level}_CORRECT", count).commit()
        }
    }

    override fun getNumberOfCorrectAnswers(level: String): Int { return sharedPreferences.getInt("${level}_CORRECT", 0) }

    override fun removeNumberOfCorrectAnswers(level: String) { edit.putInt("${level}_CORRECT", 0).commit() }

    override fun setNumberOfWrongAnswers(count: Int, level: String) {
        val numberOfAllAnswers = getNumberOfCorrectAnswers(level) + getNumberOfWrongAnswers(level) + 1
        if (numberOfAllAnswers <= maxExamples) {
            edit.putInt("${level}_WRONG", count).commit()
        }
    }

    override fun getNumberOfWrongAnswers(level: String): Int { return sharedPreferences.getInt("${level}_WRONG", 0) }

    override fun removeNumberOfWrongAnswers(level: String) { edit.putInt("${level}_WRONG", 0).commit() }
}