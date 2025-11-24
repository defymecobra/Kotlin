package com.example.pr2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCoal: Button = findViewById(R.id.btn_coal)
        val btnMazut: Button = findViewById(R.id.btn_mazut)
        val btnGas: Button = findViewById(R.id.btn_gas)

        btnCoal.setOnClickListener {
            val intent = Intent(this, CoalActivity::class.java)
            startActivity(intent)
        }

        btnMazut.setOnClickListener {
            val intent = Intent(this, MazutActivity::class.java)
            startActivity(intent)
        }

        btnGas.setOnClickListener {
            val intent = Intent(this, GasActivity::class.java)
            startActivity(intent)
        }
    }
}