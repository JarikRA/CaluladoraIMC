package com.jackanota.calculadoraimc

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var etHeight: EditText
    private lateinit var etWeight: EditText
    private lateinit var tvIMC: TextView
    private val minHeight = 0
    private val minWeight = 0
    private val maxHeight = 2.72
    private val maxWeight = 635

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etHeight = findViewById(R.id.et_height)
        etWeight = findViewById(R.id.et_weight)
        tvIMC = findViewById(R.id.tv_IMC)
    }

    @SuppressLint("SetTextI18n")
    fun clickButtonIMC(view: View) {
        val heightString = etHeight.text.toString().trim()
        val weightString = etWeight.text.toString().trim()

        if (heightString.isEmpty() || weightString.isEmpty()) {
            Toast.makeText(this, "Primero ingrese ambos datos", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val height = heightString.toDouble()
            val weight = weightString.toDouble()

            if (height <= minHeight || height > maxHeight) {
                Toast.makeText(this, "Ingrese una altura razonable", Toast.LENGTH_SHORT).show()
                tvIMC.text = ""
                return
            }

            if (weight <= minWeight || weight > maxWeight){
                Toast.makeText(this, "Ingrese un peso razonable", Toast.LENGTH_SHORT).show()
                tvIMC.text = ""
                return
            }

            val imc = calculateIMC(height,weight)

            val imcInterpretation = when (imc) {
                in 0.0..18.5 -> "bajo"
                in 18.5..24.9 -> "normal"
                in 25.0..29.9 -> "sobrepeso"
                in 30.0..34.9 -> "obesidad moderada"
                else -> "obesidad severa"
            }

            tvIMC.text = "Su IMC es de: ${imc.roundToInt()} \nSu peso es $imcInterpretation"

        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Error: Entrada no válida (debe ser un número)", Toast.LENGTH_SHORT).show()
        }
    }

    private fun calculateIMC( height: Double, weight: Double): Double{
        return weight / (height * height)
    }
}