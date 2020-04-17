package com.example.ideal_umbrella.Order

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ideal_umbrella.ChooseMeal.Meal
import com.example.ideal_umbrella.R
import kotlinx.android.synthetic.main.fragment_orders.view.*

class MyOrdersRecyclerViewAdapter(
    private val mValues: List<Order>,
    private val mListener: OnOrdersFragmentInteractionListener?,
    private val context: Context
) : RecyclerView.Adapter<MyOrdersRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_orders, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mIdView.text = context.getString(R.string.order_table_number, item.tableNumber)

        holder.mContentView.text = item.meals.fold("") { acc: String, meal: Meal ->
            "$acc${meal.mealName}*${meal.numberOfOrders}\n"
        }
        holder.mPriceView.text = context.getString(R.string.order_price, item.sumTotal)

        if(item.finished)
            holder.mView.setBackgroundColor(context.getColor(R.color.colorDone))
        else
            holder.mView.setBackgroundColor(context.getColor(R.color.colorBackground))


        holder.mView.setOnClickListener {v ->
            mListener?.onOrdersFragmentInteraction(item, this)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.item_number
        val mContentView: TextView = mView.content
        val mPriceView: TextView = mView.price
    }
}
