package com.example.simple_lab_example

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Calculator1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calculator_1_activity)

        val input1: EditText = findViewById(R.id.sum_number_1)
        val input2: EditText = findViewById(R.id.sum_number_2)

        val res: TextView = findViewById(R.id.sum_res)

        val backButton: Button = findViewById(R.id.back_button_1)
        val sumButton: Button = findViewById(R.id.sum_button)

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        sumButton.setOnClickListener {
            val number1 = input1.text.toString().toInt()
            val number2 = input2.text.toString().toInt()

            res.text = (number1 + number2).toString()
        }

    }
}