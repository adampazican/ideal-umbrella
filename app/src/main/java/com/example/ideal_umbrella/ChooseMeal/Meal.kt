package com.example.ideal_umbrella.ChooseMeal

import com.example.ideal_umbrella.MealTypeMenu.MealType

/**
 * Datova trieda ktora predstavuje jedno jedlo z ponuky
 */
data class Meal(val id: Int, val mealName: String, val mealType: MealType, val price: Int, var numberOfOrders: Int = 0)