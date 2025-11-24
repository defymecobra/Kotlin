package com.example.pr3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale
import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputPc: EditText = findViewById(R.id.input_pc)
        val inputSigma1: EditText = findViewById(R.id.input_sigma1)
        val inputSigma2: EditText = findViewById(R.id.input_sigma2)
        val inputCost: EditText = findViewById(R.id.input_cost)
        val calculateButton: Button = findViewById(R.id.calculate_button)
        val resultText: TextView = findViewById(R.id.result_text)

        calculateButton.setOnClickListener {
            val pc = inputPc.text.toString().toDoubleOrNull() ?: 0.0
            val sigma1 = inputSigma1.text.toString().toDoubleOrNull() ?: 1.0
            val sigma2 = inputSigma2.text.toString().toDoubleOrNull() ?: 0.25
            val costB = inputCost.text.toString().toDoubleOrNull() ?: 7.0 // UAH/kWh

            // Integration range: Pc +/- 5% (0.25 MW for Pc=5)
            // But task says range is 4.75 to 5.25 for Pc=5.
            // Let's assume range is always Pc +/- 0.25 (since delta=5% of 5MW = 0.25)
            // Or strictly 5% of Pc. Let's use 5% of Pc.
            val rangeDelta = pc * 0.05
            val lowerBound = pc - rangeDelta
            val upperBound = pc + rangeDelta

            // Function to calculate normal distribution probability density
            fun normalDistribution(p: Double, sigma: Double): Double {
                return (1.0 / (sigma * sqrt(2 * PI))) * exp(-((p - pc) * (p - pc)) / (2 * sigma * sigma))
            }

            // Numerical integration (Trapezoidal rule)
            fun integrate(sigma: Double): Double {
                val steps = 1000
                val stepSize = (upperBound - lowerBound) / steps
                var sum = 0.0
                for (i in 0 until steps) {
                    val x1 = lowerBound + i * stepSize
                    val x2 = lowerBound + (i + 1) * stepSize
                    sum += 0.5 * (normalDistribution(x1, sigma) + normalDistribution(x2, sigma)) * stepSize
                }
                return sum
            }

            fun calculateProfit(sigma: Double): String {
                val delta = integrate(sigma) // Share of energy without imbalance
                
                // W1 (Energy without imbalance) = Pc * 24 * delta
                val w1 = pc * 24 * delta // MW*h
                
                // Profit 1 (Pi) = W1 * B * 1000 (convert MW to kW for price)
                // B is in UAH/kWh. 1 MWh = 1000 kWh.
                // So Profit = W1 * 1000 * B
                val profit1 = w1 * 1000 * costB // UAH

                // W2 (Energy with imbalance) = Pc * 24 * (1 - delta)
                val w2 = pc * 24 * (1 - delta) // MW*h
                
                // Penalty (Sh) = W2 * B * 1000
                val penalty = w2 * 1000 * costB // UAH

                // Net Profit
                val netProfit = profit1 - penalty

                return String.format(Locale.US,
                    """
                    Частка без небалансів: %.2f%%
                    Прибуток (W1): %.2f тис. грн
                    Штраф (W2): %.2f тис. грн
                    Чистий прибуток: %.2f тис. грн
                    """.trimIndent(),
                    delta * 100, profit1 / 1000, penalty / 1000, netProfit / 1000
                )
            }

            val result1 = calculateProfit(sigma1)
            val result2 = calculateProfit(sigma2)

            resultText.text = "Система 1 (sigma=$sigma1):\n$result1\n\nСистема 2 (sigma=$sigma2):\n$result2"
        }
    }
}