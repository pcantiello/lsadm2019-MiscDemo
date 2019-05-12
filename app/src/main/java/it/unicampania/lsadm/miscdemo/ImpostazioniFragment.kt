package it.unicampania.lsadm.miscdemo


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_impostazioni.*


/**
 * Fragment per le impostazioni
 *
 */
class ImpostazioniFragment : Fragment() {

    // Chiavi nelle preferenze
    private val PREF_NAME = "MiscDemo"      // Nome del file
    private val PREF_USERNAME = "Username"
    private val PREF_AUTOLOGIN = "AutoLogin"

    private lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_impostazioni, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = activity!!.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        leggiImpostazioni()

        // Pulsanti
        btnAnnulla.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }

        btnConferma.setOnClickListener {
            if (campiValidi()) {
                salvaImpostazioni()
                Navigation.findNavController(it).navigateUp()
            }
        }
    }

    /**
     * Controlla la validità delle impostazioni
     * ed eventualmente visualizza messaggi d'errore
     */
    private fun campiValidi(): Boolean
    {
        // Controllo l'unico campo
        if (editUsername.text.toString().length == 0) {
            Toast.makeText(activity, R.string.valUsernameObbligatorio, Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    /**
     * Memorizza le impostazioni
     */
    private fun salvaImpostazioni()
    {
        val editor = sharedPref.edit()

        val username = editUsername.text.toString()
        editor.putString(PREF_USERNAME, username)

        val autoLogin = chkAutoLogin.isChecked
        editor.putBoolean(PREF_AUTOLOGIN, autoLogin)

        editor.apply()    // Salva le modifiche
    }

    /**
     * Legge le impostazioni e le visualizza
     * nella form
     */
    private fun leggiImpostazioni()
    {
        val username = sharedPref.getString(PREF_USERNAME, "")
        editUsername.setText(username)

        val autoLogin = sharedPref.getBoolean(PREF_AUTOLOGIN, false)
        chkAutoLogin.isChecked = autoLogin
    }
    
}
