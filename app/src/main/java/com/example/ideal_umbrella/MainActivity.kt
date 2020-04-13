package com.example.ideal_umbrella

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ideal_umbrella.ChooseMeal.ChooseMealFragment
import com.example.ideal_umbrella.ChooseMeal.Meal
import com.example.ideal_umbrella.ChooseMeal.OnListFragmentInteractionListener

class MainActivity : AppCompatActivity(), OnListFragmentInteractionListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onListFragmentInteraction(item: Meal?) {
    }
}
