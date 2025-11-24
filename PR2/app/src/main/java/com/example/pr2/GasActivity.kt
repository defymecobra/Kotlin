package com.example.pr2

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class GasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gas)

        val backButton: Button = findViewById(R.id.back_button)
        backButton.setOnClickListener { finish() }
    }
}
