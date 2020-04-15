package com.example.ideal_umbrella.ChooseMeal

import java.util.ArrayList

object MealContent {
    val showingMeals: MutableList<Meal> = ArrayList() /////TODO: check common access  patterns of members through filters and such and create accessor methods
    val allMeals: MutableList<Meal> = ArrayList()
    var tableNumber: Int = 0
}
