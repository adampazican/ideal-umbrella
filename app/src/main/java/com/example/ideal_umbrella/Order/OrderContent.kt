package com.example.ideal_umbrella.Order

import androidx.recyclerview.widget.RecyclerView

/**
 * Singleton trieda, ktora drzi vsetky objednavky drzane aplikaciou a odkaz na pohlad, ktory zobrazuje list objednavok
 */
object OrderContent {
    val orders: MutableList<Order> = ArrayList()
    var view: RecyclerView? = null
}