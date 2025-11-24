package com.example.simple_lab_example

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val part1Button: Button = findViewById(R.id.part_1_button)
        val part2Button: Button = findViewById(R.id.part_2_button)

        part1Button.setOnClickListener {
            val intent = Intent(this, Calculator1Activity::class.java)
            startActivity(intent)
        }

        part2Button.setOnClickListener {
            val intent = Intent(this, Calculator1Activity::class.java)
            startActivity(intent)
        }
    }
}