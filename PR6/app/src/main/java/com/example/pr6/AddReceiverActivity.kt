package com.example.pr6

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_receiver)

        val etName: EditText = findViewById(R.id.et_name)
        val etPn: EditText = findViewById(R.id.et_pn)
        val etKv: EditText = findViewById(R.id.et_kv)
        val etCos: EditText = findViewById(R.id.et_cos)
        val etUn: EditText = findViewById(R.id.et_un)
        val etN: EditText = findViewById(R.id.et_n)
        val etTg: EditText = findViewById(R.id.et_tg) // Optional
        val btnSave: Button = findViewById(R.id.btn_save)

        btnSave.setOnClickListener {
            val name = etName.text.toString()
            val pn = etPn.text.toString().toDoubleOrNull() ?: 0.0
            val kv = etKv.text.toString().toDoubleOrNull() ?: 0.0
            val cos = etCos.text.toString().toDoubleOrNull() ?: 0.9
            val un = etUn.text.toString().toDoubleOrNull() ?: 0.38
            val n = etN.text.toString().toIntOrNull() ?: 1
            val tg = etTg.text.toString().toDoubleOrNull()

            val receiver = ElectroReceiver(name, pn, kv, cos, un, n, 0.92, tg)
            
            val resultIntent = Intent()
            resultIntent.putExtra("receiver", receiver)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
