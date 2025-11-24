package com.example.pr6

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private val receivers = mutableListOf<ElectroReceiver>()
    private lateinit var adapter: ReceiverAdapter
    private lateinit var tvResult: TextView
    private lateinit var spinnerCalcLevel: Spinner

    private val addReceiverLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val receiver = result.data?.getSerializableExtra("receiver") as? ElectroReceiver
            if (receiver != null) {
                receivers.add(receiver)
                adapter.notifyItemInserted(receivers.size - 1)
                calculate()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvResult = findViewById(R.id.tv_result)
        spinnerCalcLevel = findViewById(R.id.spinner_calc_level)
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        val fabAdd: FloatingActionButton = findViewById(R.id.fab_add)
        val btnLoadExample: Button = findViewById(R.id.btn_load_example)

        adapter = ReceiverAdapter(receivers) { position ->
            receivers.removeAt(position)
            adapter.notifyItemRemoved(position)
            calculate()
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        fabAdd.setOnClickListener {
            val intent = Intent(this, AddReceiverActivity::class.java)
            addReceiverLauncher.launch(intent)
        }

        btnLoadExample.setOnClickListener {
            loadExampleData()
        }
        
        spinnerCalcLevel.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                calculate()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun loadExampleData() {
        receivers.clear()
        // ШР1
        receivers.add(ElectroReceiver("Шліфувальний верстат", 20.0, 0.15, 0.9, 0.38, 4, 0.92, 1.33))
        receivers.add(ElectroReceiver("Свердлильний верстат", 14.0, 0.12, 0.9, 0.38, 2, 0.92, 1.0))
        receivers.add(ElectroReceiver("Фугувальний верстат", 42.0, 0.15, 0.9, 0.38, 4, 0.92, 1.33))
        receivers.add(ElectroReceiver("Циркулярна пила", 36.0, 0.3, 0.9, 0.38, 1, 0.92, 1.52))
        receivers.add(ElectroReceiver("Прес", 20.0, 0.5, 0.9, 0.38, 1, 0.92, 0.75))
        receivers.add(ElectroReceiver("Полірувальний верстат", 40.0, 0.2, 0.9, 0.38, 1, 0.92, 1.0))
        receivers.add(ElectroReceiver("Фрезерний верстат", 32.0, 0.2, 0.9, 0.38, 2, 0.92, 1.0))
        receivers.add(ElectroReceiver("Вентилятор", 20.0, 0.65, 0.9, 0.38, 1, 0.92, 0.75))
        
        // Для примера цеха нужно добавить ШР2, ШР3 и Крупные.
        // Но если мы в режиме "ШР", то это будет слишком много.
        // Сделаем так: если выбран режим "Цех", добавим всё. Если "ШР", только ШР1.
        
        if (spinnerCalcLevel.selectedItemPosition == 1) { // Цех
             // Добавляем копии для ШР2 и ШР3
             val copyShr = ArrayList(receivers)
             receivers.addAll(copyShr) // ШР2
             receivers.addAll(copyShr) // ШР3
             
             // Крупные
             receivers.add(ElectroReceiver("Зварювальний трансф.", 100.0, 0.2, 0.9, 0.38, 2, 0.92, 3.0))
             receivers.add(ElectroReceiver("Сушильна шафа", 120.0, 0.8, 0.9, 0.38, 2, 0.92, 0.0))
        }
        
        adapter.notifyDataSetChanged()
        calculate()
    }

    private fun calculate() {
        val isShop = spinnerCalcLevel.selectedItemPosition == 1
        val res = LoadCalculator.calculate(receivers, isShop)

        val sb = StringBuilder()
        sb.append("Груповий коефіцієнт використання: ${String.format(Locale.US, "%.4f", res.groupKv)}\n")
        sb.append("Ефективна кількість ЕП: ${String.format(Locale.US, "%.2f", res.ne)}\n")
        sb.append("Розрахунковий коефіцієнт (Kp): ${String.format(Locale.US, "%.2f", res.kp)}\n")
        sb.append("Розрахункове навантаження:\n")
        sb.append("Pp = ${String.format(Locale.US, "%.2f", res.pp)} кВт\n")
        sb.append("Qp = ${String.format(Locale.US, "%.2f", res.qp)} квар\n")
        sb.append("Sp = ${String.format(Locale.US, "%.2f", res.sp)} кВА\n")
        sb.append("Ip = ${String.format(Locale.US, "%.2f", res.ip)} А\n")

        tvResult.text = sb.toString()
    }
}