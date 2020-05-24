package com.example.ideal_umbrella.Database

import androidx.room.TypeConverter
import com.example.ideal_umbrella.MealTypeMenu.MealType

/**
 * Konverter typu jedla na integer, ktory sa vola automaticky pri poziadavkach na databazu (enum -> int pri ukladani, int -> enum pri citani)
 */
object MealTypeConverter {

    @TypeConverter
    @JvmStatic
    fun toMeal(value: Int): MealType {
        return MealType.fromInt(value)
    }

    @TypeConverter
    @JvmStatic
    fun toInt(mealType: MealType): Int {
        return mealType.value
    }
}