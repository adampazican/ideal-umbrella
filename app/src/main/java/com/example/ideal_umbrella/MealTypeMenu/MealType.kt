package com.example.ideal_umbrella.MealTypeMenu

enum class MealType(val value: Int) {
    SOUP(0),
    MAIN_MEAL(1)
    ;

    companion object {
        fun fromInt(value: Int) = values().first { it.value == value }
    }
}