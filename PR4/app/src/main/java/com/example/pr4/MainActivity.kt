package com.example.pr4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCable: Button = findViewById(R.id.btn_cable)
        val btnShortCircuit: Button = findViewById(R.id.btn_short_circuit)
        val btnXpnem: Button = findViewById(R.id.btn_xpnem)

        btnCable.setOnClickListener {
            val intent = Intent(this, CableActivity::class.java)
            startActivity(intent)
        }

        btnShortCircuit.setOnClickListener {
            val intent = Intent(this, ShortCircuitActivity::class.java)
            startActivity(intent)
        }

        btnXpnem.setOnClickListener {
            val intent = Intent(this, XpnemActivity::class.java)
            startActivity(intent)
        }
    }
}