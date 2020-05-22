package com.example.ideal_umbrella.ChooseMeal

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ideal_umbrella.*

/**
 * Trieda predstavujuca zoznam jedal urciteho typu
 */
class ChooseMealFragment : Fragment() {

    private var mealType: Int = 0
    private var listener: OnChooseMealFragmentInteractionListener? = null

    /**
     * Metoda ktora sa zavola pri vytvoreni fragmentu. Inicializuje mealType
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            mealType = it.getInt("meal-type")
        }
    }

    /**
     * Metoda, ktora sa zavola pri vytvoreni pohladu. Inicializuje layout, adapter. Ak je zozname jedal v MealContent objekte prazdny,
     * tak sa vytvori nove vlakno ktore dotiahne vsetky jedla z databazy a potom upozorni adapter na zmenu dat.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_choose_meal_list, container, false)

        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)

                if(MealContent.allMeals.isEmpty()) {
                    Thread {
                        val db = MainActivity.db
                        db.mealDao().getAll().forEach {
                            MealContent.allMeals.add(Meal(it.id, it.mealName, it.mealType, it.price))
                        }

                        MealContent.showingMeals.clear()
                        MealContent.showingMeals.addAll(MealContent.getAllMealsByType(mealType))

                        activity?.runOnUiThread {
                            (adapter as MyChooseMealRecyclerViewAdapter).notifyDataSetChanged()
                        }
                    }.start()
                }
                else {
                    MealContent.showingMeals.clear()
                    MealContent.showingMeals.addAll(MealContent.getAllMealsByType(mealType))
                }

                adapter = MyChooseMealRecyclerViewAdapter(MealContent.showingMeals, listener, context)
            }
        }
        return view
    }

    /**
     * Metoda, ktora sa zavola pri pripojeni fragmentu do activity
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnChooseMealFragmentInteractionListener) {
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
