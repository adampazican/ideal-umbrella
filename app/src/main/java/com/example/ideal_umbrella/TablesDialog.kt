package com.example.ideal_umbrella

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.navigation.Navigation
import com.example.ideal_umbrella.ChooseMeal.MealContent

class TablesDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.dialog_table_number, null)
            val tableNumberInput = view.findViewById(R.id.table_number_input) as EditText
            tableNumberInput.transformationMethod = null

            builder.setView(view)
                .setTitle("Table number")
                .setPositiveButton("OK") { _, _ ->
                    MealContent.tableNumber = Integer.parseInt(tableNumberInput.text.toString())
                    Navigation.findNavController(activity as Activity, R.id.nav_host_fragment).navigate(R.id.mealTypeFragment);
                }


            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}