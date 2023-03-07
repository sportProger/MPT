package com.sportproger.mpt.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sportproger.domain.repository.UserRepository
import com.sportproger.mpt.presentation.recycler.model.*

class LevelViewModel(
   private val userRepository: UserRepository
): ViewModel() {
    private val level = MutableLiveData<String>()
    private val numberOfCoins = MutableLiveData<Int>()
    private val integersExamples = MutableLiveData<List<Integer>>()
    private val modulesExamples = MutableLiveData<List<Module>>()
    private val fractionExamples = MutableLiveData<List<Fraction>>()
    private val equationExamples = MutableLiveData<List<Equation>>()
    private val degreeExamples = MutableLiveData<List<Degree>>()
    private val logarithmExamples = MutableLiveData<List<Logarithm>>()
    private val linearFunctionExamples = MutableLiveData<List<LinearFunction>>()
    private val numberOfCorrectAnswer = MutableLiveData<Int>()
    private val numberOfWrongAnswer = MutableLiveData<Int>()

    fun getLevel() { level.value = userRepository.getLevel() }

    fun getCountOfCoins() { numberOfCoins.value = userRepository.getNumberOfCoins() }

    fun levelLive(): LiveData<String> { return level }

    fun numberOfCoinsLive(): LiveData<Int> { return numberOfCoins }

    fun getIntegersExamplesLive(): LiveData<List<Integer>> { return integersExamples }

    fun getModulesExamplesLive(): LiveData<List<Module>> { return modulesExamples }

    fun getFractionExamplesLive(): LiveData<List<Fraction>> { return fractionExamples }

    fun getEquationExamplesLive(): LiveData<List<Equation>> { return equationExamples }

    fun getDegreeExamplesLive(): LiveData<List<Degree>> { return degreeExamples }

    fun getLinearFunctionExamplesLive(): LiveData<List<LinearFunction>> { return linearFunctionExamples }

    fun getLogarithmExamplesLive(): LiveData<List<Logarithm>> { return logarithmExamples }

    fun numberOfCorrectAnswerLive(): LiveData<Int> { return numberOfCorrectAnswer }

    fun numberOfWrongAnswerLive(): LiveData<Int> { return numberOfWrongAnswer }

    fun getAllIntegersExamples() {
        val resultData = mutableListOf<Integer>()
        userRepository.getIntegersExample().forEach {
            resultData.add(Integer(
                number1 = it.number1,
                sign = it.sign,
                number2 = it.number2,
                result = it.result,
                userAnswer = it.userAnswer,
                stateExample = it.stateExample,
                )
            )
        }

        integersExamples.value = resultData
    }

    fun getAllModulesExamples() {
        val resultData = mutableListOf<Module>()
        userRepository.getModulesExample().forEach {
            resultData.add(Module(
                number1 = it.number1,
                sign = it.sign,
                number2 = it.number2,
                result = it.result,
                userAnswer = it.userAnswer,
                stateExample = it.stateExample,
                )
            )
        }

        modulesExamples.value = resultData
    }

    fun getAllFractionsExample() {
        val resultData = mutableListOf<Fraction>()
        userRepository.getFractionExample().forEach {
            resultData.add(Fraction(
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
                userAnswerFloat = it.userAnswerFloat,
                stateExample = it.stateExample
                )
            )
        }

        fractionExamples.value = resultData
    }

    fun getAllDegreeExample() {
        val resultData = mutableListOf<Degree>()
        userRepository.getDegreeExample().forEach {
            resultData.add(Degree(
                base1 = it.base1,
                exponent1 = it.exponent1,
                sign = it.sign,
                base2 = it.base2,
                exponent2 = it. exponent2,
                result = it. result,
                userAnswer = it. userAnswer,
                stateExample = it. stateExample,
            ))
        }

        degreeExamples.value = resultData
    }

    fun getAllEquationExample() {
        val resultData = mutableListOf<Equation>()
        userRepository.getEquationExample().forEach {
            resultData.add(Equation(
                type = it.type,
                a = it.a,
                b = it.b,
                c = it.c,
                sign1 = it.sign1,
                sign2 = it.sign2,
                linearResult = it.linearResult,
                squareResult = it.squareResult,
                userLinearAnswer = it.userLinearAnswer,
                userSquareAnswer = it.userSquareAnswer,
                stateExample = it.stateExample
            ))
        }

        equationExamples.value = resultData
    }

    fun getAllLinearFunctionExample() {
        val resultData = mutableListOf<LinearFunction>()
        userRepository.getLinearFunctionExample().forEach {
            resultData.add(LinearFunction(
                x = it.x,
                a = it.a,
                b = it.b,
                sign = it.sign,
                result = it.result,
                userAnswer = it.userAnswer,
                stateExample = it.stateExample
            ))
        }

        linearFunctionExamples.value = resultData
    }

    fun getAllLogarithmExample() {
        val resultData = mutableListOf<Logarithm>()
        userRepository.getLogarithmExample().forEach {
            resultData.add(Logarithm(
                baseOfLogarithm = it.baseOfLogarithm,
                logarithmicNumber = it.logarithmicNumber,
                result = it.result,
                userAnswer = it.userAnswer,
                stateExample = it.stateExample
            ))
        }

        logarithmExamples.value = resultData
    }

    fun removeAllIntegersExamples() { userRepository.removeIntegersExample() }

    fun removeAllModulesExamples() { userRepository.removeModulesExample() }

    fun removeAllFractionsExamples() { userRepository.removeFractionExample() }

    fun removeAllEquationExamples() { userRepository.removeEquationExample() }

    fun removeAllDegreeExample() { userRepository.removeDegreeExample() }

    fun removeAllLinearFunctionsExample() { userRepository.removeLinearFunctionExample() }

    fun removeAllLogarithmExample() { userRepository.removeLogarithmExample() }

    fun getNumberOfCorrectAnswer(level: String) { numberOfCorrectAnswer.value = userRepository.getNumberOfCorrectAnswers(level) }

    fun removeNumberOfCorrectAnswers(level: String) { userRepository.removeNumberOfCorrectAnswer(level) }

    fun getNumberOfWrongAnswer(level: String) { numberOfWrongAnswer.value = userRepository.getNumberOfWrongAnswers(level) }

    fun removeNumberOfWrongAnswers(level: String) { userRepository.removeNumberOfWrongAnswers(level) }
}