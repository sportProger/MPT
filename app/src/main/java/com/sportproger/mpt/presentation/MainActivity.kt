package com.sportproger.mpt.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.sportproger.domain.module.*
import com.sportproger.mpt.R
import com.sportproger.mpt.databinding.ActivityMainBinding
import com.sportproger.mpt.general.Base
import com.sportproger.mpt.general.Conf
import com.sportproger.mpt.presentation.viewmodel.MainViewModel
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.interstitial.InterstitialAd
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener
import com.yandex.mobile.ads.rewarded.Reward
import com.yandex.mobile.ads.rewarded.RewardedAd
import com.yandex.mobile.ads.rewarded.RewardedAdEventListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.view.ViewGroup
import android.os.Build
import androidx.core.content.ContextCompat
import com.sportproger.mpt.general.Draw
import com.sportproger.mpt.general.DrawImpl


class MainActivity: Base() {
    private lateinit var binding: ActivityMainBinding
    private val vm by viewModel<MainViewModel>()
    private lateinit var currentResultTV: TextView
    private var soundsFlag = false
    private var falseAnswerCount = 0
    private lateinit var currentTheme: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        vm.isFirstStart()
        vm.isFirstStartLive().observe(this, {
            if (it) startActivity(Intent(this@MainActivity, StartActivity::class.java))
            else setContentView(binding.root)
        })
    }

    override fun onStart() = with(binding) {
        super.onStart()
        share(share)

        vm.getCurrentTheme()
        vm.currentThemeLive().observe(this@MainActivity, {
            currentTheme = it
            vm.setCurrentTheme(it)
        })

        vm.getNumberOfCoins()
        vm.numberOfCoinsLive().observe(this@MainActivity, { tvCoinCount.text = it.toString() })

        vm.integersExampleLive().observe(this@MainActivity, { example ->
            Log.d("TaskLog", "${example.number1} ${example.sign} ${example.number2}")
            showIntegersExample(example.number1, example.sign, example.number2)
            equals.setOnClickListener {
                val stateExample = checkExampleGeneral(Conf.INTEGERS, example.result)
                Log.d("TaskLog", "$stateExample - stateExample integers")
                if (stateExample != null) {
                    val userAnswer = currentResultTV.text.toString().toInt()
                    currentResultTV.text = ""
                    vm.setIntegersExample(IntegersExampleSaveData(example.number1, example.sign, example.number2, example.result, userAnswer, stateExample))

                    equalsAd(stateExample)
                    determineNumberOfCoins(Conf.INTEGERS, stateExample)
               }
            }
        })

        vm.modulesExampleLive().observe(this@MainActivity, { example ->
            Log.d("Looog", "${example.number1} ${example.sign} ${example.number2} = ${example.result} - live")
            showModulesExample(example.number1, example.sign, example.number2)
            equals.setOnClickListener {
                val stateExample = checkExampleGeneral(Conf.MODULES, example.result)
                if (stateExample != null) {
                    val userAnswer = currentResultTV.text.toString().toInt()
                    currentResultTV.text = ""
                    vm.setModulesExample(ModulesExampleSaveData(example.number1, example.sign, example.number2, example.result, userAnswer, stateExample))

                    equalsAd(stateExample)
                    determineNumberOfCoins(Conf.MODULES, stateExample)
                }
            }
        })

        vm.fractionExampleLive().observe(this@MainActivity, {example ->
            if (example.type == "common") {
                Log.d("TaskLog", "${example.numerator1} - numerator1, ${example.numerator2} - number2, ${example.sign} - sign, ${example.denominator1} - denominator1, ${example.denominator2} - denominator2, ${example.result} - result")
                showCommonFractionExample(example.numerator1, example.denominator1, example.sign, example.numerator2, example.denominator2)
                equals.setOnClickListener {
                    val stateExample = checkExampleGeneral(Conf.FRACTION, example.result)
                    if (stateExample != null) {
                        val userAnswer = currentResultTV.text.toString().toInt()
                        currentResultTV.text = ""
                        vm.setFractionExample(FractionExampleSaveData(example.type, example.numerator1, example.denominator1, example.sign, example.numerator2, example.denominator2, example.number1, example.number2, example.floatResult, example.result,  userAnswer, 0F, stateExample))
                        equalsAd(stateExample)
                        determineNumberOfCoins(Conf.FRACTION, stateExample)
                    }
                }
            }
            if (example.type == "decimals") {
                Log.d("TaskLog", "${example.number1} - number1, ${example.sign} - sign, ${example.number2} - number2, ${example.floatResult} - result")
                showDecimalsFractionExample(example.number1, example.sign, example.number2)
                equals.setOnClickListener {
                    val stateExample = checkExampleGeneral(Conf.FRACTION, example.floatResult)
                    if (stateExample != null) {
                        val userAnswer = currentResultTV.text.toString().toFloat()
                        currentResultTV.text = ""
                        vm.setFractionExample(FractionExampleSaveData(example.type, example.numerator1, example.denominator1, example.sign, example.numerator2, example.denominator2, example.number1, example.number2, example.floatResult, example.result,  0, userAnswer, stateExample))
                        equalsAd(stateExample)
                        determineNumberOfCoins(Conf.FRACTION, stateExample)
                    }
                }
            }
        })

        vm.equationExampleLive().observe(this@MainActivity, {example ->
            showEquationExampleLive(example.type, example.a, example.b, example.c, example.sign1, example.sign2)
            equals.setOnClickListener {
                var stateExample: Boolean? = null;

                if (example.type == "linear") stateExample = checkExampleGeneral(Conf.EQUATION, example.linearResult)
                else if (example.type == "square") stateExample = checkExampleGeneral(Conf.EQUATION, example.squareResult)

                if (stateExample != null) {
                    if (example.type == "linear") {
                        val userAnswer = currentResultTV.text.toString().toFloat()
                        vm.setEquationExample(EquationExampleSaveData(example.type, example.a, example.b, example.c, example.sign1, example.sign2, example.linearResult, example.squareResult, userAnswer, null, stateExample))
                    }
                    else if (example.type == "square") {
                        val userAnswer = currentResultTV.text.toString().toInt()
                        vm.setEquationExample(EquationExampleSaveData(example.type, example.a, example.b, example.c, example.sign1, example.sign2, example.linearResult, example.squareResult, null, userAnswer, stateExample))
                    }

                    currentResultTV.text = ""
                    equalsAd(stateExample)
                    determineNumberOfCoins(Conf.EQUATION, stateExample)
                }
            }
        })

        vm.degreeExampleLive().observe(this@MainActivity, {example ->
            showDegreeExample(example.base1, example.exponent1, example.sign, example.base2, example.exponent2)
            equals.setOnClickListener {
                val stateExample = checkExampleGeneral(Conf.DEGREE, example.result)
                if (stateExample != null) {
                    val userAnswer = currentResultTV.text.toString().toInt()
                    currentResultTV.text = ""
                    vm.setDegreeExample(DegreeExampleSaveData(example.base1, example.exponent1, example.sign, example.base2, example.exponent2, example.result, userAnswer, stateExample))

                    equalsAd(stateExample)
                    determineNumberOfCoins(Conf.DEGREE, stateExample)
                }
            }
        })

        vm.rootExampleLive().observe(this@MainActivity, { example ->
            showRootExample(example.type, example.sign, example.exponent1, example.exponent2, example.baseRoot1, example.baseRoot2)
            equals.setOnClickListener {
                val stateExample = checkExampleGeneral(Conf.ROOT, example.result)
                if (stateExample != null) {
                    val userAnswer = currentResultTV.text.toString().toFloat()
                    currentResultTV.text = ""
                    vm.setRootExample(RootExampleSaveData(example.type, example.exponent1, example.exponent2, example.baseRoot1, example.baseRoot2, example.sign, example.result, userAnswer, stateExample))

                    equalsAd(stateExample)
                    determineNumberOfCoins(Conf.ROOT, stateExample)
                }
            }
        })

        vm.linearFunctionExampleLive().observe(this@MainActivity, {example ->
            showLinearFunctionExample(example.x, example.a, example.sign, example.b)
            equals.setOnClickListener {
                val stateExample = checkExampleGeneral(Conf.LINEAR_FUNCTIONS, example.result)
                if (stateExample != null) {
                    val userAnswer = currentResultTV.text.toString().toInt()
                    currentResultTV.text = ""
                    vm.setLinearFunctionExample(LinearFunctionExampleSaveData(
                        x = example.x,
                        a = example.a,
                        b = example.b,
                        sign = example.sign,
                        result = example.result,
                        userAnswer = userAnswer,
                        stateExample = stateExample
                    ))

                    equalsAd(stateExample)
                    determineNumberOfCoins(Conf.LINEAR_FUNCTIONS, stateExample)
                }
            }
        })

        vm.logarithmExampleLive().observe(this@MainActivity, { example ->
            showLogarithmExample(example.baseOfLogarithm, example.logarithmicNumber)
            equals.setOnClickListener {
                val stateExample = checkExampleGeneral(Conf.LOGARITHM, example.result)
                if (stateExample != null) {
                    val userAnswer = currentResultTV.text.toString().toInt()
                    currentResultTV.text = ""
                    vm.setLogarithmExample(LogarithmExampleSaveData(
                        baseOfLogarithm = example.baseOfLogarithm,
                        logarithmicNumber = example.logarithmicNumber,
                        result = example.result,
                        userAnswer = userAnswer,
                        stateExample = stateExample
                    ))

                    equalsAd(stateExample)
                    determineNumberOfCoins(Conf.LOGARITHM, stateExample)
                }
            }
        })

        vm.getSounds()
        vm.soundsLive().observe(this@MainActivity, {
            soundsFlag = it
        })

        vm.levelLive().observe(this@MainActivity, {
            when(it) {
                Conf.INTEGERS -> {
                    vm.generateIntegersExample(module = false)
                    showHideLayout(constraintSimple)
                    currentResultTV = resSimple
                }
                Conf.FRACTION -> {
                    vm.generateFractionExample()
                    showHideLayout(constraintFraction)
                    currentResultTV = resFraction
                }
                Conf.MODULES -> {
                    vm.generateIntegersExample(module = true)
                    showHideLayout(constraintModules)
                    currentResultTV = resModules
                }
                Conf.EQUATION -> {
                    vm.generateEquationExample()
                    showHideLayout(constraintEquation)
                    currentResultTV = equationRes
                }
                Conf.DEGREE -> {
                    vm.generateDegreeExample()
                    showHideLayout(constraintDegree)
                    currentResultTV = resDegree
                }
                Conf.ROOT -> {
                    vm.generateRootExamples()
                    showHideLayout(constraintRoot)
                    currentResultTV = rootRes
                }
                Conf.LINEAR_FUNCTIONS -> {
                    vm.generateLinearFunctionExample()
                    showHideLayout(constraintLinearFunction)
                    currentResultTV = resForLinearFunction
                }
                Conf.LOGARITHM -> {
                    vm.generateLogarithmExample()
                    showHideLayout(constraintLogarithm)
                    currentResultTV = resLogarighm
                }
            }
        })

        menuBtn.setOnClickListener { drawer.openDrawer(GravityCompat.START) }

        val listener = object: NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.settings       -> {
                        interstitialAdvertising()
                        startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
                    }
                    R.id.help_project   -> startActivity(Intent(this@MainActivity, HelpProjectActivity::class.java))
                    R.id.levels         -> startActivity(Intent(this@MainActivity, LevelsActivity::class.java))
                    R.id.games          -> {
                        if (internetConnectionCheck()) startActivity(Intent(this@MainActivity, GamesActivity::class.java))
                        else Toast.makeText(this@MainActivity, getString(R.string.enabled_internet), Toast.LENGTH_SHORT).show()
                    }
                    R.id.themes         -> startActivity(Intent(this@MainActivity, ThemesActivity::class.java))
                    R.id.about_us       -> startActivity(Intent(this@MainActivity, AboutActivity::class.java))
                    R.id.privacy_policy -> startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Conf.PRIVACY_POLICY_URL)))
                }

                return true
            }
        }

        menu.setNavigationItemSelectedListener(listener)
        vm.getLevel()
    }

    private fun determineNumberOfCoins(level: String, state: Boolean) {
        val currentCoinCount = binding.tvCoinCount.text.toString().toInt()
        when(level) {
            Conf.INTEGERS -> {
                if (state) vm.setNumberOfCoins(currentCoinCount + Conf.PRICE_FOR_CORRECT_INTEGERS_ANSWER)
                else vm.setNumberOfCoins(currentCoinCount + Conf.PRICE_FOR_WRONG_INTEGERS_ANSWER)
            }
            Conf.MODULES -> {
                if (state) vm.setNumberOfCoins(currentCoinCount + Conf.PRICE_FOR_CORRECT_MODULES_ANSWER)
                else vm.setNumberOfCoins(currentCoinCount + Conf.PRICE_FOR_WRONG_MODULES_ANSWER)
            }
            Conf.FRACTION -> {
                if (state) vm.setNumberOfCoins(currentCoinCount + Conf.PRICE_FOR_CORRECT_FRACTION_ANSWER)
                else vm.setNumberOfCoins(currentCoinCount + Conf.PRICE_FOR_WRONG_FRACTION_ANSWER)
            }
            Conf.EQUATION -> {
                if (state) vm.setNumberOfCoins(currentCoinCount + Conf.PRICE_FOR_WRONG_EQUATION_ANSWER)
                else vm.setNumberOfCoins(currentCoinCount + Conf.PRICE_FOR_WRONG_EQUATION_ANSWER)
            }
            Conf.DEGREE -> {
                if (state) vm.setNumberOfCoins(currentCoinCount + Conf.PRICE_FOR_CORRECT_DEGREE_ANSWER)
                else vm.setNumberOfCoins(currentCoinCount + Conf.PRICE_FOR_WRONG_DEGREE_ANSWER)
            }
            Conf.ROOT -> {
                if (state) vm.setNumberOfCoins(currentCoinCount + Conf.PRICE_FOR_CORRECT_ROOT_ANSWER)
                else vm.setNumberOfCoins(currentCoinCount + Conf.PRICE_FOR_WRONG_ROOT_ANSWER)
            }
            Conf.LINEAR_FUNCTIONS -> {
                if (state) vm.setNumberOfCoins(currentCoinCount + Conf.PRICE_FOR_CORRECT_LINEAR_FUNCTIONS_ANSWER)
                else vm.setNumberOfCoins(currentCoinCount + Conf.PRICE_FOR_WRONG_LINEAR_FUNCTIONS_ANSWER)
            }
            Conf.LOGARITHM -> {
                if (state) vm.setNumberOfCoins(currentCoinCount + Conf.PRICE_FOR_CORRECT_LOGARITHM_ANSWER)
                else vm.setNumberOfCoins(currentCoinCount + Conf.PRICE_FOR_WRONG_LOGARITHM_ANSWER)
            }
        }

        vm.getNumberOfCoins()
    }

    private fun trueAnswer() = with(binding) {
        tvTrueFalse.setTextColor(Color.parseColor( "#85DA97" ))
        tvTrueFalse.text = getString(R.string.it_is_true)

        if (soundsFlag) MediaPlayer.create(this@MainActivity, R.raw.true_answer).start()
    }

    private fun falseAnswer() = with(binding) {
        tvTrueFalse.setTextColor(Color.parseColor( "#E09F9F" ))
        tvTrueFalse.text = getString(R.string.it_is_false)

        if (soundsFlag) MediaPlayer.create(this@MainActivity, R.raw.false_answer).start()
    }

    private fun emptyAnswer() = with(binding) {
        tvTrueFalse.setTextColor(Color.parseColor( "#E09F9F" ))
        tvTrueFalse.text = getString(R.string.you_dont_enter)
    }

    private fun errorAnswer(text: Int) = with(binding) {
        tvTrueFalse.setTextColor(Color.parseColor( "#E09F9F" ))
        tvTrueFalse.text = getString(text)
    }

    private fun checkExample(userAnswer: Int, result: Int): Boolean {
        return userAnswer == result
    }

    private fun checkExample(userAnswer: Float, result: Float): Boolean {
        return userAnswer == result
    }

    private fun checkAddFloat(str: String): String {
        var res = str
        if (!str.contains(".")) res = "${str}.0"
        return res
    }

    private fun checkExampleGeneral(level: String, result: Int): Boolean? {
        var value: Boolean? = null
        if (currentResultTV.text.isNotEmpty() && currentResultTV.text != "-") {
            var res = checkExample(currentResultTV.text.toString().toInt(), result)
            if (res) { trueAnswer(); vm.addCorrectAnswer(level); }
            else { falseAnswer(); vm.addWrongAnswer(level) }

            value = res
            vm.getLevel()
        }
        else if (currentResultTV.text == "-") errorAnswer(R.string.you_dont_enter_number)
        else emptyAnswer()

        return value
    }

    private fun checkExampleGeneral(level: String, result: Float?): Boolean? {
        var value: Boolean? = null
        val ua = checkAddFloat(currentResultTV.text.toString())
        if (ua.isNotEmpty() && ua != "-") {
            val res = result?.let { checkExample(ua.toFloat(), it) }
            if (res == true) { trueAnswer(); vm.addCorrectAnswer(level); }
            else { falseAnswer(); vm.addWrongAnswer(level) }

            value = res
            vm.getLevel()
        }
        else if (ua == "-") errorAnswer(R.string.you_dont_enter_number)
        else if (ua.isNotEmpty()) {
            if (ua[-1] == ',') {
                errorAnswer(R.string.incorrect_data_entered)
            }
        }
        else emptyAnswer()

        return value
    }

    private fun checkExampleGeneral(level: String, result: ArrayList<Int?>?): Boolean? {
        var value: Boolean? = null
        if (currentResultTV.text.isNotEmpty() && currentResultTV.text.toString() != "-") {
            var res = false;
            if (currentResultTV.text.toString().toInt() == result?.get(0) || currentResultTV.text.toString().toInt() == result?.get(1)) {
                res = true
            }
            if (res) { trueAnswer(); vm.addCorrectAnswer(level); }
            else { falseAnswer(); vm.addWrongAnswer(level) }

            value = res
            vm.getLevel()
        }
        else if(currentResultTV.text == "-") errorAnswer(R.string.you_dont_enter_number)
        else if(currentResultTV.text.isNotEmpty()) {
            if (currentResultTV.text[-1] == ',') {
                errorAnswer(R.string.incorrect_data_entered)
            }
        }
        else emptyAnswer()

        return value
    }

    private fun allClear() {
        if (currentResultTV.text.isNotEmpty()) currentResultTV.text = ""
        else {
            binding.tvTrueFalse.setTextColor(Color.rgb(224, 159, 159))
            binding.tvTrueFalse.text = getString(R.string.you_dont_enter)
        }
    }

    private fun clear() {
        val arr = currentResultTV.text.toMutableList()

        if (arr.size != 0) {
            arr.removeAt(arr.size - 1)
            currentResultTV.text = arr.joinToString("")
        } else {
            binding.tvTrueFalse.setTextColor(Color.rgb(224, 159, 159))
            binding.tvTrueFalse.text = getString(R.string.you_dont_enter)
        }
    }

    @SuppressLint("SetTextI18n")
    fun onClickCalcButton(view: View) {
        when (view.id) {
            R.id.one   -> currentResultTV.text = "${currentResultTV.text}1"
            R.id.two   -> currentResultTV.text = "${currentResultTV.text}2"
            R.id.three -> currentResultTV.text = "${currentResultTV.text}3"
            R.id.four  -> currentResultTV.text = "${currentResultTV.text}4"
            R.id.five  -> currentResultTV.text = "${currentResultTV.text}5"
            R.id.six   -> currentResultTV.text = "${currentResultTV.text}6"
            R.id.seven -> currentResultTV.text = "${currentResultTV.text}7"
            R.id.eight -> currentResultTV.text = "${currentResultTV.text}8"
            R.id.nine  -> currentResultTV.text = "${currentResultTV.text}9"
            R.id.zero  -> currentResultTV.text = "${currentResultTV.text}0"
            R.id.comma -> if (currentResultTV.text.isNotEmpty()) currentResultTV.text = "${currentResultTV.text}."
            R.id.minus -> if (currentResultTV.text.isEmpty()) currentResultTV.text = "${currentResultTV.text}-"
            R.id.allClear -> allClear()
            R.id.clear    -> clear()
        }
    }

    private fun showHideLayout(showLayout: ConstraintLayout) = with(binding) {
        constraintDegree.visibility            = View.INVISIBLE
        constraintSimple.visibility            = View.INVISIBLE
        constraintModules.visibility           = View.INVISIBLE
        constraintFraction.visibility          = View.INVISIBLE
        constraintEquation.visibility          = View.INVISIBLE
        constraintFactorial.visibility         = View.INVISIBLE
        constraintLogarithm.visibility         = View.INVISIBLE
        constraintRoot.visibility              = View.INVISIBLE
        constraintLinearFunction.visibility    = View.INVISIBLE
        constraintCombinations.visibility      = View.INVISIBLE
        constraintAccommodation.visibility     = View.INVISIBLE
        showLayout.visibility                  = View.VISIBLE
    }

    private fun showIntegersExample(num1: Int, sign: String, num2: Int) = with(binding) {
        num1Simple.text = num1.toString()
        signSimple.text = sign
        num2Simple.text = num2.toString()

        Log.d("TaskLog", "Show Integers Example")
    }

    private fun showModulesExample(number1: Int, sign: String, number2: Int) = with(binding) {
        Log.d("Looog", "$number1 $sign $number2 - show")
        number1Modules.text = number1.toString()
        signModules.text    = sign
        number2Modules.text = number2.toString()
    }

    private fun showDecimalsFractionExample(number1: Float, sign: String, number2: Float) = with(binding) {
        val tv1 = TextView(this@MainActivity)
        tv1.text = number1.toString()
        val tv2 = TextView(this@MainActivity)
        tv2.text = number2.toString()

        tv1.textSize = 24F
        tv2.textSize = 24F

        fractionLinearLayout.removeAllViews()
        fractionLinearLayout2.removeAllViews()

        fractionLinearLayout.addView(tv1)
        signForFraction.text = sign
        fractionLinearLayout2.addView(tv2)
    }

    private fun showCommonFractionExample(numer1: Int, denomin1: Int, sign: String, numer2: Int, denomin2: Int) = with(binding) {
        val numerator1 = TextView(this@MainActivity)
        val numerator2 = TextView(this@MainActivity)
        val line = ImageView(this@MainActivity)
        val line2 = ImageView(this@MainActivity)
        val denominator1 = TextView(this@MainActivity)
        val denominator2 = TextView(this@MainActivity)

        numerator1.text = numer1.toString()
        numerator2.text = numer2.toString()
        line.setImageResource(R.drawable.ic_line_black_horizontal)
        line2.setImageResource(R.drawable.ic_line_black_horizontal)
        denominator1.text = denomin1.toString()
        denominator2.text = denomin2.toString()

        line.setPadding(0, 8, 0, 8)
        line2.setPadding(0, 8, 0, 8)

        numerator1.textSize = 24F
        numerator2.textSize = 24F
        denominator1.textSize = 24F
        denominator2.textSize = 24F

        fractionLinearLayout.removeAllViews()
        fractionLinearLayout2.removeAllViews()

        fractionLinearLayout.gravity = Gravity.CENTER_VERTICAL
        fractionLinearLayout2.gravity = Gravity.CENTER_VERTICAL

        fractionLinearLayout.addView(numerator1)
        fractionLinearLayout.addView(line)
        fractionLinearLayout.addView(denominator1)

        signForFraction.text = sign

        fractionLinearLayout2.addView(numerator2)
        fractionLinearLayout2.addView(line2)
        fractionLinearLayout2.addView(denominator2)
    }

    @SuppressLint("SetTextI18n")
    private fun showEquationExampleLive(type: String, a: Int, b: Int, c: Int, sign1: String, sign2: String) = with(binding) {
        if (type == "linear") {
            constraintEquationSquare.visibility = View.GONE
            constraintEquationLinear.visibility = View.VISIBLE

            equationLinearA.text = "${a}x"
            equationLinearSign.text = sign1
            equationLinearB.text = b.toString()
        }
        else if (type == "square") {
            constraintEquationLinear.visibility = View.GONE
            constraintEquationSquare.visibility = View.VISIBLE

            equationSquareA.text = "${a}x"
            equationSquareSign1.text = sign1
            equationSquareB.text = "${b}x"
            equationSquareSign2.text = sign2
            equationSquareC.text = c.toString()
        }
    }

    private fun showDegreeExample(base1: Int, exp1: Int, sign: String, base2: Int, exp2: Int) = with(binding) {
        baseOfDegree1.text = base1.toString()
        exponent1.text = exp1.toString()
        signForDegree.text = sign
        baseOfDegree2.text = base2.toString()
        exponent2.text = exp2.toString()
    }

    @SuppressLint("SetTextI18n")
    private fun showRootExample(type: String, sign: String, exponent1: Int, exponent2: Int, rootBase1: Int, rootBase2: Int) {
        val rect = Rect()
        rect[100, 100, 300] = 300

        val canvas = Canvas()
        lateinit var bitmap: Bitmap;

        if (type == Conf.ROOT_TYPES.ONE.name) {
            bitmap = Bitmap.createBitmap(( rootBase1.toString().length * 40f ).toInt() + 120, 125, Bitmap.Config.ARGB_8888)
            canvas.setBitmap(bitmap)

            val draw = DrawImpl(this, canvas)

            if (currentTheme == Conf.STANDART_THEM) draw.drawRoot1(rootBase1.toString(), exponent1.toString(), 5f, 64f, R.color.black)
            if (currentTheme == Conf.DARK_THEM) draw.drawRoot1(rootBase1.toString(), exponent1.toString(), 5f, 64f, R.color.white)
        }
        if (type == Conf.ROOT_TYPES.TWO.name) {
            bitmap = Bitmap.createBitmap(600, 125, Bitmap.Config.ARGB_8888)
            canvas.setBitmap(bitmap)

            val draw = DrawImpl(this, canvas)
            var color = R.color.black
            if (currentTheme == Conf.DARK_THEM) color = R.color.white

            draw.drawRoot1(rootBase1.toString(), exponent1.toString(), 5f, 64f, color)
            draw.drawSign(sign, 64f, rootBase1.toString(), color)
            draw.drawRoot2(rootBase2.toString(), exponent2.toString(),5f, 64f, 200f + rootBase1.toString().length * 40f, color)
        }

        val imageView = ImageView(this)
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.setBackgroundColor(Color.TRANSPARENT)
        imageView.scaleType = ImageView.ScaleType.CENTER
        imageView.setImageBitmap(bitmap)
        binding.constraintRootTwo.removeAllViews()
        binding.constraintRootTwo.addView(imageView)
    }

    @SuppressLint("SetTextI18n")
    private fun showLinearFunctionExample(x: Int, a: Int, sign: String, b: Int) = with(binding) {
        nameFunLinear.text = "F($x)="
        axLinear.text = "${a}x"
        firstSignForLinearFun.text = sign
        bxLinear.text = "${b}x"
    }

    private fun showLogarithmExample(base1: Int, logarithmicNumber: Int) = with(binding) {
        baseOfLogarighm1.text = base1.toString()
        logarighmicNumber1.text = logarithmicNumber.toString()
    }

    private fun equalsAd(stateExample: Boolean) = with(binding) {
        if (!stateExample) falseAnswerCount += 1
        if (falseAnswerCount == 5) {
            AdFrame.visibility = View.VISIBLE
            watchAd.setOnClickListener {
                val a  = internetConnectionCheck()
                if (a) {
                    showAd(); AdFrame.visibility = View.INVISIBLE
                }
                else Toast.makeText(this@MainActivity, getString(R.string.enabled_internet), Toast.LENGTH_SHORT).show()
            }

            solveExamples.setOnClickListener { AdFrame.visibility = View.INVISIBLE }
            falseAnswerCount = 0
        }
    }

    private fun internetConnectionCheck(): Boolean {
        // Удалить
        val connectivity = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifi = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI)?.isConnected
        val mobile = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)?.isConnected
        var result = false

        if (wifi != null && mobile != null) {
            result = wifi || mobile
        }

        return result
    }

    fun showAd() {
        val mRewardedAd = RewardedAd(this)
        mRewardedAd.setAdUnitId(Conf.AdUnitIdForVideo)
        val rewardedEventListener = object: RewardedAdEventListener {
            override fun onAdLoaded() {
                mRewardedAd.show()
            }

            override fun onAdFailedToLoad(p0: AdRequestError) {
                Toast.makeText(this@MainActivity, getString(R.string.video_ad_unavailable), Toast.LENGTH_SHORT).show()
            }

            override fun onAdShown() {}
            override fun onAdDismissed() {}
            override fun onLeftApplication() {}
            override fun onReturnedToApplication() {}

            override fun onRewarded(p0: Reward) {
                vm.setNumberOfCoins(binding.tvCoinCount.text.toString().toInt() + 10)
                vm.getNumberOfCoins()
            }

            override fun onAdClicked() {}
            override fun onImpression(p0: ImpressionData?) {}
        }

        mRewardedAd.setRewardedAdEventListener(rewardedEventListener)

        mRewardedAd.loadAd(AdRequest.Builder().build())
    }

    fun interstitialAdvertising() {
        val mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.setAdUnitId(Conf.AdUnitIdForInterstitial)
        val interstitialEventListener = object: InterstitialAdEventListener {
            override fun onAdLoaded() { mInterstitialAd.show() }
            override fun onAdFailedToLoad(p0: AdRequestError) { Log.d(Conf.AD_LOG, p0.description) }
            override fun onAdShown() {}
            override fun onAdDismissed() {}
            override fun onAdClicked() {}
            override fun onLeftApplication() {}
            override fun onReturnedToApplication() {}
            override fun onImpression(p0: ImpressionData?) {}
        }
        mInterstitialAd.setInterstitialAdEventListener(interstitialEventListener)
        mInterstitialAd.loadAd(AdRequest.Builder().build())
    }
}