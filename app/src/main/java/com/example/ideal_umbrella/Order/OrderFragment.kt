package com.example.ideal_umbrella.Order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ideal_umbrella.ChooseMeal.MealContent
import com.example.ideal_umbrella.MainActivity
import com.example.ideal_umbrella.R

class OrderFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_order_list, container, false)

        MainActivity.orderPlace?.isVisible = true

        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = MyOrderRecyclerViewAdapter(MealContent.getOrderedMeals(), context)
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        MainActivity.orderPlace?.isVisible = false
    }
}
