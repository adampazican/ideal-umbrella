package com.example.ideal_umbrella

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ideal_umbrella.dummy.DummyContent

class MainActivity : AppCompatActivity(), ChooseMealFragment.OnListFragmentInteractionListener {
    override fun onListFragmentInteraction(item: DummyContent.DummyItem?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
