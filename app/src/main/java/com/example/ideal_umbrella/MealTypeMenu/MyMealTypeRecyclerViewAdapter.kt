package com.example.ideal_umbrella.MealTypeMenu

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.ideal_umbrella.R

import kotlinx.android.synthetic.main.fragment_meal_type.view.*
import java.util.*

/**
 * Adapter MealType fragmentu
 */
class MyMealTypeRecyclerViewAdapter(
    private val mValues: List<MealType>,
    private val mListener: OnMealTypesFragmentInteractionListener?
) : RecyclerView.Adapter<MyMealTypeRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as MealType
            mListener?.onMealTypesFragmentInteraction(item)
        }
    }

    /**
     * Metoda, ktora sa zavola pri vytvoreni instancie adaptera
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_meal_type, parent, false)
        return ViewHolder(view)
    }

    /**
     * Metoda, ktora inicializuje view holder. Pouziva experimentalnu std kniznicu na kapitalizaciu nazvu jedla
     */
    @ExperimentalStdlibApi
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mContentView.text = item.name.toLowerCase(Locale.ROOT).capitalize(Locale.getDefault()).replace("_", " ")

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    /**
     * @return vracia pocet objednavok v adapteri
     */
    override fun getItemCount(): Int = mValues.size

    /**
     * Predstavuje typ dat v adapteri
     */
    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mContentView: TextView = mView.content
    }
}
