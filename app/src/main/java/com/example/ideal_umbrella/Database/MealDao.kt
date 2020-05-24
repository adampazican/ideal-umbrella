package com.example.ideal_umbrella.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/**
 * Interface definujuci vsetky pouzivane operacie nad databazou
 */
@Dao
interface MealDao {
    /**
     * Selectuje vsetky jedla z databazy
     */
    @Query("SELECT * FROM meal")
    fun getAll(): List<Meal>

    /**
     * Vklada do databayz list jedal
     */
    @Insert
    fun insertAll(meals: List<Meal>)

    /**
     * Vymaze vsetky jedla z databazy
     */
    @Query("DELETE FROM meal")
    fun deleteAll()
}
