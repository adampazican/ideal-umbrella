package com.example.ideal_umbrella

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.Navigation
import androidx.room.Room
import com.example.ideal_umbrella.MealTypeMenu.MealType
import com.example.ideal_umbrella.Database.AppDatabase
import com.example.ideal_umbrella.Database.Meal

class StartScreenFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_start_screen, container, false);
        val startOrderButton = view?.findViewById(R.id.start_order_button) as Button;
        val updateMealsButton = view?.findViewById(R.id.update_meals_button) as Button

        startOrderButton.setOnClickListener {
            //TODO: show popup which asks table number

            val builder: AlertDialog.Builder? = activity?.let {
                AlertDialog.Builder(it)
            }

// 2. Chain together various setter methods to set the dialog characteristics
            builder?.setMessage("dialog")
                ?.setTitle("jes")

// 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
            val dialog: AlertDialog? = builder?.create()



//            Navigation.findNavController(activity as Activity, R.id.nav_host_fragment).navigate(R.id.mealTypeFragment);
        }

        val db = MainActivity.db

        updateMealsButton.setOnClickListener {
            HttpHandler.getAllMeals { mealArray: ArrayList<com.example.ideal_umbrella.ChooseMeal.Meal>?, success: Boolean ->
                if (success && mealArray != null) {
                    db.mealDao().deleteAll()
                    db.mealDao().insertAll(mealArray.map {
                        Meal(it.id, it.mealName, it.mealType, it.price)
                    })

                    activity?.runOnUiThread {
                        Toast.makeText(activity, "Db updated", Toast.LENGTH_SHORT).show()
                    }
                }
                else {
                    activity?.runOnUiThread {
                        Toast.makeText(activity, "Couldn't connect to the server! Check your internet connection", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        return view;
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.dialog_table_number, null))
                // Add action buttons
                .setPositiveButton("signin",
                    DialogInterface.OnClickListener { dialog, id ->
                        // sign in the user ...
                    })
                .setNegativeButton("cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog()?.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}