package com.sportproger.mpt.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.sportproger.mpt.general.Base
import com.sportproger.mpt.databinding.ActivityLevelBinding
import com.sportproger.mpt.general.Conf
import com.sportproger.mpt.presentation.recycler.*
import com.sportproger.mpt.presentation.viewmodel.LevelViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LevelActivity: Base() {
    lateinit var binding: ActivityLevelBinding
    private val integersAdapter = IntegersAdapter()
    private val modulesAdapter = ModulesAdapter()
    private val fractionAdapter = FractionAdapter()
    private val equationAdapter = EquationAdapter()
    private val degreeAdapter = DegreeAdapter()
    private val rootAdapter = RootAdapter()
    private val linearFunctionAdapter = LinearFunctionAdapter()
    private val logarithmAdapter = LogarithmAdapter()
    private val vm by viewModel<LevelViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLevelBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        back(); share(binding.share)

        val levelName = intent.getSerializableExtra("name").toString()
        binding.nameLevel.text = levelName

        vm.getCountOfCoins()
        vm.numberOfCoinsLive().observe(this, {
            binding.tvCoinCount.text = it.toString()
        })

        vm.getLevel()
        vm.levelLive().observe(this, { level ->
            when(level) {
                Conf.INTEGERS -> initIntegers()
                Conf.MODULES  -> initModules()
                Conf.FRACTION -> initFractions()
                Conf.EQUATION -> initEquation()
                Conf.DEGREE   -> initDegree()
                Conf.ROOT     -> initRoot()
                Conf.LINEAR_FUNCTIONS -> initLinearFunction()
                Conf.LOGARITHM -> initLogarithm()
            }
        })

        binding.start.setOnClickListener {
            startActivity(Intent(this@LevelActivity, MainActivity::class.java))
            finish()
        }
    }

    private fun initGeneral(level: String) = with(binding) {
        clearBtn.setOnClickListener {
            rvExamples.removeAllViews()
            vm.removeNumberOfCorrectAnswers(level)
            vm.removeNumberOfWrongAnswers(level)
            when(level) {
                Conf.INTEGERS -> {
                    vm.removeAllIntegersExamples()
                    integersAdapter.clear()
                }
                Conf.MODULES  -> {
                    vm.removeAllModulesExamples()
                    modulesAdapter.clear()
                }
                Conf.FRACTION -> {
                    vm.removeAllFractionsExamples()
                    fractionAdapter.clear()
                }
                Conf.EQUATION -> {
                    vm.removeAllEquationExamples()
                    equationAdapter.clear()
                }
                Conf.DEGREE -> {
                    vm.removeAllDegreeExample()
                    degreeAdapter.clear()
                }
                Conf.ROOT -> {
                    vm.removeAllRootExample()
                    rootAdapter.clear()
                }
                Conf.LINEAR_FUNCTIONS -> {
                    vm.removeAllLinearFunctionsExample()
                    linearFunctionAdapter.clear()
                }
                Conf.LOGARITHM -> {
                    vm.removeAllLogarithmExample()
                    logarithmAdapter.clear()
                }
            }

            vm.getNumberOfCorrectAnswer(level)
            vm.getNumberOfWrongAnswer(level)
        }

        vm.getNumberOfCorrectAnswer(level)
        vm.numberOfCorrectAnswerLive().observe(this@LevelActivity, {
            trueCount.text = it.toString()
        })

        vm.getNumberOfWrongAnswer(level)
        vm.numberOfWrongAnswerLive().observe(this@LevelActivity, {
            Log.d(Conf.MY_LOG, it.toString() + "number of wrong answers")
            falseCount.text = it.toString()
        })
    }

    private fun initIntegers() = with(binding) {
        rvExamples.layoutManager = LinearLayoutManager(this@LevelActivity)
        rvExamples.adapter = integersAdapter
        vm.getAllIntegersExamples()
        vm.getIntegersExamplesLive().observe(this@LevelActivity, {
            it.forEach { example ->
                integersAdapter.addExample(example)
            }
        })

        initGeneral(Conf.INTEGERS)

        falseAnswer.text = Conf.PRICE_FOR_WRONG_INTEGERS_ANSWER.toString()
        trueAnswer.text = Conf.PRICE_FOR_CORRECT_INTEGERS_ANSWER.toString()
    }

    private fun initModules() = with(binding) {
        rvExamples.layoutManager = LinearLayoutManager(this@LevelActivity)
        rvExamples.adapter = modulesAdapter
        vm.getAllModulesExamples()
        vm.getModulesExamplesLive().observe(this@LevelActivity, {
            it.forEach { example ->
                modulesAdapter.addExample(example)
            }
        })

        initGeneral(Conf.MODULES)

        falseAnswer.text = Conf.PRICE_FOR_WRONG_MODULES_ANSWER.toString()
        trueAnswer.text = Conf.PRICE_FOR_CORRECT_MODULES_ANSWER.toString()
    }

    private fun initFractions() = with(binding) {
        rvExamples.layoutManager = LinearLayoutManager(this@LevelActivity)
        rvExamples.adapter = fractionAdapter
        vm.getAllFractionsExample()
        vm.getFractionExamplesLive().observe(this@LevelActivity, {
            it.forEach { example -> fractionAdapter.addExample(example) }
        })

        initGeneral(Conf.FRACTION)

        falseAnswer.text = Conf.PRICE_FOR_CORRECT_FRACTION_ANSWER.toString()
        trueAnswer.text = Conf.PRICE_FOR_WRONG_FRACTION_ANSWER.toString()
    }

    private fun initEquation() = with(binding) {
        rvExamples.layoutManager = LinearLayoutManager(this@LevelActivity)
        rvExamples.adapter = equationAdapter
        vm.getAllEquationExample()
        vm.getEquationExamplesLive().observe(this@LevelActivity, {
            it.forEach { example -> equationAdapter.addExample(example) }
        })

        initGeneral(Conf.EQUATION)

        falseAnswer.text = Conf.PRICE_FOR_CORRECT_EQUATION_ANSWER.toString()
        trueAnswer.text = Conf.PRICE_FOR_WRONG_EQUATION_ANSWER.toString()
    }

    private fun initDegree() = with(binding) {
        rvExamples.layoutManager = LinearLayoutManager(this@LevelActivity)
        rvExamples.adapter = degreeAdapter
        vm.getAllDegreeExample()
        vm.getDegreeExamplesLive().observe(this@LevelActivity, {
            it.forEach { example -> degreeAdapter.addExample(example) }
        })

        initGeneral(Conf.DEGREE)

        falseAnswer.text = Conf.PRICE_FOR_CORRECT_DEGREE_ANSWER.toString()
        trueAnswer.text = Conf.PRICE_FOR_WRONG_DEGREE_ANSWER.toString()
    }

    private fun initRoot() = with(binding) {
        rvExamples.layoutManager = LinearLayoutManager(this@LevelActivity)
        rvExamples.adapter = rootAdapter
        vm.getAllRootExample()
        vm.getRootExamplesLive().observe(this@LevelActivity, {
            it.forEach { example -> rootAdapter.addExample(example) }
        })

        initGeneral(Conf.ROOT)

        falseAnswer.text = Conf.PRICE_FOR_CORRECT_ROOT_ANSWER.toString()
        trueAnswer.text = Conf.PRICE_FOR_WRONG_ROOT_ANSWER.toString()
    }

    private fun initLinearFunction() = with(binding) {
        rvExamples.layoutManager = LinearLayoutManager(this@LevelActivity)
        rvExamples.adapter = linearFunctionAdapter
        vm.getAllLinearFunctionExample()
        vm.getLinearFunctionExamplesLive().observe(this@LevelActivity, {
            it.forEach { example -> linearFunctionAdapter.addExample(example) }
        })

        initGeneral(Conf.LINEAR_FUNCTIONS)

        falseAnswer.text = Conf.PRICE_FOR_CORRECT_LINEAR_FUNCTIONS_ANSWER.toString()
        trueAnswer.text = Conf.PRICE_FOR_WRONG_LINEAR_FUNCTIONS_ANSWER.toString()
    }

    private fun initLogarithm() = with(binding) {
        rvExamples.layoutManager = LinearLayoutManager(this@LevelActivity)
        rvExamples.adapter = logarithmAdapter
        vm.getAllLogarithmExample()
        vm.getLogarithmExamplesLive().observe(this@LevelActivity, {
            it.forEach { example -> logarithmAdapter.addExample(example) }
        })

        initGeneral(Conf.LOGARITHM)

        falseAnswer.text = Conf.PRICE_FOR_CORRECT_LINEAR_FUNCTIONS_ANSWER.toString()
        trueAnswer.text = Conf.PRICE_FOR_WRONG_LINEAR_FUNCTIONS_ANSWER.toString()
    }
}