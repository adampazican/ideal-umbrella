package com.example.ideal_umbrella.MealTypeMenu

import com.example.ideal_umbrella.ChooseMeal.Meal
import java.util.*

object MealTypesContent {
    val ITEMS: MutableList<MealType> = ArrayList()

    init {
        for (i in MealType.values().indices) {
            ITEMS.add(
                MealType.fromInt(i)
            )
        }
    }
}
