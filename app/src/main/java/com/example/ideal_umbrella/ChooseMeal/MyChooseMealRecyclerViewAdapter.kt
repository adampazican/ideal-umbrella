package com.example.ideal_umbrella.ChooseMeal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ideal_umbrella.R
import kotlinx.android.synthetic.main.fragment_choose_meal.view.*

class MyChooseMealRecyclerViewAdapter(
    private val mValues: List<Meal>,
    private val mListener: OnChooseMealFragmentInteractionListener?,
    private val context: Context
) : RecyclerView.Adapter<MyChooseMealRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_choose_meal, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mIdView.text = position.plus(1).toString()
        holder.mContentView.text = item.mealName
        holder.mOrderView.text = item.numberOfOrders.toString()

        if(item.numberOfOrders > 0)
            holder.mOrderView.setTextColor(context.getColor(R.color.colorDone))
        else
            holder.mOrderView.setTextColor(context.getColor(R.color.colorBlack))

        holder.mPlusButton.setOnClickListener {v ->
            mListener?.onListFragmentInteractionPlus(v as Button, item, this, holder.mOrderView)
        }

        holder.mMinusButton.setOnClickListener {v ->
            mListener?.onListFragmentInteractionMinus(v as Button, item, this, holder.mOrderView)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.item_number
        val mContentView: TextView = mView.content
        val mOrderView: TextView = mView.number_of_orders
        val mPlusButton: Button = mView.plus_order_button
        val mMinusButton: Button = mView.minus_order_button
    }
}
