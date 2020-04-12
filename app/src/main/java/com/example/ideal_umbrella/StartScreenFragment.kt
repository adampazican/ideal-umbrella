package com.example.ideal_umbrella

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.app.Activity
import android.widget.Button
import androidx.navigation.Navigation

class StartScreenFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_start_screen, container, false);
        val startOrderButton = view?.findViewById(R.id.start_order_button) as Button;

        startOrderButton?.setOnClickListener {
            val bundle = Bundle();
            bundle.putInt("column-count",  2);
            Navigation.findNavController(activity as Activity, R.id.nav_host_fragment).navigate(R.id.chooseMealFragment, bundle);
        }

        return view;
    }
}