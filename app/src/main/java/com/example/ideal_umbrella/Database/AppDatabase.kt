package com.example.ideal_umbrella.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * Trieda predstavujuca databazovy system aplikacie. Inicializuju sa cez nu jednotlive databazy,
 * entity, verzia databazy pre jednoduche migracie a converteri dat.
 */
@Database(entities = [Meal::class], version = 1)
@TypeConverters(MealTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mealDao(): MealDao

    companion object {
        const val DB_NAME: String = "waiter"
    }
}