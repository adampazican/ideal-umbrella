package com.example.ideal_umbrella

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
import com.example.ideal_umbrella.Order.*

class MainActivity : AppCompatActivity(), OnChooseMealFragmentInteractionListener, OnMealTypesFragmentInteractionListener, OnOrdersFragmentInteractionListener {
    companion object {
        lateinit var db: AppDatabase
        var orderSummary: MenuItem? = null
        var orderPlace: MenuItem? = null
        var orderReset: MenuItem? = null
        var orderRefresh: MenuItem? = null
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
            numberView.setTextColor(applicationContext.getColor(R.color.colorDone))
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
                    numberView.setTextColor(applicationContext.getColor(R.color.colorBackground))
            }
        }
    }

    override fun onMealTypesFragmentInteraction(item: MealType?) {
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
        menuInflater.inflate(R.menu.order_refresh, menu)

        orderReset = menu?.getItem(0)
        orderSummary = menu?.getItem(1)
        orderPlace = menu?.getItem(2)
        orderRefresh = menu?.getItem(3)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_order_summary -> {
            Navigation.findNavController(this as Activity, R.id.nav_host_fragment).navigate(R.id.orderFragment)
            true
        }
        R.id.action_order_place -> {
            val order = Order(MealContent.allMeals.filter { it.numberOfOrders > 0 } as ArrayList<Meal>, MealContent.tableNumber, MealContent.allMeals.fold(0){ acc: Int, meal: Meal -> acc + meal.price * meal.numberOfOrders }, -1)
            MealContent.allMeals.clear()
            OrderContent.orders.add(order)

            HttpHandler.storeOrder(order) {success: Boolean, id: Int ->
                if(!success) {
                    this.runOnUiThread {
                        Toast.makeText(applicationContext, "Couldn't send order to the server", Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    order.id = id
                }
            }

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
        R.id.action_order_refresh -> {
            HttpHandler.getAllOrders { ordersArray, _ ->
                OrderContent.orders.clear()
                if (ordersArray != null) {
                    OrderContent.orders.addAll(ordersArray)
                    this.runOnUiThread {
                        OrderContent.view?.adapter?.notifyDataSetChanged()
                    }
                }
            }
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onOrdersFragmentInteraction(order: Order?, adapter: MyOrdersRecyclerViewAdapter) {
        if(order != null) {
            order.finished = !order.finished
            adapter.notifyDataSetChanged()
            HttpHandler.makeOrderFinished(order.id, order.finished) {success ->
                if(!success) {
                    this.runOnUiThread {
                        Toast.makeText(applicationContext,"Couldn't connect to the server", Toast.LENGTH_SHORT).show() //TODO: make all string constants into resources
                    }
                }
            }
        }
    }
}
