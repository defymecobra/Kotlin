package com.example.pr6

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReceiverAdapter(
    private val receivers: MutableList<ElectroReceiver>,
    private val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<ReceiverAdapter.ReceiverViewHolder>() {

    class ReceiverViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_name)
        val tvDetails: TextView = view.findViewById(R.id.tv_details)
        val btnDelete: ImageButton = view.findViewById(R.id.btn_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceiverViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_receiver, parent, false)
        return ReceiverViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReceiverViewHolder, position: Int) {
        val item = receivers[position]
        holder.tvName.text = item.name
        holder.tvDetails.text = "n=${item.quantity}, Pn=${item.nominalPower} кВт, Kv=${item.usageCoefficient}, cos=${item.loadPowerFactor}"
        
        holder.btnDelete.setOnClickListener {
            onDelete(position)
        }
    }

    override fun getItemCount() = receivers.size
}
