package com.example.ideal_umbrella

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ChooseMealFragment : Fragment() {

    private var columnCount = 1
    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt("column-count")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_choose_meal_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyChooseMealRecyclerViewAdapter(MealContent.meals, listener)
                MealContent.addItem(Meal(0, "dd", MealType.MEAL_SOUP, 10));
                if (MealContent.meals.isEmpty()) {
                    Thread {
                        val url = URL("http://9a7b6458.ngrok.io:4000/daily-menu");
                        val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
                        conn.connect();
                        val inputStream: InputStream = conn.inputStream
                        val reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
                        Log.d("ChooseMealFragment", reader.toString());
                    }.start();
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


    interface OnListFragmentInteractionListener {//TODO: new file
        fun onListFragmentInteraction(item: Meal?)
    }
}
