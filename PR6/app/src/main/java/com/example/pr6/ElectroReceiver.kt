package com.example.pr6

import java.io.Serializable

data class ElectroReceiver(
    val name: String,
    val nominalPower: Double, // Pn, кВт
    val usageCoefficient: Double, // Kv
    val loadPowerFactor: Double, // cos phi
    val voltage: Double, // Un, кВ
    val quantity: Int, // n, шт
    val efficiency: Double = 0.92, // n_n (ККД), default 0.92 from example
    val tanPhi: Double? = null // tg phi, if known directly
) : Serializable {
    // Вычисляем tg phi, если он не задан, через cos phi
    val calculatedTanPhi: Double
        get() {
            if (tanPhi != null) return tanPhi
            if (loadPowerFactor >= 1.0) return 0.0
            // tg(acos(cos_phi)) = sqrt(1 - cos^2) / cos
            val sinPhi = Math.sqrt(1 - loadPowerFactor * loadPowerFactor)
            return sinPhi / loadPowerFactor
        }

    val totalNominalPower: Double
        get() = quantity * nominalPower
}
