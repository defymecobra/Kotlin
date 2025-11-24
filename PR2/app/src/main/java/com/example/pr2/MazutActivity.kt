package com.example.pr2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale
import kotlin.math.pow

class MazutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mazut)

        val inputQri: EditText = findViewById(R.id.input_qri)
        val inputAr: EditText = findViewById(R.id.input_ar)
        val inputAvyn: EditText = findViewById(R.id.input_avyn)
        val inputGvyn: EditText = findViewById(R.id.input_gvyn)
        val inputEtazu: EditText = findViewById(R.id.input_etazu)
        val inputB: EditText = findViewById(R.id.input_b)

        val calculateButton: Button = findViewById(R.id.calculate_button)
        val backButton: Button = findViewById(R.id.back_button)
        val resultText: TextView = findViewById(R.id.result_text)

        backButton.setOnClickListener { finish() }

        calculateButton.setOnClickListener {
            fun getVal(et: EditText): Double = et.text.toString().toDoubleOrNull() ?: 0.0

            val qri = getVal(inputQri)
            val ar = getVal(inputAr)
            val avyn = getVal(inputAvyn)
            val gvyn = getVal(inputGvyn)
            val etazu = getVal(inputEtazu)
            val b = getVal(inputB)

            // Formula 2.2: k_tv
            // k_tv = (10^6 / Qri) * avyn * (Ar / (100 - Gvyn)) * (1 - etazu)
            val kTv = (10.0.pow(6) / qri) * avyn * (ar / (100.0 - gvyn)) * (1.0 - etazu)

            // Formula 2.1: E_j
            // E_j = 10^-6 * k_tv * Qri * B
            val ej = 10.0.pow(-6) * kTv * qri * b

            val result = String.format(Locale.US,
                """
                Показник емісії (k_tv): %.2f г/ГДж
                Валовий викид (E_j): %.2f т
                """.trimIndent(),
                kTv, ej
            )
            resultText.text = result
        }
    }
}
