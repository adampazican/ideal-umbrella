package com.example.ideal_umbrella.Order

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.ideal_umbrella.ChooseMeal.Meal
import com.example.ideal_umbrella.R

import com.example.ideal_umbrella.Order.OrdersFragment.OnListFragmentInteractionListener

import kotlinx.android.synthetic.main.fragment_orders.view.*

class MyOrdersRecyclerViewAdapter(
    private val mValues: List<Order>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MyOrdersRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Order
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_orders, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mIdView.text = "Table \n${item.tableNumber}"
        holder.mContentView.text = item.meals.fold("") { acc: String, meal: Meal ->
            "$acc${meal.mealName}*${meal.numberOfOrders}\n"
        }
        holder.mPriceView.text = "Price \n${item.sumTotal} €"

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.item_number
        val mContentView: TextView = mView.content
        val mPriceView: TextView = mView.price
    }
}
