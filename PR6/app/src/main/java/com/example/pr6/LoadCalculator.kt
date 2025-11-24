package com.example.pr6

import kotlin.math.sqrt
import kotlin.math.ceil

object LoadCalculator {
    
    fun calculate(receivers: List<ElectroReceiver>, isShop: Boolean = false): CalculationResult {
        if (receivers.isEmpty()) return CalculationResult()

        var sumPn = 0.0
        var sumPnKv = 0.0
        var sumPn2 = 0.0
        var sumPnKvTg = 0.0
        
        for (ep in receivers) {
            val pnTotal = ep.quantity * ep.nominalPower
            sumPn += pnTotal
            sumPnKv += pnTotal * ep.usageCoefficient
            sumPn2 += ep.quantity * (ep.nominalPower * ep.nominalPower)
            sumPnKvTg += pnTotal * ep.usageCoefficient * ep.calculatedTanPhi
        }
        
        val groupKv = if (sumPn > 0) sumPnKv / sumPn else 0.0
        
        val ne = if (sumPn2 > 0) (sumPn * sumPn) / sumPn2 else 0.0
        val neInt = ceil(ne).toInt()
        
        // Pass isShop flag to KpLookup
        val kp = KpLookup.getKp(neInt, groupKv, isShop)
        
        val pp = kp * sumPnKv
        
        val qAvg = sumPnKvTg
        val qp = if (neInt > 10) qAvg else 1.1 * qAvg
        
        val sp = sqrt(pp * pp + qp * qp)
        
        val un = receivers.firstOrNull()?.voltage ?: 0.38
        val ipCalculated = if (un > 0) pp / un else 0.0 
        
        return CalculationResult(
            groupKv = groupKv,
            ne = ne,
            kp = kp,
            pp = pp,
            qp = qp,
            sp = sp,
            ip = ipCalculated
        )
    }
}

data class CalculationResult(
    val groupKv: Double = 0.0,
    val ne: Double = 0.0,
    val kp: Double = 0.0,
    val pp: Double = 0.0,
    val qp: Double = 0.0,
    val sp: Double = 0.0,
    val ip: Double = 0.0
)
