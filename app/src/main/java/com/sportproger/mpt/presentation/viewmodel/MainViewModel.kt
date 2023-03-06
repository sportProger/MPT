package com.sportproger.mpt.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sportproger.domain.module.*
import com.sportproger.domain.repository.UserRepository
import com.sportproger.mpt.general.Conf
import kotlin.math.absoluteValue
import kotlin.math.pow
import android.net.ConnectivityManager

import android.net.NetworkInfo
import org.koin.core.component.getScopeId


class MainViewModel(
    private val userRepository: UserRepository

): ViewModel() {
    private val isFirstStart = MutableLiveData<Boolean>()
    private val numberOfCoins = MutableLiveData<Int>()
    private val isInternetConnection = MutableLiveData<Boolean>()
    private val exampleForIntegers = MutableLiveData<IntegersExample>()
    private val exampleForModules = MutableLiveData<ModulesExample>()
    private val exampleForFraction = MutableLiveData<FractionExample>()
    private val exampleForDegree = MutableLiveData<DegreeExample>()
    private val exampleForLinearFunction = MutableLiveData<LinearFunctionExample>()
    private val exampleForLogarithm = MutableLiveData<LogarithmExample>()
    private val levelLive = MutableLiveData<String>()
    private val currentTheme = MutableLiveData<String>()
    private val sounds = MutableLiveData<Boolean>()

    fun numberOfCoinsLive(): LiveData<Int> { return numberOfCoins }

    fun setNumberOfCoins(numberOfCoins: Int) { userRepository.setNumberOfCoins(numberOfCoins) }

    fun getNumberOfCoins() { numberOfCoins.value = userRepository.getNumberOfCoins() }

    fun isFirstStartLive(): LiveData<Boolean> { return isFirstStart }

    fun isFirstStart() { isFirstStart.value = userRepository.isFirstStart() }

    fun isInternetConnectionLive(): LiveData<Boolean> { return isInternetConnection }

    fun levelLive(): LiveData<String> { return levelLive }

    fun currentThemeLive(): LiveData<String> { return currentTheme }

    fun soundsLive(): LiveData<Boolean> { return sounds }

    fun getLevel() { levelLive.value = userRepository.getLevel() }

    fun integersExampleLive(): LiveData<IntegersExample> { return exampleForIntegers }

    fun modulesExampleLive(): LiveData<ModulesExample> { return exampleForModules }

    fun fractionExampleLive(): LiveData<FractionExample> { return exampleForFraction }

    fun linearFunctionExampleLive(): LiveData<LinearFunctionExample> { return exampleForLinearFunction }

    fun degreeExampleLive(): LiveData<DegreeExample> { return exampleForDegree }

    fun logarithmExampleLive(): LiveData<LogarithmExample> { return exampleForLogarithm }

    fun setIntegersExample(integersExample: IntegersExampleSaveData) { userRepository.setIntegersExample(integersExample) }

    fun setModulesExample(modulesExample: ModulesExampleSaveData) { userRepository.setModulesExample(modulesExample) }

    fun setFractionExample(fractionsExample: FractionExampleSaveData) { userRepository.setFractionExample(fractionsExample) }

    fun setDegreeExample(degreeExample: DegreeExampleSaveData) { userRepository.setDegreeExample(degreeExample) }

    fun setLogarithmExample(logarithmExample: LogarithmExampleSaveData) { userRepository.setLogarithmExample(logarithmExample) }

    fun setLinearFunctionExample(functionExample: LinearFunctionExampleSaveData) { userRepository.setLinearFunctionExample(functionExample) }

    fun setNumberOfCorrectAnswers(count: Int, level: String) { userRepository.setNumberOfCorrectAnswers(count, level) }

    fun setNumberOfWrongAnswers(count: Int, level: String) { userRepository.setNumberOfWrongAnswers(count, level) }

    fun setCurrentTheme(themeName: String) { userRepository.setCurrentTheme(themeName) }

    fun getCurrentTheme() { currentTheme.value = userRepository.getCurrentTheme() }

    fun getSounds() { sounds.value = userRepository.getSounds() }

    fun addCorrectAnswer(level: String) {
        val it = userRepository.getNumberOfCorrectAnswers(level)
        setNumberOfCorrectAnswers(it + 1, level)
    }

    fun addWrongAnswer(level: String) {
        val it = userRepository.getNumberOfWrongAnswers(level)
        setNumberOfWrongAnswers(it + 1, level)
    }

    fun internetConnectionCheck(context: Context) {
        val connectivity = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifi = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI)?.isConnected
        val mobile = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)?.isConnected

        if (wifi != null && wifi) { isInternetConnection.value = true }
        else if (mobile != null && mobile) { isInternetConnection.value = true }
        else { isInternetConnection.value = false }
    }

    private fun max(a: Int, b: Int): Int {
        if (a > b) return a
        else return b
    }

    private fun min(a: Int, b: Int): Int {
        if (a < b) return a
        else return b
    }

    private fun numbersToDivide(): ArrayList<Int> {
        val data = userRepository.getTypeNumbers()
        val num1 = ((data.from + 1)..data.to).shuffled().last()
        val array = arrayListOf<Int>()
        var i = data.from + 1

        while (i <= data.to) {
            if(i % num1 == 0 || num1 % i == 0) array.add(i)
            i++
        }
        val num2 = array.shuffled().last()

        return arrayListOf(max(num1, num2), min(num1, num2))
    }

    fun generateIntegersExample(negativity: Boolean = false) {
        val sign = arrayOf("+", "-", "*", "/").random()
        val data = userRepository.getTypeNumbers()

        if(sign != "/") {
            var num1: Int
            var num2: Int
            var result = 0

            if (!negativity) {
                num1 = (data.from..data.to).shuffled().last()
                num2 = (data.from..data.to).shuffled().last()
                Log.d(Conf.MY_LOG, "$num1, $num2")
                Log.d(Conf.MY_LOG, "${data.type} ${data.from} ${data.to}")

                if(sign == "+") result = (num1 + num2)
                if(sign == "-") result = (num1 - num2)
                if(sign == "*") result = (num1 * num2)

                exampleForIntegers.value = IntegersExample(num1, sign, num2, result)
            }
            else {
                if(data.from == 0) {
                    num1 = (-9..data.to).random()
                    num2 = (-9..data.to).random()
                }
                else {
                    num1 = (-data.from..data.to).random()
                    num2 = (-data.from..data.to).random()
                }

                if(sign == "+") result = (num1.absoluteValue + num2.absoluteValue)
                if(sign == "-") result = (num1.absoluteValue - num2.absoluteValue)
                if(sign == "*") result = (num1.absoluteValue * num2.absoluteValue)

                exampleForModules.value = ModulesExample(num1, sign, num2, result)
            }
        }
        else {
            val numbers = numbersToDivide()
            exampleForIntegers.value = IntegersExample(numbers[0], "/", numbers[1], (numbers[0] / numbers[1]))
        }
    }

    fun generateFractionExample() {
        val sign = arrayListOf("+", "-", "*").random()
        val type = arrayListOf("decimals", "common").random()

        if (type == "common") {
            val numbers1 = numbersToDivide()
            val numbers2 = numbersToDivide()

            val numerator1 = numbers1[0]
            val denominator1 = numbers1[1]
            val numerator2 = numbers2[0]
            val denominator2 = numbers2[1]
            var result = 0

            when (sign) {
                "+" -> result = (numerator1 / denominator1) + (numerator2 / denominator2)
                "-" -> result = (numerator1 / denominator1) - (numerator2 / denominator2)
                "*" -> result = (numerator1 / denominator1) * (numerator2 / denominator2)
            }

            exampleForFraction.value = FractionExample(type, numerator1, denominator1, sign, numerator2, denominator2, 0.0F, 0.0F, result, 0.0F)
        }
        if (type == "decimals") {
            val data = userRepository.getTypeNumbers()
            val numerator1 = (data.from..data.to).shuffled().last().toFloat()
            val denominator1 = (-10..10).shuffled().last().toFloat() + 1
            val numerator2 = (data.from..data.to).shuffled().last().toFloat()
            val denominator2 = (10..10).shuffled().last().toFloat() + 1

            val number1 = String.format("%.2f", (numerator1 / denominator1)).toFloat()
            val number2 = String.format("%.2f", (numerator2 / denominator2)).toFloat()
            Log.d("TaskLog", "${data.from} - from, ${data.to} - to, $number1 - number1, $number2 - number2")

            var result = 0.0F

            when (sign) {
                "+" -> result = String.format("%.2f", (number1 + number2)).toFloat()
                "-" -> result = String.format("%.2f", (number1 - number2)).toFloat()
                "*" -> result = String.format("%.2f", (number1 * number2)).toFloat()
            }

            exampleForFraction.value = FractionExample(type, 0, 0, sign, 0, 0, number1, number2, 0, result)
        }

        // decimals - десятичные
        // common - обыкновенные
    }

    fun generateDegreeExample() {
        val data = userRepository.getTypeNumbers()
        val sign = arrayListOf("+", "-", "*").random()
        val base1 = (data.from..data.to).random()
        val base2 = (data.from..data.to).random()
        var exponent1 = 0
        var exponent2 = 0
        var result = 0

        if (base1 < 10) exponent1 = (0..4).random()
        if (base1 in 11..99) exponent1 = (0..2).random()
        if (base1 in 100..999) exponent1 = (0..1).random()

        if (base2 < 10) exponent2 = (0..4).random()
        if (base2 in 11..99) exponent2 = (0..2).random()
        if (base2 in 100..999) exponent2 = (0..1).random()

        if(sign == "+") result = (base1.toDouble().pow(exponent1) + base2.toDouble().pow(exponent2)).toInt()
        if(sign == "-") result = (base1.toDouble().pow(exponent1) - base2.toDouble().pow(exponent2)).toInt()
        if(sign == "*") result = (base1.toDouble().pow(exponent1) * base2.toDouble().pow(exponent2)).toInt()

        // Delete
        Log.d(Conf.MY_LOG, "$base1^$exponent1 $sign $base2^$exponent2")
        Log.d(Conf.MY_LOG, "${base1.toDouble().pow(exponent1)} $sign ${base2.toDouble().pow(exponent2)}")
        Log.d(Conf.MY_LOG, result.toString())

        exampleForDegree.value = DegreeExample(base1, exponent1, sign, base2, exponent2, result)
    }

    fun generateLinearFunctionExample() {
        val data = userRepository.getTypeNumbers()
        val x = (data.from..data.to).shuffled().last()
        val a = (data.from..data.to).shuffled().last()
        val b = (data.from..data.to).shuffled().last()
        val sign = arrayListOf("+", "-").shuffled().last()
        var result = 0

        if (sign == "+") result = (a*x + b*x)
        if (sign == "-") result = (a*x - b*x)

        // Delete
        Log.d(Conf.MY_LOG, "${a}x + $b")
        Log.d(Conf.MY_LOG, result.toString())

        exampleForLinearFunction.value = LinearFunctionExample(x, a, b, sign, result)
    }

    fun generateLogarithmExample() {
        val data = userRepository.getTypeNumbers()
        val base = (data.from..data.to).shuffled().last()
        val pow = (0..4).shuffled().last()
        val logarithmicNumber = base.toDouble().pow(pow).toInt()

        exampleForLogarithm.value = LogarithmExample(base, logarithmicNumber, pow)
    }
}