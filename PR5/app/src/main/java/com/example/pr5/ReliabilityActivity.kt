package com.example.pr5

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class ReliabilityActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reliability)

        val inputWoc: EditText = findViewById(R.id.input_w_oc)
        val inputTvOc: EditText = findViewById(R.id.input_tv_oc)
        val inputTpOc: EditText = findViewById(R.id.input_tp_oc)
        val inputWsv: EditText = findViewById(R.id.input_w_sv)
        val calculateButton: Button = findViewById(R.id.calculate_button)
        val backButton: Button = findViewById(R.id.back_button)
        val resultText: TextView = findViewById(R.id.result_text)

        backButton.setOnClickListener { finish() }

        calculateButton.setOnClickListener {
            val wOc = inputWoc.text.toString().toDoubleOrNull() ?: 0.295
            val tvOc = inputTvOc.text.toString().toDoubleOrNull() ?: 10.7
            val tpOc = inputTpOc.text.toString().toDoubleOrNull() ?: 43.0
            val wSv = inputWsv.text.toString().toDoubleOrNull() ?: 0.02

            // Коэффициент аварийного простоя (ka_oc)
            val kaOc = (wOc * tvOc) / 8760.0

            // Коэффициент планового простоя (kp_oc)
            val kpMax = tpOc / 8760.0
            val kpOc = 1.2 * kpMax

            // Частота отказов двухколовой системы (w_dk)
            // Формула для идентичных цепей: w_dk = 2 * w_oc * (ka_oc + 0.5 * kp_oc)
            val wDk = 2 * wOc * (kaOc + 0.5 * kpOc)

            // С учетом секционного выключателя
            val w2c = wDk + wSv

            val result = String.format(Locale.US,
                """
                === Одноколова система ===
                Частота відмов (ω_oc): %.3f рік⁻¹
                Середній час відновлення (t_в.oc): %.2f год
                Коеф. аварійного простою (k_а.oc): %.5f
                Коеф. планового простою (k_п.oc): %.5f
                
                === Двоколова система ===
                Частота відмов (ω_дк): %.5f рік⁻¹
                З урахуванням секційного вимикача (ω_2с): %.5f рік⁻¹
                
                Висновок: Надійність двоколової системи значно вища.
                """.trimIndent(),
                wOc, tvOc, kaOc, kpOc, wDk, w2c
            )
            resultText.text = result
        }
    }
}
