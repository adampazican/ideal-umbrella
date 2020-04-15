package com.example.ideal_umbrella.ChooseMeal

import java.util.ArrayList

object MealContent {
    val showingMeals: MutableList<Meal> = ArrayList()
    val allMeals: MutableList<Meal> = ArrayList()
    var tableNumber: Int = 0

    fun getAllMealsByType(type: Int): List<Meal> {
        return allMeals.filter { it.mealType.value == type }
    }

    fun getOrderedMeals(): List<Meal> {
        return allMeals.filter { it.numberOfOrders > 0 }
    }
}
