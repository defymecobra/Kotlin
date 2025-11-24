package com.example.fuelcalculator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnTask1: Button = findViewById(R.id.btn_task1)
        val btnTask2: Button = findViewById(R.id.btn_task2)

        btnTask1.setOnClickListener {
            val intent = Intent(this, SolidFuelActivity::class.java)
            startActivity(intent)
        }

        btnTask2.setOnClickListener {
            val intent = Intent(this, MazutActivity::class.java)
            startActivity(intent)
        }
    }
}