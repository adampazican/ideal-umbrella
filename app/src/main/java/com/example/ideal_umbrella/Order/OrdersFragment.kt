package com.example.ideal_umbrella.Order

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ideal_umbrella.MainActivity
import com.example.ideal_umbrella.R

/**
 * Trieda predstavuje obrazovku, ktora zobrazuje zoznam objednavok
 */
class OrdersFragment : Fragment() {

    private var listener: OnOrdersFragmentInteractionListener? = null

    /**
     * Metoda, ktora sa zavola pri vytvoreni pohladu. Inicializuje layout, adapter a tlacidlo na nacitanie objednavok zo servera
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_orders_list, container, false)

        MainActivity.orderRefresh?.isVisible = true

        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = MyOrdersRecyclerViewAdapter(OrderContent.orders, listener, context)
                OrderContent.view = this
            }
        }
        return view
    }

    /**
     * Metoda, ktora sa zavola pri zaniku pohladu, zabezpecuje schovanie tlacidla na nacitanie objednavok zo servera
     */
    override fun onDestroyView() {
        super.onDestroyView()
        MainActivity.orderRefresh?.isVisible = false
    }

    /**
     * Metoda, ktora sa zavola pri pripojeni fragmentu do activity
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnOrdersFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnListFragmentInteractionListener")
        }
    }

    /**
     * Metoda, ktora sa zavola pri pripojeni fragmentu do activity
     */
    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
