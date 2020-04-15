package com.example.ideal_umbrella

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.Navigation
import com.example.ideal_umbrella.ChooseMeal.MealContent


class TablesDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val view = View.inflate(context, R.layout.dialog_table_number, null)
        val tableNumberInput = view.findViewById(R.id.table_number_input) as EditText
        tableNumberInput.transformationMethod = null
        tableNumberInput.requestFocus()

        builder.setView(view)
            .setTitle("Table number")
            .setPositiveButton("OK") { _, _ ->
                if (tableNumberInput.text == null || tableNumberInput.text.isEmpty()) {
                    dialog?.dismiss()
                    Toast.makeText(context, "Invalid table number", Toast.LENGTH_SHORT).show()
                }
                else {
                    MealContent.tableNumber = Integer.parseInt(tableNumberInput.text.toString())
                    Navigation.findNavController(activity as Activity, R.id.nav_host_fragment).navigate(R.id.mealTypeFragment)
                }
            }
            .setNegativeButton("Cancel") { _, _ ->
                dialog?.dismiss()
            }

        return builder.create()
    }
}