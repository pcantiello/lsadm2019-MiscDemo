package it.unicampania.lsadm.miscdemo


import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.getSystemService
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_sensori.*


/**
 * Fragment per mostrare l'utilizzo di alcuni sensori
 *
 */
class SensoriFragment : Fragment(), SensorEventListener {

    // Manager per accedere ai sensori del dispositivo
    private lateinit var sensorManager: SensorManager

    // Sensori e relativi listener
    private var tempSensor: Sensor? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sensori, container, false)
    }

    /**
     * Invocata dopo la creazione della view
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ottiene il sensor managar
        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        // Verifico che sia presente il sensore di temperatura
        tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        textTemperatura.text = if (tempSensor != null) getString(R.string.presente) else getString(R.string.non_presente)
    }

    /**
     * Il fragment diventa attivo
     */
    override fun onResume() {
        super.onResume()

        // Abilito il monitoraggio del sensore di temperatura, se presente
        tempSensor?.also { temp ->
            sensorManager.registerListener(this, temp, SensorManager.SENSOR_DELAY_NORMAL)
        }

    }

    /**
     * Il fragment diventa inattivo
     */
    override fun onPause() {
        super.onPause()

        // Disattivo il monitoraggio del sensore di temperatura
        sensorManager.unregisterListener(this)
    }


    // Implementazione interfaccia SensorEventListener

    /**
     * E' cambiata la precisione di un sensore
     */
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Non ci interessa al momento
    }

    /**
     * E' cambiato il valore di un sensore
     */
    override fun onSensorChanged(event: SensorEvent?) {

        when (event?.sensor?.type) {
            Sensor.TYPE_AMBIENT_TEMPERATURE -> textTemperatura.text = getString(R.string.temp_format).format(event.values[0])
        }

    }

}
