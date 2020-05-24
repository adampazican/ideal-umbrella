package com.example.ideal_umbrella.Order

/**
 * Interface, ktory sa pouziva na odchytenie akcie stlacenia jednej z objednavok v zozname objednavok
 */
interface OnOrdersFragmentInteractionListener {
    fun onOrdersFragmentInteraction(order: Order?, adapter: MyOrdersRecyclerViewAdapter)
}