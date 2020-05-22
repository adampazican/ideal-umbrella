package com.example.ideal_umbrella.ChooseMeal

import android.widget.Button
import android.widget.TextView

/**
 * Interface, ktory sa pouziva na odchytenie akcie stlacenia tlacidiel plus a minus v liste jedal urciteho typu
 */
interface OnChooseMealFragmentInteractionListener {
    fun onListFragmentInteractionPlus(button: Button, item: Meal?, adapter: MyChooseMealRecyclerViewAdapter, numberView: TextView)
    fun onListFragmentInteractionMinus(button: Button, item: Meal?, adapter: MyChooseMealRecyclerViewAdapter, numberView: TextView)
}