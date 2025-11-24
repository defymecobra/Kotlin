package com.example.pr4

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class XpnemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_xpnem)

        val calculateButton: Button = findViewById(R.id.calculate_button)
        val backButton: Button = findViewById(R.id.back_button)
        val resultText: TextView = findViewById(R.id.result_text)

        backButton.setOnClickListener { finish() }

        calculateButton.setOnClickListener {
            // Данные из примера 7.4 (Хмельницкие сетевые электрические сети)
            
            // 1. НОРМАЛЬНЫЙ РЕЖИМ (оба автотрансформатора ХАЕС работают)
            // Rc.н = 10.65 Ом, Xc.н = 24.02 Ом
            val rcNormal = 10.65
            val xcNormal = 24.02
            
            // Трансформатор: Xt = 233 Ом
            val xt = 233.0
            
            // Суммарное сопротивление для нормального режима
            val rSumNormal = rcNormal
            val xSumNormal = xcNormal + xt // 24.02 + 233 = 257.02
            val zNormal = kotlin.math.sqrt(rSumNormal * rSumNormal + xSumNormal * xSumNormal)
            
            // Напряжение сети: Uвн = 10 kV (или 11 kV из примера)
            val uVn = 11.0 // kV
            
            // Трехфазный ток КЗ: I(3) = Uвн * 10^3 / (sqrt(3) * Z)
            val i3Normal = (uVn * 1000) / (kotlin.math.sqrt(3.0) * zNormal) / 1000 // kA
            
            // Двухфазный ток: I(2) = I(3) * sqrt(3) / 2
            val i2Normal = i3Normal * kotlin.math.sqrt(3.0) / 2.0
            
            // 2. МИНИМАЛЬНЫЙ РЕЖИМ (один автотрансформатор выведен)
            // Rc.min = 34.88 Ом, Xc.min = 65.68 Ом
            val rcMin = 34.88
            val xcMin = 65.68
            
            val rSumMin = rcMin
            val xSumMin = xcMin + xt // 65.68 + 233 = 298.68
            val zMin = kotlin.math.sqrt(rSumMin * rSumMin + xSumMin * xSumMin)
            
            val i3Min = (uVn * 1000) / (kotlin.math.sqrt(3.0) * zMin) / 1000 // kA
            val i2Min = i3Min * kotlin.math.sqrt(3.0) / 2.0
            
            // 3. АВАРИЙНЫЙ РЕЖИМ (с учетом коэффициента приведения k_пр)
            // k_пр = U²н.н / U²к.н = 11² / 115² = 0.009
            val kPr = (11.0 * 11.0) / (115.0 * 115.0)
            
            // Rш.н = Rш * k_пр = 10.65 * 0.009 = 0.1 Ом
            val rEmergency = rcNormal * kPr
            val xEmergency = xcNormal * kPr
            
            val rSumEmerg = rEmergency
            val xSumEmerg = xEmergency + xt // ~ 2.31 Ом
            val zEmerg = kotlin.math.sqrt(rSumEmerg * rSumEmerg + xSumEmerg * xSumEmerg)
            
            val i3Emerg = (uVn * 1000) / (kotlin.math.sqrt(3.0) * zEmerg) / 1000 // kA
            val i2Emerg = i3Emerg * kotlin.math.sqrt(3.0) / 2.0

            val result = String.format(Locale.US,
                """
                === НОРМАЛЬНИЙ РЕЖИМ ===
                Опір системи: R = %.2f Ом, X = %.2f Ом
                Z∑ = %.2f Ом
                I⁽³⁾ = %.2f кА
                I⁽²⁾ = %.2f кА
                
                === МІНІМАЛЬНИЙ РЕЖИМ ===
                Опір системи: R = %.2f Ом, X = %.2f Ом
                Z∑ = %.2f Ом
                I⁽³⁾ = %.2f кА
                I⁽²⁾ = %.2f кА
                
                === АВАРІЙНИЙ РЕЖИМ ===
                Коеф. приведення k = %.4f
                Опір системи: R = %.2f Ом, X = %.2f Ом
                Z∑ = %.2f Ом
                I⁽³⁾ = %.2f кА
                I⁽²⁾ = %.2f кА
                """.trimIndent(),
                rcNormal, xSumNormal, zNormal, i3Normal, i2Normal,
                rcMin, xSumMin, zMin, i3Min, i2Min,
                kPr, rEmergency, xSumEmerg, zEmerg, i3Emerg, i2Emerg
            )
            resultText.text = result
        }
    }
}
