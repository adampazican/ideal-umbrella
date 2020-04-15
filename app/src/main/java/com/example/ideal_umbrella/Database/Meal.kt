package com.example.ideal_umbrella.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ideal_umbrella.MealTypeMenu.MealType

@Entity
data class Meal(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "meal_name") val mealName: String,
    @ColumnInfo(name = "meal_type") val mealType: MealType,
    @ColumnInfo(name = "price") val price: Int
)