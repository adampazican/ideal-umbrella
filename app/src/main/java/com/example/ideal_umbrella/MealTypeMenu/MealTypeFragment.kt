package com.example.ideal_umbrella.MealTypeMenu

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ideal_umbrella.ChooseMeal.MealContent
import com.example.ideal_umbrella.MainActivity
import com.example.ideal_umbrella.R

class MealTypeFragment : Fragment() {

    private var listener: OnMealTypesFragmentInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_meal_type_list, container, false)

        MainActivity.orderSummary?.isVisible = !(MealContent.showingMeals.isEmpty() || MealContent.showingMeals.filter { it.numberOfOrders > 0 }.isEmpty()) //TODO: hide this on destroy view probably so its not patchy everywhere

        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = MyMealTypeRecyclerViewAdapter(MealTypesContent.ITEMS, listener)
            }
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnMealTypesFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
