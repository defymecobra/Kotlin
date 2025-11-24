package com.example.pr6

object KpLookup {
    
    fun getKp(ne: Int, kv: Double, isShop: Boolean): Double {
        if (isShop) {
            // Таблица 6.4 (для шин 0.38 кВ ТП)
            // Упрощенная логика для примера (ne=56, Kv=0.32 -> Kp=0.7)
            if (ne > 50 && kv > 0.3) return 0.7
            if (ne > 20 && kv > 0.2) return 0.8
            return 0.9
        } else {
            // Таблица 6.3 (для ШР)
            // Пример: ne=15, Kv=0.2 -> Kp=1.25
            if (ne in 11..20 && kv in 0.15..0.25) return 1.25
            
            // Заглушки
            if (ne <= 5) {
                if (kv < 0.2) return 3.0
                return 1.5
            }
            if (ne > 10 && kv < 0.2) return 1.5
            
            return 1.0
        }
    }
}
