package com.sportproger.mpt.general

object Conf {
    const val MY_LOG = "MY_LOG"
    const val AD_LOG = "AD_LOG"
    const val STANDART_THEM = "STANDART_THEM"
    const val DARK_THEM = "DARK_THEM"
    const val PRIVACY_POLICY_URL = "https://docs.google.com/document/d/1JEzeYJVHo6sH7ld8wvFkSFbiVF5QLamygi524pIrcOc/edit"
    const val SDK_KEY = "52490166856894734142"
    const val AdUnitIdForVideo = "R-M-1716465-1"
    const val AdUnitIdForBanner = "R-M-1716465-2"
    const val AdUnitIdForInterstitial = "R-M-1716465-3"
    const val START_COIN = 1000

    const val UNAMBIGUOUS = "UNAMBIGUOUS" // Однозначные
    const val TWO_DIGIT   = "TWO_DIGIT" // Двузначные
    const val THREE_DIGIT = "THREE_DIGIT" // Трехзначные

    const val UNAMBIGUOUS_RU = "Однозначные" // Однозначные
    const val TWO_DIGIT_RU   = "Двузначные" // Двузначные
    const val THREE_DIGIT_RU = "Трехзначные" // Трехзначные

    const val INTEGERS = "INTEGERS" // Натуральные числа
    const val INTEGERS_RU = "Целые числа" // Натуральные числа

    const val MODULES = "MODULE"
    const val MODULES_RU = "Модули"

    const val FRACTION = "FRACTION"
    const val FRACTION_RU = "Дроби"

    const val EQUATION = "EQUATION"
    const val EQUATION_RU = "Уравнения"

    const val DEGREE = "DEGREE"
    const val DEGREE_RU = "Степени"

    const val ROOT = "ROOT"
    const val ROOT_RU = "Корни"

    const val LINEAR_FUNCTIONS = "LINEAR_FUNCTIONS"
    const val LINEAR_FUNCTIONS_RU = "Линейные функции"

    const val LOGARITHM = "LOGARITHM"
    const val LOGARITHM_RU = "Логарифмы"

    enum class ROOT_TYPES(type: String) {
        ONE("one"),
        TWO("two")
    }

    const val PRICE_FOR_DARK_THEME = 400

    const val PRICE_FOR_CORRECT_INTEGERS_ANSWER = 2
    const val PRICE_FOR_WRONG_INTEGERS_ANSWER = -1

    const val PRICE_FOR_CORRECT_MODULES_ANSWER = 3
    const val PRICE_FOR_WRONG_MODULES_ANSWER = -2

    const val PRICE_FOR_CORRECT_FRACTION_ANSWER = 4
    const val PRICE_FOR_WRONG_FRACTION_ANSWER = -3

    const val PRICE_FOR_CORRECT_EQUATION_ANSWER = 5
    const val PRICE_FOR_WRONG_EQUATION_ANSWER = -4

    const val PRICE_FOR_CORRECT_DEGREE_ANSWER = 6
    const val PRICE_FOR_WRONG_DEGREE_ANSWER = -5

    const val PRICE_FOR_CORRECT_ROOT_ANSWER = 7
    const val PRICE_FOR_WRONG_ROOT_ANSWER = -6

    const val PRICE_FOR_CORRECT_LINEAR_FUNCTIONS_ANSWER = 8
    const val PRICE_FOR_WRONG_LINEAR_FUNCTIONS_ANSWER = -7

    const val PRICE_FOR_CORRECT_LOGARITHM_ANSWER = 9
    const val PRICE_FOR_WRONG_LOGARITHM_ANSWER = -8

    const val PRICE_FOR_INTEGERS = 0
    const val PRICE_FOR_MODULES = 50
    const val PRICE_FOR_FRACTION = 100
    const val PRICE_FOR_EQUATION = 150
    const val PRICE_FOR_DEGREE = 200
    const val PRICE_FOR_ROOT = 250
    const val PRICE_FOR_LINEAR_FUNCTIONS = 300
    const val PRICE_FOR_LOGARITHM = 350

    //  Games

    const val CONNECTION_GAME = "Соедини"
    const val SEARCH_GAME = "Поиск"
}