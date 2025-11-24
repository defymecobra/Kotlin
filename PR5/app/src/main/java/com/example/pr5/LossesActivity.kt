package com.example.pr5

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class LossesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_losses)

        val inputW: EditText = findViewById(R.id.input_w)
        val inputTv: EditText = findViewById(R.id.input_tv)
        val inputPm: EditText = findViewById(R.id.input_pm)
        val inputTm: EditText = findViewById(R.id.input_tm)
        val inputZpera: EditText = findViewById(R.id.input_zpera)
        val inputZperp: EditText = findViewById(R.id.input_zperp)
        val calculateButton: Button = findViewById(R.id.calculate_button)
        val backButton: Button = findViewById(R.id.back_button)
        val resultText: TextView = findViewById(R.id.result_text)

        backButton.setOnClickListener { finish() }

        calculateButton.setOnClickListener {
            val w = inputW.text.toString().toDoubleOrNull() ?: 0.01
            val tv = inputTv.text.toString().toDoubleOrNull() ?: 0.045 // года
            val pm = inputPm.text.toString().toDoubleOrNull() ?: 5120.0 // кВт
            val tm = inputTm.text.toString().toDoubleOrNull() ?: 6451.0 // год
            val zpera = inputZpera.text.toString().toDoubleOrNull() ?: 23.6
            val zperp = inputZperp.text.toString().toDoubleOrNull() ?: 17.6

            // Математическое ожидание аварийного недоотпуска
            // M(W_ned.a) = w * tv * Pm * Tm
            // В примере: 0.01 * 45*10^-3 * 5.12*10^3 * 6451 = 14892 кВт*год (примерно 14900)
            val mwNedA = w * tv * pm * tm

            // Математическое ожидание планового недоотпуска
            // kp = 4 * 10^-3 (среднее время планового простоя трансформатора)
            val kp = 0.004
            // M(W_ned.p) = kp * Pm * Tm
            // В примере: 4*10^-3 * 5.12*10^3 * 6451 = 132116 кВт*год (примерно 132400)
            val mwNedP = kp * pm * tm

            // Математическое ожидание убытков
            // M(Z) = zpera * M(W_ned.a) + zperp * M(W_ned.p)
            val mz = zpera * mwNedA + zperp * mwNedP

            val result = String.format(Locale.US,
                """
                Мат. очікування аварійного недовідпуску (M(Wн.а)): %.2f кВт·год
                Мат. очікування планового недовідпуску (M(Wн.п)): %.2f кВт·год
                
                Мат. очікування збитків (M(З)): %.2f грн
                """.trimIndent(),
                mwNedA, mwNedP, mz
            )
            resultText.text = result
        }
    }
}
