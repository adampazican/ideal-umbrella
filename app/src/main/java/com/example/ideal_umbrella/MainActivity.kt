package com.example.ideal_umbrella

import android.app.Activity
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

/**
 * Hlavna aktivita aplikacie. Odpoveda na vsetky akcie aplikacie kedze je jedina aktivita a aplikacia je rozdelena na fragmenty medzi ktorymi sa naviguje pomocou navigatora
 */
class MainActivity : AppCompatActivity(), OnChooseMealFragmentInteractionListener, OnMealTypesFragmentInteractionListener, OnOrdersFragmentInteractionListener {
    /**
     * Deklaracia premennych (databaza, menu itemy)
     */
    companion object {
        lateinit var db: AppDatabase
        var orderSummary: MenuItem? = null
        var orderPlace: MenuItem? = null
        var orderReset: MenuItem? = null
        var orderRefresh: MenuItem? = null
    }

    /**
     * Akcia vyvolana pri vytvoreni aktivity, inicializuje databazu, vytvara action bar
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = Room.databaseBuilder(
            this,
            AppDatabase::class.java, AppDatabase.DB_NAME
        ).build()

        setSupportActionBar(findViewById(R.id.toolbar))
    }

    /**
     * Akcia vyvolana pri zniceni aktivity, zatvara databazu
     */
    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }

    /**
     * Akcia vyvolana stlacenim tlacidla plus pri konkretom jedle v ponuke, co zvacsi pocet objednanych kusov
     * @param button predstavuje tlacidlo plus
     * @param item predstavuje stlacene jedlo
     * @param adapter predstavuje adapter listu jedal
     * @param numberView predstavuje pocet objednanych jedal, ktory je zobrazeny pru kazdom jedle
     */
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

    /**
     * Akcia vyvolana stlacenim tlacidla minus pri konkretom jedle v ponuke, co zmensi pocet objednanych kusov
     * @param button predstavuje tlacidlo minus
     * @param item predstavuje stlacene jedlo
     * @param adapter predstavuje adapter listu jedal
     * @param numberView predstavuje pocet objednanych jedal, ktory je zobrazeny pru kazdom jedle
     */
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

    /**
     * Akcia vyvolana pri kliknuti na typ jedla pri objednavani, otvori list jedal tohoto typu
     * @param item predstavuje stlaceny typ jedla
     */
    override fun onMealTypesFragmentInteraction(item: MealType?) {
        if (item != null) {
            val bundle = Bundle()
            bundle.putInt("meal-type",  item.value)
            Navigation.findNavController(this as Activity, R.id.nav_host_fragment).navigate(R.id.chooseMealFragment, bundle)
        }
    }

    /**
     * Akcia vyvolana pri vytvoreni menu, inicializuje menu tlacidla
     * @param menu predstavuje menu aplikacie
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.order_reset, menu)
        menuInflater.inflate(R.menu.order_summary_button, menu)
        menuInflater.inflate(R.menu.order_place, menu)
        menuInflater.inflate(R.menu.order_refresh, menu)

        orderReset = instantiateMenuItem(orderReset, menu, 0)
        orderSummary = instantiateMenuItem(orderSummary, menu, 1)
        orderPlace = instantiateMenuItem(orderPlace, menu, 2)
        orderRefresh = instantiateMenuItem(orderRefresh, menu, 3)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * Metoda vytvarajuca menuItemy. Zabezpecuje navrat itemu do spravneho stavu viditelnosti pri zmene konfiguracie zariadenie
     * @param menuItem predstavuje vytvarany menu item
     * @param menu predstavuje vytvarane menu
     * @param index predstavuje index [menuItem] v [menu]
     * @return vracia novy menuItem
     */
    private fun instantiateMenuItem(menuItem: MenuItem?, menu: Menu?, index: Int): MenuItem? {
        val isVisible = menuItem?.isVisible

        val newMenuItem = menu?.getItem(index)
        if (isVisible != null) {
            newMenuItem?.isVisible = isVisible
        }

        return newMenuItem
    }

    /**
     * Akcia vyvolana stlacenim tlacidla menu
     * @param item predstavuje stlacene menu tlacidlo
     */
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
                        Toast.makeText(applicationContext, applicationContext.getString(R.string.place_order_error), Toast.LENGTH_SHORT).show()
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

    /**
     * Akcia vyvolana stlacenim objednavky v liste objednavok
     * @param order predstavuje stlacenu objednavku
     * @param adapter adapter listu objednavok
     */
    override fun onOrdersFragmentInteraction(order: Order?, adapter: MyOrdersRecyclerViewAdapter) {
        if(order != null) {
            order.finished = !order.finished
            adapter.notifyDataSetChanged()
            HttpHandler.makeOrderFinished(order.id, order.finished) {success ->
                if(!success) {
                    this.runOnUiThread {
                        Toast.makeText(applicationContext,applicationContext.getString(R.string.finish_order_error), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
