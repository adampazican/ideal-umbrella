package com.example.ideal_umbrella.Order

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.LinearLayout
import com.example.ideal_umbrella.R

class OrdersFragment : Fragment() {

    private var listener: OnOrdersFragmentInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_orders_list, container, false)

        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = MyOrdersRecyclerViewAdapter(OrderContent.orders, listener) //TODO: refresh objednavok zo servera, poslat serveru vybavenie objednavky po kliknuti
            }
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnOrdersFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
    interface OnOrdersFragmentInteractionListener {
        fun onOrdersFragmentInteraction(order: Order?, adapter: MyOrdersRecyclerViewAdapter) //TODO: vynat + premenovat metody ostatnych listenerov
    }
}
