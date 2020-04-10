package com.example.ideal_umbrella

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ideal_umbrella.dummy.DummyContent
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity(), ChooseMealFragment.OnListFragmentInteractionListener {
    override fun onListFragmentInteraction(item: DummyContent.DummyItem?) {
        val bytes = URL("localhost:3999/daily-menu").readBytes();
        print(bytes);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
