package com.example.ideal_umbrella.ChooseMeal

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.ideal_umbrella.*
import com.example.ideal_umbrella.Database.AppDatabase

class ChooseMealFragment : Fragment() {

    private var mealType: Int = 0 //TODO: null by default all????? or no
    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            mealType = it.getInt("meal-type")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_choose_meal_list, container, false)

        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = MyChooseMealRecyclerViewAdapter(MealContent.meals, listener)

                if (MealContent.meals.isEmpty()) {
                    val db = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java, "waiter"
                    ).build()

                    Thread {
                        db.mealDao().getAllByMealType(mealType).forEach {
                            MealContent.addItem(Meal(it.id, it.mealName, it.mealType, it.price))
                        }

                        activity?.runOnUiThread {
                            (adapter as MyChooseMealRecyclerViewAdapter).notifyDataSetChanged()
                        }
                    }.start()
                }
            }
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
