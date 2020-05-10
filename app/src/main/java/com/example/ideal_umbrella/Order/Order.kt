package com.example.ideal_umbrella.Order

import com.example.ideal_umbrella.ChooseMeal.Meal

/**
 * Datova trieda, ktora predstavuje objednavku
 */
data class Order(val meals: ArrayList<Meal> = ArrayList(), val tableNumber: Int, val sumTotal: Int, var id:Int, var finished: Boolean = false)