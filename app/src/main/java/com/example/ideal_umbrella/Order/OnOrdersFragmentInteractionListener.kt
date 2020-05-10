package com.example.ideal_umbrella.Order

/**
 * Interface, ktory sa pouziva na odchitenie akcie stlacenia jednej z obejdnavok v zozname objednavok
 */
interface OnOrdersFragmentInteractionListener {
    fun onOrdersFragmentInteraction(order: Order?, adapter: MyOrdersRecyclerViewAdapter)
}