package com.example.fuelcalculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MazutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mazut)

        // Find Views
        val inputH: EditText = findViewById(R.id.input_h)
        val inputC: EditText = findViewById(R.id.input_c)
        val inputS: EditText = findViewById(R.id.input_s)
        val inputN: EditText = findViewById(R.id.input_n)
        val inputO: EditText = findViewById(R.id.input_o)
        val inputW: EditText = findViewById(R.id.input_w)
        val inputA: EditText = findViewById(R.id.input_a)
        val inputV: EditText = findViewById(R.id.input_v)
        val inputQ: EditText = findViewById(R.id.input_q)

        val calculateButton: Button = findViewById(R.id.calculate_button)
        val backButton: Button = findViewById(R.id.back_button)
        val resultText: TextView = findViewById(R.id.result_text)

        backButton.setOnClickListener {
            finish()
        }

        calculateButton.setOnClickListener {
            // Helper to get double from EditText safely
            fun getVal(et: EditText): Double {
                return et.text.toString().toDoubleOrNull() ?: 0.0
            }

            val hG = getVal(inputH)
            val cG = getVal(inputC)
            val sG = getVal(inputS)
            val nG = getVal(inputN)
            val oG = getVal(inputO)
            val wP = getVal(inputW)
            val aD = getVal(inputA)
            val vDaf = getVal(inputV)
            val qDaf = getVal(inputQ)

            // Calculations
            // 1. Calculate Working Ash (A^P)
            // A^P = A^d * (100 - W^P) / 100
            val aP = aD * (100 - wP) / 100

            // 2. Calculate Correction Factor (K)
            // K = (100 - W^P - A^P) / 100
            val k = (100 - wP - aP) / 100

            // 3. Calculate Working Mass Components
            val hP = hG * k
            val cP = cG * k
            val sP = sG * k
            val nP = nG * k
            val oP = oG * k
            
            // 4. Calculate Vanadium (V^P)
            val vP = vDaf * (100 - wP) / 100

            // 5. Calculate Lower Heat (Q^P)
            // Q^P = Q^daf * K - 0.025 * W^P
            val qP = qDaf * k - 0.025 * wP

            // Formatting
            val result = String.format(Locale.US,
                """
                Склад робочої маси мазуту:
                H^P = %.2f%%
                C^P = %.2f%%
                S^P = %.2f%%
                N^P = %.2f%%
                O^P = %.2f%%
                A^P = %.2f%%
                V^P = %.2f мг/кг
                
                Нижча теплота згоряння:
                Q^P = %.4f МДж/кг
                """.trimIndent(),
                hP, cP, sP, nP, oP, aP, vP, qP
            )

            resultText.text = result
        }
    }
}
