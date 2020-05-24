package com.example.ideal_umbrella.ChooseMeal

import java.util.ArrayList

/**
 * Singleton trieda, ktora drzi vsetky jedla, jedla ktore sa maju zobrazit a cislo stola, pre ktory sa zaznamenava objednavka
 */
object MealContent {
    val showingMeals: MutableList<Meal> = ArrayList()
    val allMeals: MutableList<Meal> = ArrayList()
    var tableNumber: Int = 0

    /**
     * @return vracia novy list jedal, ktory obsahuje jedla typu [type]
     */
    fun getAllMealsByType(type: Int): List<Meal> {
        return allMeals.filter { it.mealType.value == type }
    }

    /**
     * @return vracia novy list jedal, ktory obsahuje jedla objednane viac ako nula krat
     */
    fun getOrderedMeals(): List<Meal> {
        return allMeals.filter { it.numberOfOrders > 0 }
    }
}
