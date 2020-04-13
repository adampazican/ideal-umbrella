package com.example.ideal_umbrella.ChooseMeal

import java.util.ArrayList

object MealContent {
    val order: MutableList<Meal> = ArrayList()
    val meals: MutableList<Meal> = ArrayList()

    fun addItem(item: Meal) {
        meals.add(item)
    }

    fun addItems(items: List<Meal>) {
       meals.addAll(items);
    }

    fun removeNotOrdered(): List<Meal> {
        return meals.filter {
            it.numberOfOrders < 0
        }
    }
}
