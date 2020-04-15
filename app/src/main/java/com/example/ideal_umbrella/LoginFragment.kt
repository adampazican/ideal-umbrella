package com.example.ideal_umbrella

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation


class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.login_fragment, container, false)
        view.findViewById<Button>(R.id.button).setOnClickListener {
            attemptLogin(view)
        }
        return view
    }

    private fun attemptLogin(view: View) {
        val email = view.findViewById<EditText>(R.id.login_email_input)?.text
        val password = view.findViewById<EditText>(R.id.login_password_input)?.text

        //todo: send to server
        Navigation.findNavController(activity as Activity, R.id.nav_host_fragment).navigate(R.id.startScreenFragment)
    }
}
