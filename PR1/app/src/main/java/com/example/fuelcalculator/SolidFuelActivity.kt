package com.example.fuelcalculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class SolidFuelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solid_fuel)

        // Find Views
        val inputH: EditText = findViewById(R.id.input_h)
        val inputC: EditText = findViewById(R.id.input_c)
        val inputS: EditText = findViewById(R.id.input_s)
        val inputN: EditText = findViewById(R.id.input_n)
        val inputO: EditText = findViewById(R.id.input_o)
        val inputW: EditText = findViewById(R.id.input_w)
        val inputA: EditText = findViewById(R.id.input_a)

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

            val hP = getVal(inputH)
            val cP = getVal(inputC)
            val sP = getVal(inputS)
            val nP = getVal(inputN)
            val oP = getVal(inputO)
            val wP = getVal(inputW)
            val aP = getVal(inputA)

            // Calculations
            // 1. Calculate Coefficients
            // Dry Mass Coeff (K_PC) = 100 / (100 - W^P)
            val kPC = 100.0 / (100.0 - wP)
            
            // Combustible Mass Coeff (K_PG) = 100 / (100 - W^P - A^P)
            val kPG = 100.0 / (100.0 - wP - aP)

            // 2. Calculate Dry Mass Composition
            val hC = hP * kPC
            val cC = cP * kPC
            val sC = sP * kPC
            val nC = nP * kPC
            val oC = oP * kPC
            val aC = aP * kPC

            // 3. Calculate Combustible Mass Composition
            val hG = hP * kPG
            val cG = cP * kPG
            val sG = sP * kPG
            val nG = nP * kPG
            val oG = oP * kPG

            // 4. Calculate Lower Heat of Working Mass (Q^P_H)
            // Formula 1.2: Q^P_H = (339*C^P + 1030*H^P - 108.8*(O^P - S^P) - 25*W^P) / 1000  (to get MJ/kg)
            val qPH = (339 * cP + 1030 * hP - 108.8 * (oP - sP) - 25 * wP) / 1000.0

            // 5. Calculate Lower Heat of Dry Mass (Q^C_H)
            // Q^C_H = (Q^P_H + 0.025 * W^P) * 100 / (100 - W^P)
            val qCH = (qPH + 0.025 * wP) * 100.0 / (100.0 - wP)

            // 6. Calculate Lower Heat of Combustible Mass (Q^G_H)
            // Q^G_H = (Q^P_H + 0.025 * W^P) * 100 / (100 - W^P - A^P)
            val qGH = (qPH + 0.025 * wP) * 100.0 / (100.0 - wP - aP)

            // Formatting
            val result = String.format(Locale.US,
                """
                Коефіцієнт переходу до сухої маси: %.2f
                Коефіцієнт переходу до горючої маси: %.2f

                Склад сухої маси:
                H^C = %.2f%%, C^C = %.2f%%, S^C = %.2f%%
                N^C = %.2f%%, O^C = %.2f%%, A^C = %.2f%%

                Склад горючої маси:
                H^G = %.2f%%, C^G = %.2f%%, S^G = %.2f%%
                N^G = %.2f%%, O^G = %.2f%%

                Нижча теплота згоряння:
                Робоча (Q^P_H) = %.4f МДж/кг
                Суха (Q^C_H) = %.4f МДж/кг
                Горюча (Q^G_H) = %.4f МДж/кг
                """.trimIndent(),
                kPC, kPG,
                hC, cC, sC, nC, oC, aC,
                hG, cG, sG, nG, oG,
                qPH, qCH, qGH
            )

            resultText.text = result
        }
    }
}
