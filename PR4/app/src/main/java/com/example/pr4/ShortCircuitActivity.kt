package com.example.pr4

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale
import kotlin.math.sqrt

class ShortCircuitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_short_circuit)

        val inputSk: EditText = findViewById(R.id.input_sk)
        val inputUcn: EditText = findViewById(R.id.input_ucn)
        val inputSnom: EditText = findViewById(R.id.input_snom)
        val inputUk: EditText = findViewById(R.id.input_uk)
        val calculateButton: Button = findViewById(R.id.calculate_button)
        val backButton: Button = findViewById(R.id.back_button)
        val resultText: TextView = findViewById(R.id.result_text)

        backButton.setOnClickListener { finish() }

        calculateButton.setOnClickListener {
            val sk = inputSk.text.toString().toDoubleOrNull() ?: 200.0 // МВА
            val ucn = inputUcn.text.toString().toDoubleOrNull() ?: 10.5 // кВ
            val snom = inputSnom.text.toString().toDoubleOrNull() ?: 6.3 // МВА
            val ukPercent = inputUk.text.toString().toDoubleOrNull() ?: 7.5 // %

            // Реактанс системы: Xc = Ucn^2 / Sk (Ом)
            val xc = (ucn * ucn) / sk

            // Реактанс трансформатора: Xt = (uk% * Ucn^2) / (100 * Snom) (Ом)
            val xt = (ukPercent * ucn * ucn) / (100 * snom)

            // Суммарное сопротивление
            val xTotal = xc + xt

            // Трехфазный ток КЗ: I(3) = Ucn / (sqrt(3) * X_total) (кА)
            val i3 = (ucn * 1000) / (sqrt(3.0) * xTotal) // A
            val i3kA = i3 / 1000 // кА

            // Двухфазный ток КЗ: I(2) = I(3) * sqrt(3) / 2
            val i2kA = i3kA * sqrt(3.0) / 2.0

            val result = String.format(Locale.US,
                """
                Опори:
                - Системи (Xc): %.2f Ом
                - Трансформатора (Xt): %.2f Ом
                - Сумарний (X∑): %.2f Ом
                
                Струми КЗ:
                - Трифазний (I⁽³⁾): %.2f кА
                - Двофазний (I⁽²⁾): %.2f кА
                """.trimIndent(),
                xc, xt, xTotal, i3kA, i2kA
            )
            resultText.text = result
        }
    }
}
