package com.example.ideal_umbrella

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

/**
 * Trieda predstavuje prihlasovaciu obrazovku. Pouzivatel musi zadat spravny email a heslo aby mohol pokracovat do aplikacie.
 */
class LoginFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    /**
     * Metoda, ktora sa zavola pri vytvoreni pohladu. Nastavuje akciu ktora sa vykona, ked pouzivatel stlaci tlacidlo na prihlasenie
     */
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

    /**
     * Akcia vyvolana tlacidlom na prihlasenie. Posle serveru email a heslo a podla odpovede bud pokracujeme do dalsieho pohladu alebo ukazeme toast s chybovou hlaskou
     */
    private fun attemptLogin(view: View) {
        val email = view.findViewById<EditText>(R.id.login_email_input)?.text
        val password = view.findViewById<EditText>(R.id.login_password_input)?.text
        val url = view.findViewById<EditText>(R.id.login_url)?.text.toString()

        HttpHandler.initialize(url)

        HttpHandler.verifyUser(email.toString(), password.toString()){success, error ->
            if(success)
                Navigation.findNavController(activity as Activity, R.id.nav_host_fragment).navigate(R.id.startScreenFragment)
            else {
                if(error == null) {
                    activity?.runOnUiThread {
                        Toast.makeText(context, context?.getString(R.string.login_invalid_credentials), Toast.LENGTH_SHORT).show()
                    }
                }
                else {
                    activity?.runOnUiThread {
                        Toast.makeText(context, context?.getString(R.string.login_network_error), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
