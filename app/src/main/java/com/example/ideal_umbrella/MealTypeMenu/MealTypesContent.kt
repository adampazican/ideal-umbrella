package com.example.ideal_umbrella.MealTypeMenu

import java.util.*

/**
 * Singleton trieda, ktora drzi vsetky typy jedal v liste
 */
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
