package com.example.ideal_umbrella.ChooseMeal

import android.widget.Button
import android.widget.TextView

interface OnChooseMealFragmentInteractionListener {
    fun onListFragmentInteractionPlus(button: Button, item: Meal?, adapter: MyChooseMealRecyclerViewAdapter, numberView: TextView)
    fun onListFragmentInteractionMinus(button: Button, item: Meal?, adapter: MyChooseMealRecyclerViewAdapter, numberView: TextView)
}