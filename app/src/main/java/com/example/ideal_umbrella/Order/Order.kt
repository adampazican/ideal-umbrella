package com.example.ideal_umbrella.Order

import com.example.ideal_umbrella.ChooseMeal.Meal

data class Order(val meals: List<Meal>, val tableNumber: Int, val sumTotal: Int);