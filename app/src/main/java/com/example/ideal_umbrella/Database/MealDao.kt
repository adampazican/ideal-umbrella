package com.example.ideal_umbrella.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MealDao {
    @Query("SELECT * FROM meal")
    fun getAll(): List<Meal>

    @Query("SELECT * FROM meal WHERE meal_type = :mealType")
    fun getAllByMealType(mealType: Int): List<Meal>

    @Insert
    fun insertAll(vararg meals: Meal)

    @Insert
    fun insertAll(meals: List<Meal>)

    @Delete
    fun delete(meal: Meal)

    @Query("DELETE FROM meal")
    fun deleteAll();
}
