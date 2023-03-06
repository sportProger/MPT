package com.sportproger.mpt.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
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

class MainActivity: Base() {
    private lateinit var binding: ActivityMainBinding
    private val vm by viewModel<MainViewModel>()
    private lateinit var currentResultTV: TextView
    private var soundsFlag = false
    private var falseAnswerCount = 0

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
        vm.currentThemeLive().observe(this@MainActivity, { vm.setCurrentTheme(it) })

        vm.getNumberOfCoins()
        vm.numberOfCoinsLive().observe(this@MainActivity, { tvCoinCount.text = it.toString() })

        vm.integersExampleLive().observe(this@MainActivity, { example ->
            showIntegersExample(example.number1, example.sign, example.number2)
            equals.setOnClickListener {
                val stateExample = checkExampleGeneral(Conf.INTEGERS, example.result)
                Log.d(Conf.MY_LOG, "$stateExample - stateExample integers")
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
                showHideLayout(constraintCommonFraction)
                showCommonFractionExample(example.numerator1, example.denominator1, example.sign, example.numerator2, example.denominator2)
            }
            if (example.type == "decimals") {
                showHideLayout(constraintDecimalsFraction)
                showDecimalsFractionExample(example.number1, example.sign, example.number2)
            }
            equals.setOnClickListener {
                val stateExample = checkExampleGeneral(Conf.FRACTION, example.result)
                if (stateExample != null) {
                    val userAnswer = currentResultTV.text.toString().toInt()
                    currentResultTV.text = ""
                    vm.setFractionExample(FractionExampleSaveData(example.type, example.numerator1, example.denominator1, example.sign, example.numerator2, example.denominator2, example.number1, example.number2, example.floatResult, example.result,  userAnswer, stateExample))

                    equalsAd(stateExample)
                    determineNumberOfCoins(Conf.FRACTION, stateExample)
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
                    vm.setDegreeExample(DegreeExampleSaveData(
                        base1 = example.base1,
                        exponent1 = example.exponent1,
                        sign = example.sign,
                        base2 = example.base2,
                        exponent2 = example.exponent2,
                        result = example.result,
                        userAnswer = userAnswer,
                        stateExample = stateExample
                    ))

                    equalsAd(stateExample)
                    determineNumberOfCoins(Conf.DEGREE, stateExample)
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
            Log.d(Conf.MY_LOG, "$it - sounds state")
            soundsFlag = it
        })

        vm.levelLive().observe(this@MainActivity, {
            Log.d("Looog", "$it - level Live")
            when(it) {
                Conf.INTEGERS -> {
                    vm.generateIntegersExample(negativity = true)
                    showHideLayout(constraintSimple)
                    currentResultTV = resSimple
                }
                Conf.FRACTION -> {
                    vm.generateFractionExample()
                    currentResultTV = resFraction
                }
                Conf.MODULES -> {
                    vm.generateIntegersExample(negativity = true)
                    showHideLayout(constraintModules)
                    currentResultTV = resModules
                }
                Conf.DEGREE -> {
                    vm.generateDegreeExample()
                    showHideLayout(constraintDegree)
                    currentResultTV = resDegree
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
                        if (internetConnectionCheck()) {
                            startActivity(Intent(this@MainActivity, GamesActivity::class.java))
                        }
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
            Conf.DEGREE -> {
                if (state) vm.setNumberOfCoins(currentCoinCount + Conf.PRICE_FOR_CORRECT_DEGREE_ANSWER)
                else vm.setNumberOfCoins(currentCoinCount + Conf.PRICE_FOR_WRONG_DEGREE_ANSWER)
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

    private fun checkExampleGeneral(level: String, result: Int): Boolean? {
        var value: Boolean? = null
        if (currentResultTV.text.isNotEmpty() && currentResultTV.text != "-" && currentResultTV.text[-1] != ',') {
            val res = checkExample(currentResultTV.text.toString().toInt(), result)
            if (res) { trueAnswer(); vm.addCorrectAnswer(level); }
            else { falseAnswer(); vm.addWrongAnswer(level) }

            value = res
            vm.getLevel()
        }
        else if(currentResultTV.text == "-") errorAnswer(R.string.you_dont_enter_number)
        else if(currentResultTV.text[-1] == ',') errorAnswer(R.string.incorrect_data_entered)
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
            R.id.comma -> if (currentResultTV.text.isNotEmpty()) currentResultTV.text = "${currentResultTV.text},"
            R.id.minus -> if (currentResultTV.text.isEmpty()) currentResultTV.text = "${currentResultTV.text}-"
            R.id.allClear -> allClear()
            R.id.clear    -> clear()
        }
    }

    private fun showHideLayout(showLayout: ConstraintLayout) = with(binding) {
        Log.d("Looog", showLayout.toString())
        constraintDegree.visibility            = View.INVISIBLE
        constraintSimple.visibility            = View.INVISIBLE
        constraintModules.visibility           = View.INVISIBLE
        constraintDecimalsFraction.visibility  = View.INVISIBLE
        constraintCommonFraction.visibility    = View.INVISIBLE
        constraintFactorial.visibility         = View.INVISIBLE
        constraintLogarithm.visibility         = View.INVISIBLE
        constraintLinearFunction.visibility    = View.INVISIBLE
        constraintCombinations.visibility      = View.INVISIBLE
        constraintAccommodation.visibility     = View.INVISIBLE
        showLayout.visibility                  = View.VISIBLE
    }

    private fun showIntegersExample(num1: Int, sign: String, num2: Int) = with(binding) {
        num1Simple.text = num1.toString()
        signSimple.text = sign
        num2Simple.text = num2.toString()
    }

    private fun showModulesExample(number1: Int, sign: String, number2: Int) = with(binding) {
        Log.d("Looog", "$number1 $sign $number2 - show")
        number1Modules.text = number1.toString()
        signModules.text    = sign
        number2Modules.text = number2.toString()
    }

    private fun showDecimalsFractionExample(number1: Float, sign: String, number2: Float) = with(binding) {
        decimalsNumber1.text   = number1.toString()
        decimalsResult.text    = sign
        decimalsNumber2.text   = number2.toString()
    }

    private fun showCommonFractionExample(numer1: Int, denomin1: Int, sign: String, numer2: Int, denomin2: Int) = with(binding) {
        numerator1.text      = numer1.toString()
        denominator1.text    = denomin1.toString()
        numerator2.text      = numer2.toString()
        denominator2.text    = denomin2.toString()
        signForFraction.text = sign
    }

    private fun showDegreeExample(base1: Int, exp1: Int, sign: String, base2: Int, exp2: Int) = with(binding) {
        baseOfDegree1.text = base1.toString()
        exponent1.text = exp1.toString()
        signForDegree.text = sign
        baseOfDegree2.text = base2.toString()
        exponent2.text = exp2.toString()
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
                Log.d("Looog", a.toString() + " - connect")
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
                Log.d("Looog", "ad is loaded")
            }

            override fun onAdFailedToLoad(p0: AdRequestError) {
                Log.d("Looog", "$p0 - video error")
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