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
        description = "Sensibilité Générale et Point Rouge poussées à 200/200 pour les joueurs voulant une vitesse de rotation extrême."
    ),
    PRECISION_HEADSHOT(
        title = "Précision & One-Tap (Calibré)",
        subtitle = "Spécial Headshot (M1887, Desert Eagle)",
        badge = "🎯 ONE-TAP",
        description = "Général stable (125-138) et Point Rouge vif (130-145) pour caler le lock tête net sans que le viseur ne vole au-dessus de l'adversaire."
    ),
    SPEED_RUSHER(
        title = "Rapidité & Rusher Dynamique",
        subtitle = "Mouvements 360° & Mur de glace rapide",
        badge = "⚡ RUSHER",
        description = "Sensibilité dynamique (145-158) pour des demi-tours fluides et duels rapprochés SMG/Pompe sans décrochage."
    ),
    BALANCED(
        title = "Équilibré & Polyvalent",
        subtitle = "Parfait pour Clash Squad & Classé BR",
        badge = "⚖️ ALL-ROUND",
        description = "Le compromis pro idéal (115-128) entre contrôle du recul, précision à mi-distance et fluidité de caméra."
    ),
    RECOIL_CONTROL(
        title = "Contrôle du Recul (Anti-Recul)",
        subtitle = "Tir continu stabilisé (AK47, SCAR, M4)",
        badge = "🛡️ ANTI-RECOIL",
        description = "Sensibilité douce (102-116) pour maintenir le réticule verrouillé sur la cible lors des tirs en rafale continue."
    ),
    SNIPER_PRO(
        title = "Sniper & Longue Portée",
        subtitle = "Visée millimétrée (AWM, M82B, Barrett)",
        badge = "🔭 SNIPER",
        description = "Mire sniper ultra-précise (35-45) pour un switch d'arme fluide et des tirs chirurgicaux à longue distance."
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
    val useDpi: Boolean = true,
    val isAppleDevice: Boolean = false,
    val iosGlidingSpeed: Int = 120,
    val iosTrackingSensitivity: String = "100% (Max)",
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
) {
    val isApple: Boolean
        get() = brand.contains("Apple", ignoreCase = true) ||
                brand.contains("iPhone", ignoreCase = true) ||
                model.contains("iPhone", ignoreCase = true)
}
