package com.example.ideal_umbrella

import java.util.ArrayList

object MealContent {

   val meals: MutableList<Meal> = ArrayList()

    fun addItem(item: Meal) {
        meals.add(item)
    }

    private fun addItems(items: List<Meal>) {
       meals.addAll(items);
    }
}
