package com.example.pr5

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnReliability: Button = findViewById(R.id.btn_reliability)
        val btnLosses: Button = findViewById(R.id.btn_losses)

        btnReliability.setOnClickListener {
            val intent = Intent(this, ReliabilityActivity::class.java)
            startActivity(intent)
        }

        btnLosses.setOnClickListener {
            val intent = Intent(this, LossesActivity::class.java)
            startActivity(intent)
        }
    }
}