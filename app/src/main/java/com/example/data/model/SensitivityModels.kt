package com.example.data.model

enum class Playstyle(
    val title: String,
    val subtitle: String,
    val badge: String,
    val description: String
) {
    MAX_SENSI_200(
        title = "Sensibilité Max 200%",
        subtitle = "Full 200/200 & Vitesse Ultime",
        badge = "🔥 MAX 200",
        description = "Sensibilité Générale et Point Rouge calibrées à 200/200 pour une vitesse de rotation maximale et One-Tap instantané sans latence."
    ),
    PRECISION_HEADSHOT(
        title = "Précision & One-Tap (190-200)",
        subtitle = "Spécial Headshot (M1887, Desert Eagle)",
        badge = "🎯 ONE-TAP",
        description = "Général élevé (190-198) et Point Rouge ultra-vif pour déclencher le flick One-Tap instantané sans blocage sur le plastron."
    ),
    SPEED_RUSHER(
        title = "Rapidité & Rusher (195-200)",
        subtitle = "Mouvements 360° & Mur de glace éclair",
        badge = "⚡ RUSHER",
        description = "Sensibilité ultra-haute (195-200) pour les demi-tours instantanés, glissades et combats rapprochés SMG/Pompe."
    ),
    BALANCED(
        title = "Équilibré & Polyvalent",
        subtitle = "Parfait pour Clash Squad & Classé BR",
        badge = "⚖️ ALL-ROUND",
        description = "Le compromis pro idéal entre contrôle du recul, précision à mi-distance et vitesse de caméra."
    ),
    RECOIL_CONTROL(
        title = "Contrôle du Recul (Spray)",
        subtitle = "Tir continu stabilisé (AK47, SCAR, M4)",
        badge = "🛡️ ANTI-RECOIL",
        description = "Ajustement progressif pour maintenir le réticule verrouillé sur la cible lors des rafales."
    ),
    SNIPER_PRO(
        title = "Sniper & Longue Portée",
        subtitle = "Visée millimétrée (AWM, M82B, Barrett)",
        badge = "🔭 SNIPER",
        description = "Mire sniper adoucie pour un switch d'arme fluide et des tirs chirurgicaux à longue distance."
    )
}

data class SensitivityConfig(
    val general: Int,
    val redDot: Int,
    val scope2x: Int,
    val scope4x: Int,
    val sniper: Int,
    val freeLook: Int,
    val dpi: Int,
    val stockDpi: Int,
    val fireButtonSize: Int,
    val fireButtonPosition: String,
    val dragTechnique: String,
    val estimatedHeadshotRate: Int,
    val tips: List<String>
) {
    companion object {
        const val MIN_VAL = 0
        const val MAX_VAL = 200

        fun clamp(value: Int): Int = value.coerceIn(MIN_VAL, MAX_VAL)
    }
}

data class DeviceSpec(
    val brand: String,
    val model: String,
    val refreshRateHz: Int = 90,
    val screenInch: Float = 6.67f,
    val stockDpi: Int = 392,
    val recommendedSafeMaxDpi: Int = 720,
    val touchSamplingHz: Int = 240,
    val ramGb: Int = 8
)
