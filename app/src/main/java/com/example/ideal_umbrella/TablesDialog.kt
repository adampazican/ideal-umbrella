package com.example.ideal_umbrella

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.Navigation
import com.example.ideal_umbrella.ChooseMeal.MealContent

/**
 * [TablesDialog] je trieda definujuca dialog na zadanie cisla stola pri novej objednavke
 */
class TablesDialog : DialogFragment() {
    /**
     * Metoda zavolana pri vytvoreni dialogu. Definuje titulok a spravanie tlacidiel dialogu
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val view = View.inflate(context, R.layout.dialog_table_number, null)
        val tableNumberInput = view.findViewById(R.id.table_number_input) as EditText
        tableNumberInput.transformationMethod = null
        tableNumberInput.requestFocus()

        builder.setView(view)
            .setTitle(context?.getString(R.string.table_dialog_title))
            .setPositiveButton(context?.getString(R.string.table_dialog_positive_button)) { _, _ ->
                if (tableNumberInput.text == null || tableNumberInput.text.isEmpty()) {
                    dialog?.dismiss()
                    Toast.makeText(context, context?.getString(R.string.table_dialog_invalid_message), Toast.LENGTH_SHORT).show()
                }
                else {
                    MealContent.tableNumber = Integer.parseInt(tableNumberInput.text.toString())
                    Navigation.findNavController(activity as Activity, R.id.nav_host_fragment).navigate(R.id.mealTypeFragment)
                }
            }
            .setNegativeButton(context?.getString(R.string.table_dialog_negative_button)) { _, _ ->
                dialog?.dismiss()
            }

        return builder.create()
    }
}