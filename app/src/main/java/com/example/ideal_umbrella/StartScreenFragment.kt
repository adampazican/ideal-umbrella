package com.example.ideal_umbrella

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.ideal_umbrella.Database.Meal

class StartScreenFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_start_screen, container, false)
        val startOrderButton = view?.findViewById(R.id.start_order_button) as Button
        val orderListButton = view.findViewById(R.id.order_list_button) as Button
        val updateMealsButton = view.findViewById(R.id.update_meals_button) as Button

        startOrderButton.setOnClickListener {
            TablesDialog().show(parentFragmentManager, "example")
        }

        orderListButton.setOnClickListener {
            Navigation.findNavController(activity as Activity, R.id.nav_host_fragment).navigate(R.id.ordersFragment)
        }

        val db = MainActivity.db

        updateMealsButton.setOnClickListener {
            HttpHandler.getAllMeals { mealArray: ArrayList<com.example.ideal_umbrella.ChooseMeal.Meal>?, success: Boolean ->
                if (success && mealArray != null) {
                    db.mealDao().deleteAll()
                    db.mealDao().insertAll(mealArray.map {
                        Meal(it.id, it.mealName, it.mealType, it.price)
                    })

                    activity?.runOnUiThread {
                        Toast.makeText(activity, context?.getString(R.string.update_meals_success), Toast.LENGTH_SHORT).show()
                    }
                }
                else {
                    activity?.runOnUiThread {
                        Toast.makeText(activity, context?.getString(R.string.update_meals_error), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        return view
    }
}