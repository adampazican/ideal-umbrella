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

/**
 * Trieda predstavuje obrazovku detailu vytvaranej objednavky. Obsahuje tlacidlo na potvrdenie objednavky, po ktoreho stlaceni sa objednavka posle na server
 */
class OrderFragment : Fragment() {

    /**
     * Metoda, ktora sa zavola pri vytvoreni pohladu. Inicializuje layout, adapter a tlacidlo na potvrdenie objednavky
     */
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

    /**
     * Metoda, ktora sa zavola pri zaniku pohladu, zabezpecuje schovanie tlacidla na potvrdenie objednavky
     */
    override fun onDestroyView() {
        super.onDestroyView()
        MainActivity.orderPlace?.isVisible = false
    }
}
