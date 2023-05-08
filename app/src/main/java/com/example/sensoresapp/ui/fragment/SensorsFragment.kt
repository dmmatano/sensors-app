package com.example.sensoresapp.ui.fragment

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.sensoresapp.R
import com.example.sensoresapp.databinding.FragmentSensorsBinding

class SensorsFragment : Fragment() {
    private var _binding: FragmentSensorsBinding? = null
    private val binding get() = _binding!!
    private var selectedSensor: Int = 0
    private val sensorManager: SensorManager by lazy{
        requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }
    //lista de sensores disponiveis
    private val sensorList: List<Sensor> by lazy{
        sensorManager.getSensorList(Sensor.TYPE_ALL)
    }
    private val sensorListenner = object: SensorEventListener{
        override fun onSensorChanged(event: SensorEvent?) {
            var values = getString(R.string.msg_sensor_values) + "\n"

            event?.values?.indices?.forEach { i->
                values += "values[$i] = ${event.values[i]}\n"
            }

            binding.textViewSensorData.text = values
        }

        override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
            Toast.makeText(
                requireContext(),
                "A precisão dos sensores mudou: $p1",
                Toast.LENGTH_LONG
            ).show()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSensorsBinding.inflate(inflater, container, false)

        spinnerConfig()

        return binding.root
    }

    private fun spinnerConfig() {
        val sensorNameList = sensorList.map{ it.name }
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            sensorNameList
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerSensors.apply{
            this.adapter = adapter
            this.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                    selectedSensor = position
                    unregisterSensor()
                    registerSensor()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    //NOTHING TO DO
                }

            }
        }
    }

    private fun registerSensor() {
        sensorManager.registerListener(
            sensorListenner,
            sensorList[selectedSensor],
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    private fun unregisterSensor() {
        sensorManager.unregisterListener(sensorListenner)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterSensor()
        _binding = null
    }

}