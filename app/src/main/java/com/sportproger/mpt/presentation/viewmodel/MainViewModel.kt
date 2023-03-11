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
import kotlin.math.abs


class MainViewModel(
    private val userRepository: UserRepository

): ViewModel() {
    private val isFirstStart = MutableLiveData<Boolean>()
    private val numberOfCoins = MutableLiveData<Int>()
    private val isInternetConnection = MutableLiveData<Boolean>()
    private val exampleForIntegers = MutableLiveData<IntegersExample>()
    private val exampleForModules = MutableLiveData<ModulesExample>()
    private val exampleForFraction = MutableLiveData<FractionExample>()
    private val exampleForEquation = MutableLiveData<EquationExample>()
    private val exampleForDegree = MutableLiveData<DegreeExample>()
    private val exampleForRoot = MutableLiveData<RootExample>()
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

    fun equationExampleLive(): LiveData<EquationExample> { return exampleForEquation }

    fun linearFunctionExampleLive(): LiveData<LinearFunctionExample> { return exampleForLinearFunction }

    fun degreeExampleLive(): LiveData<DegreeExample> { return exampleForDegree }

    fun rootExampleLive(): LiveData<RootExample> { return exampleForRoot }

    fun logarithmExampleLive(): LiveData<LogarithmExample> { return exampleForLogarithm }

    fun setIntegersExample(integersExample: IntegersExampleSaveData) { userRepository.setIntegersExample(integersExample) }

    fun setModulesExample(modulesExample: ModulesExampleSaveData) { userRepository.setModulesExample(modulesExample) }

    fun setFractionExample(fractionsExample: FractionExampleSaveData) { userRepository.setFractionExample(fractionsExample) }

     fun setEquationExample(equationExample: EquationExampleSaveData) { userRepository.setEquationExample(equationExample) }

    fun setDegreeExample(degreeExample: DegreeExampleSaveData) { userRepository.setDegreeExample(degreeExample) }

    fun setRootExample(rootExample: RootExampleSaveData) { userRepository.setRootExample(rootExample) }

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
        else isInternetConnection.value = mobile != null && mobile
    }

    private fun max(a: Int, b: Int): Int {
        return if (a > b) a
        else b
    }

    private fun min(a: Int, b: Int): Int {
        return if (a < b) a
        else b
    }

    private fun numbersToDivide(): ArrayList<Int> {
        val data = userRepository.getTypeNumbers()
        val num1 = ((data.from + 1)..data.to).random()
        val array = arrayListOf<Int>()
        var i = data.from + 1

        while (i <= data.to) {
            if(i % num1 == 0 || num1 % i == 0) array.add(i)
            i++
        }
        val num2 = array.random()

        return arrayListOf(max(num1, num2), min(num1, num2))
    }

    fun generateIntegersExample(module: Boolean = false) {
        val sign = arrayOf("+", "-", "*", "/").random()
        val data = userRepository.getTypeNumbers()

        Log.d("TaskLog", "Generate Integers Example")

        if(sign != "/") {
            val num1: Int
            val num2: Int
            var result = 0

                if(data.from == 1) {
                    num1 = (-data.to..data.to).random()
                    num2 = (-data.to..data.to).random()
                }
                else {
                    num1 = (-data.from..data.to).random()
                    num2 = (-data.from..data.to).random()
                }

                if (module) {
                    if (sign == "+") result = (num1.absoluteValue + num2.absoluteValue)
                    if (sign == "-") result = (num1.absoluteValue - num2.absoluteValue)
                    if (sign == "*") result = (num1.absoluteValue * num2.absoluteValue)

                    exampleForModules.value = ModulesExample(num1, sign, num2, result)
                }
                else {
                    if (sign == "+") result = (num1 + num2)
                    if (sign == "-") result = (num1 - num2)
                    if (sign == "*") result = (num1 * num2)

                    exampleForIntegers.value = IntegersExample(num1, sign, num2, result)
                }
        }
        else {
            val numbers = numbersToDivide()
            if (module) exampleForModules.value = ModulesExample(numbers[0], "/", numbers[1], (numbers[0] / numbers[1]))
            else exampleForIntegers.value = IntegersExample(numbers[0], "/", numbers[1], (numbers[0] / numbers[1]))
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
            var denominator1 = (-10..10).shuffled().last().toFloat()
            val numerator2 = (data.from..data.to).shuffled().last().toFloat()
            var denominator2 = (10..10).shuffled().last().toFloat()

            if (denominator1 == 0.0F) denominator1 += 1.0F
            if (denominator2 == 0.0F) denominator2 += 1.0F

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

    fun generateEquationExample() {
        val data = userRepository.getTypeNumbers()
        val type = arrayListOf("square", "square").random()
        var result = 0.0F

        if (type == "linear") {
            val sign1 = arrayListOf("-", "+").shuffled().last()
            val a = (data.from..data.to).random()
            var b = (data.from..data.to).random()

            Log.d("TaskLog", "$type - type, $a - a, $b - b, $result - result")

            when (sign1) {
                "+" -> b = -b
                "-" -> b = abs(b)
            }

            result = String.format("%.2f", (b.toFloat() / a.toFloat())).toFloat()
            exampleForEquation.value = EquationExample(type, a, b, 0,  sign1, "", result, null)
            Log.d("TaskLog", "$type - type, $a - a, $b - b, $result - result")

        }
        else if (type == "square") {
            val a = (data.from..data.to).shuffled().last()
            val x1 = (-data.from..data.to).shuffled().last()
            val x2 = (-data.from..data.to).shuffled().last()
            var b = (x2 + x1) * a
            var c = (x1 * x2) * a
            var sign1 = ""
            var sign2 = ""
            // (x sign1 x1)(x sign2 x2)

            Log.d("TaskLog", "$a - a, $b - b, $c - c, $x1 - x1, $x2 - x2")

            if (b < 0) { sign1 = "+"; b = abs(b) }
            else sign1 = "-"

            if (c < 0) { sign2 = "-"; c = abs(c) }
            else sign2 = "+"

            exampleForEquation.value = EquationExample(type, a, b, c, sign1, sign2, null, arrayListOf(x1, x2))
            Log.d("TaskLog", "$type - type, $a - a, $b - b, $c - c, $x1 - x1, $x2 - x2, $sign1 - sign1, $sign2 - sign2")
        }
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

    fun generateRootExamples() {
        val data = userRepository.getTypeNumbers()
        val type = arrayListOf(Conf.ROOT_TYPES.TWO.name, Conf.ROOT_TYPES.TWO.name).random()
        val sign = arrayListOf("+", "-", "*", "/").shuffled().last()
        val exponent1 = (2..4).shuffled().last()
        val exponent2 = (2..4).shuffled().last()
        val base1 = (data.from..data.to).shuffled().last()
        val base2 = (data.from..data.to).shuffled().last()
        val baseRoot1 = base1.toDouble().pow(exponent1.toDouble()).toInt()
        val baseRoot2 = base2.toDouble().pow(exponent2.toDouble()).toInt()
        var result = 0.0F

        if (type == Conf.ROOT_TYPES.ONE.name) {
            exampleForRoot.value = RootExample(type, exponent1, exponent2, baseRoot1, baseRoot2, sign, base1.toFloat())
        }
        if (type == Conf.ROOT_TYPES.TWO.name) {
            if (sign == "+") result = (base1 + base2).toFloat()
            if (sign == "*") result = (base1 * base2).toFloat()
            if (sign == "-") result = (base1 - base2).toFloat()
            if (sign == "/") result = String.format("%.2f", (base1.toFloat() / base2.toFloat())).toFloat()

            exampleForRoot.value = RootExample(type, exponent1, exponent2, baseRoot1, baseRoot2, sign, result)
        }

        Log.d("TaskLog", "$base1 - base1, $base2 - base2, $exponent1 - exponent1, $exponent2 - exponent2, $baseRoot1 - root1, $baseRoot2 - root2, $result - result")
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