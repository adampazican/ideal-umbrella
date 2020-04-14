package com.example.ideal_umbrella.ChooseMeal

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ideal_umbrella.*

class ChooseMealFragment : Fragment() {

    private var mealType: Int = 0
    private var listener: OnChooseMealFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivity.orderSummary?.setVisible(false) //TODO: hide this on destroy view probably so its not patchy everywhere

        arguments?.let {
            mealType = it.getInt("meal-type")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_choose_meal_list, container, false)

        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)

                if(MealContent.allMeals.isEmpty()) {
                    Thread {
                        val db = MainActivity.db
                        db.mealDao().getAll().forEach {
                            MealContent.allMeals.add(Meal(it.id, it.mealName, it.mealType, it.price))
                        }

                        MealContent.showingMeals.addAll(MealContent.allMeals.filter { it.mealType?.value == mealType })

                        activity?.runOnUiThread {
                            (adapter as MyChooseMealRecyclerViewAdapter).notifyDataSetChanged()
                        }
                    }.start()
                }
                else {
                    MealContent.showingMeals.clear()
                    MealContent.showingMeals.addAll(MealContent.allMeals.filter { it.mealType?.value == mealType })
                }

                adapter = MyChooseMealRecyclerViewAdapter(MealContent.showingMeals, listener)
            }
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnChooseMealFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
