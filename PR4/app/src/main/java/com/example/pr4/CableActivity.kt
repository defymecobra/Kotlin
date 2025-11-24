package com.example.pr4

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale
import kotlin.math.sqrt

class CableActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cable)

        val inputIk: EditText = findViewById(R.id.input_ik)
        val inputTf: EditText = findViewById(R.id.input_tf)
        val inputSm: EditText = findViewById(R.id.input_sm)
        val inputUn: EditText = findViewById(R.id.input_un)
        val inputTm: EditText = findViewById(R.id.input_tm)
        val calculateButton: Button = findViewById(R.id.calculate_button)
        val backButton: Button = findViewById(R.id.back_button)
        val resultText: TextView = findViewById(R.id.result_text)

        backButton.setOnClickListener { finish() }

        calculateButton.setOnClickListener {
            val ik = inputIk.text.toString().toDoubleOrNull() ?: 2.5 // kA
            val tf = inputTf.text.toString().toDoubleOrNull() ?: 2.5 // s
            val sm = inputSm.text.toString().toDoubleOrNull() ?: 1300.0 // kVA
            val un = inputUn.text.toString().toDoubleOrNull() ?: 10.0 // kV
            val tm = inputTm.text.toString().toDoubleOrNull() ?: 4000.0 // hours

            // Расчет тока нормального режима: Im = Sm / (2 * sqrt(3) * Un)
            // Sm в кВА, Un в кВ => Im в А
            val im = sm / (2 * sqrt(3.0) * un) // А (но это для деления на 2, нормально Im = Sm / (sqrt(3) * Un))
            // Правильная формула: Im = Sm / (sqrt(3) * Un) в кА
            val imNormal = sm / (sqrt(3.0) * un) // А (если Sm в кВА, Un в кВ)

            // Экономическая плотность тока
            // Для Tm = 4000 часов, из таблицы 7.1:
            // Для алюминия с бумажной изоляцией: Jek = 1.4 А/мм²
            val jek = 1.4 // А/мм²

            // Экономическое сечение: sek = Im / Jek
            val sek = imNormal / jek // мм²

            // Минимальное сечение по термической стойкости:
            // smin = Ik * sqrt(tf) / CT
            // CT = 92 для кабелей с алюминиевыми жилами и бумажной изоляцией
            val ct = 92.0
            val ikAmp = ik * 1000 // Переводим кА в А
            val smin = (ikAmp * sqrt(tf)) / ct // мм²

            // Выбор стандартного сечения
            val standardSections = listOf(10, 16, 25, 35, 50, 70, 95, 120, 150, 185, 240)
            val chosenSection = standardSections.firstOrNull { it >= sek } ?: standardSections.last()

            // Проверка термической стойкости
            val isSafe = chosenSection >= smin

            val result = String.format(Locale.US,
                """
                Розрахунковий струм (Im): %.2f А
                Економічний переріз (sek): %.2f мм²
                Мінімальний переріз (smin): %.2f мм²
                
                Вибрано кабель: %d мм²
                %s
                """.trimIndent(),
                imNormal, sek, smin, chosenSection,
                if (isSafe) "✓ Кабель пройшов перевірку на термічну стійкість"
                else "✗ УВАГА! Потрібно збільшити переріз до ${standardSections.firstOrNull { it >= smin } ?: "більше 240"} мм²"
            )
            resultText.text = result
        }
    }
}
