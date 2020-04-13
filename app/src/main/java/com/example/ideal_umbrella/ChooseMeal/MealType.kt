package com.example.ideal_umbrella.ChooseMeal

enum class MealType(val value: Int) {
    MEAL_SOUP(0),
    Meal_MAIN(1)
    ;

    companion object {
        fun fromInt(value: Int) = values().first { it.value == value }
    }
}