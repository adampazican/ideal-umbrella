package com.example.ideal_umbrella

import android.app.Activity
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.room.Room
import com.example.ideal_umbrella.ChooseMeal.Meal
import com.example.ideal_umbrella.ChooseMeal.MealContent
import com.example.ideal_umbrella.ChooseMeal.MyChooseMealRecyclerViewAdapter
import com.example.ideal_umbrella.ChooseMeal.OnChooseMealFragmentInteractionListener
import com.example.ideal_umbrella.Database.AppDatabase
import com.example.ideal_umbrella.MealTypeMenu.MealType
import com.example.ideal_umbrella.MealTypeMenu.OnMealTypesFragmentInteractionListener
import com.example.ideal_umbrella.Order.Order
import com.example.ideal_umbrella.Order.OrderContent
import com.example.ideal_umbrella.Order.OrderFragmentDirections

class MainActivity : AppCompatActivity(), OnChooseMealFragmentInteractionListener, OnMealTypesFragmentInteractionListener {
    companion object {
        lateinit var db: AppDatabase
        var orderSummary: MenuItem? = null
        var orderPlace: MenuItem? = null
        var orderReset: MenuItem? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = Room.databaseBuilder(
            this,
            AppDatabase::class.java, AppDatabase.DB_NAME
        ).build()

        setSupportActionBar(findViewById(R.id.toolbar))
    }

    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }

    override fun onListFragmentInteractionPlus(
        button: Button,
        item: Meal?,
        adapter: MyChooseMealRecyclerViewAdapter,
        numberView: TextView
    ) {
        if(item != null) {
            item.numberOfOrders++
            numberView.setTextColor(Color.parseColor("#00ff00"))
            adapter.notifyDataSetChanged()
        }
    }

    override fun onListFragmentInteractionMinus(
        button: Button,
        item: Meal?,
        adapter: MyChooseMealRecyclerViewAdapter,
        numberView: TextView
    ) {
        if(item != null) {
            if(item.numberOfOrders > 0) {
                item.numberOfOrders--
                adapter.notifyDataSetChanged()

                if (item.numberOfOrders == 0)
                    numberView.setTextColor(Color.parseColor("#000000"))
            }
        }
    }

    override fun onListFragmentInteraction(item: MealType?) {
        if (item != null) {
            val bundle = Bundle()
            bundle.putInt("meal-type",  item.value)
            Navigation.findNavController(this as Activity, R.id.nav_host_fragment).navigate(R.id.chooseMealFragment, bundle)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.order_reset, menu)
        menuInflater.inflate(R.menu.order_summary_button, menu)
        menuInflater.inflate(R.menu.order_place, menu)

        orderSummary = menu?.getItem(0)
        orderPlace = menu?.getItem(1)
        orderReset = menu?.getItem(2)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) { //TODO: daily menu? images(are these important?)?
        R.id.action_order_summary -> {
            Navigation.findNavController(this as Activity, R.id.nav_host_fragment).navigate(R.id.orderFragment)
            true
        }
        R.id.action_order_place -> {
            OrderContent.orders.add(Order(MealContent.allMeals.filter { it.numberOfOrders > 0 }, MealContent.tableNumber, MealContent.allMeals.fold(0){acc: Int, meal: Meal -> acc + meal.price * meal.numberOfOrders }))
            MealContent.allMeals.clear()
            1-1 //TODO: send order to the server
            findNavController(R.id.nav_host_fragment).navigate(OrderFragmentDirections.actionOrderFragmentToOrdersFragment())
            true
        }
        R.id.action_order_reset -> {
            MealContent.allMeals.map {
                it.numberOfOrders = 0
            }
            orderSummary?.isVisible = false
            orderReset?.isVisible= false
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}
