package com.example.ideal_umbrella.Order

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.ideal_umbrella.ChooseMeal.Meal
import com.example.ideal_umbrella.R

import kotlinx.android.synthetic.main.fragment_order.view.*

class MyOrderRecyclerViewAdapter(
    private val mValues: List<Meal>
) : RecyclerView.Adapter<MyOrderRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_order, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mIdView.text = item.mealName
        holder.mContentView.text = item.numberOfOrders.toString()
        holder.mPriceView.text = (item.price?.times(item.numberOfOrders)).toString()
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.item_number
        val mContentView: TextView = mView.content
        val mPriceView: TextView = mView.price
    }
}