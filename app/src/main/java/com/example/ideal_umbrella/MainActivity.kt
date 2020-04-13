package com.example.ideal_umbrella

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.room.Room
import com.example.ideal_umbrella.ChooseMeal.Meal
import com.example.ideal_umbrella.ChooseMeal.MyChooseMealRecyclerViewAdapter
import com.example.ideal_umbrella.ChooseMeal.OnListFragmentInteractionListener
import com.example.ideal_umbrella.Database.AppDatabase

class MainActivity : AppCompatActivity(), OnListFragmentInteractionListener {
//    @JvmStatic
    lateinit var db: AppDatabase //TODO: check later

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) //TODO: make new fragment with list of types of meal (dynamically added from enum), table number and big order button
//        db = Room.databaseBuilder(
//            this,
//            AppDatabase::class.java, "waiter"
//        ).build()
    }

    override fun onListFragmentInteractionPlus(
        button: Button,
        item: Meal?,
        adapter: MyChooseMealRecyclerViewAdapter,
        numberView: TextView
    ) {
        if(item != null) {
            item.numberOfOrders++;
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
                item.numberOfOrders--;
                adapter.notifyDataSetChanged()

                if (item.numberOfOrders == 0)
                    numberView.setTextColor(Color.parseColor("#000000"))
            }
        }
    }
}
