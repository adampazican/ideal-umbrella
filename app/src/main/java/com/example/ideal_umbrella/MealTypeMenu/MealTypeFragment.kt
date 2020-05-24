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

/**
 * Trieda predstavuje zoznam typov jedal
 */
class MealTypeFragment : Fragment() {

    private var listener: OnMealTypesFragmentInteractionListener? = null

    /**
     * Metoda, ktora sa zavola pri vytvoreni pohladu. Inicializuje layout, adapter a tlacidla na zobrazenie zhrnutia objednavky a na resetnutie objednavky
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_meal_type_list, container, false)

        if(!(MealContent.showingMeals.isEmpty() || MealContent.showingMeals.none { it.numberOfOrders > 0 })) {
            MainActivity.orderSummary?.isVisible = true
            MainActivity.orderReset?.isVisible= true
        }


        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = MyMealTypeRecyclerViewAdapter(MealTypesContent.ITEMS, listener)
            }
        }
        return view
    }

    /**
     * Metoda, ktora sa zavola pri zaniku pohladu, zabezpecuje schovanie tlacidla na zobrazenie zhrnutia objednavky a na resetnutie objednavky
     */
    override fun onDestroyView() {
        super.onDestroyView()
        MainActivity.orderSummary?.isVisible = false
        MainActivity.orderReset?.isVisible= false
    }

    /**
     * Metoda, ktora sa zavola pri pripojeni fragmentu do activity
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnMealTypesFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnListFragmentInteractionListener")
        }
    }

    /**
     * Metoda, ktora sa zavola pri odpojeni fragmentu do activity
     */
    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
