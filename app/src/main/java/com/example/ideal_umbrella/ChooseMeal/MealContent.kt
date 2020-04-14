package com.example.ideal_umbrella.ChooseMeal

import java.util.ArrayList

object MealContent {
    val meals: MutableList<Meal> = ArrayList()

    fun addItem(item: Meal) {
        meals.add(item)
    }
}
