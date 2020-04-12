package com.example.ideal_umbrella

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.net.URL

class MainActivity : AppCompatActivity(), ChooseMealFragment.OnListFragmentInteractionListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onListFragmentInteraction(item: Meal?) {

    }
}
