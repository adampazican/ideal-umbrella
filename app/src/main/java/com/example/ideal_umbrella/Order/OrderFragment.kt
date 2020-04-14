package com.example.ideal_umbrella.Order

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

class OrderFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivity.orderSummary?.setVisible(false)
        MainActivity.orderPlace?.setVisible(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_order_list, container, false)

        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = MyOrderRecyclerViewAdapter(MealContent.allMeals.filter { it.numberOfOrders > 0 })
            }
        }
        return view
    }
}
