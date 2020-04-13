package com.example.ideal_umbrella.Database

import androidx.room.TypeConverter
import com.example.ideal_umbrella.ChooseMeal.MealType

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