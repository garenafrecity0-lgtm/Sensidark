package com.example.data.generator

import com.example.data.model.DeviceSpec
import com.example.data.model.Playstyle
import com.example.data.model.SensitivityConfig
import kotlin.random.Random

object SensitivityEngine {

    /**
     * Generates optimal, realistic Free Fire sensitivity values (0 to 200)
     * strictly calculated based on the specific device brand, screen specs
     * (Hz, touch sampling rate, display inches, stock DPI) and the chosen gameplay style.
     */
    fun calculate(
        device: DeviceSpec,
        playstyle: Playstyle,
        useDpi: Boolean = true,
        variationSeed: Int = 0
    ): SensitivityConfig {
        val isApple = device.isApple
        val effectiveUseDpi = if (isApple) false else useDpi

        // 1. Touch Sampling Rate Factor
        val touchFactor = when {
            device.touchSamplingHz >= 480 -> -3 // Ultra-fast gaming response: slight reduction prevents overshooting
            device.touchSamplingHz >= 360 -> -1
            device.touchSamplingHz >= 240 -> 0  // Standard responsive gaming sensor
            device.touchSamplingHz <= 120 -> +4 // Slower sensor needs higher sensitivity compensation
            else -> +2
        }

        // 2. Refresh Rate Factor
        val refreshFactor = when {
            device.refreshRateHz >= 144 -> -2
            device.refreshRateHz >= 120 -> -1
            device.refreshRateHz >= 90 -> 0
            device.refreshRateHz == 60 -> +3
            else -> 0
        }

        // 3. Screen Size Factor
        val screenFactor = when {
            device.screenInch >= 6.75f -> +2 // Longer distance to swipe from bottom to top
            device.screenInch >= 6.4f -> 0
            else -> -2                      // Compact screen requires less thumb travel
        }

        // 4. Brand-Specific Driver & Latency Factor
        val brandLower = device.brand.lowercase()
        val brandFactor = when {
            isApple -> -2 // iOS touch engine is ultra-direct
            brandLower.contains("rog") || brandLower.contains("redmagic") || brandLower.contains("black shark") -> -2 // Gaming phones
            brandLower.contains("infinix") || brandLower.contains("tecno") || brandLower.contains("itel") -> +2 // Transsion Dar-link tuning
            brandLower.contains("samsung") -> 0
            brandLower.contains("xiaomi") || brandLower.contains("redmi") || brandLower.contains("poco") -> +1
            brandLower.contains("realme") || brandLower.contains("oppo") || brandLower.contains("oneplus") -> 0
            else -> 0
        }

        // Compensation if playing WITHOUT DPI modification on Android
        val noDpiCompensation = if (!isApple && !effectiveUseDpi) +4 else 0

        // 5. Base Playstyle Sensitivity Profiles & Ergonomic Button Sizes
        val (baseGeneral, baseRedDot, base2x, base4x, baseSniper, baseFreeLook, recommendedButtonSize, buttonPos) = when (playstyle) {
            Playstyle.MAX_SENSI_200 -> {
                Tuplet8(200, 200, 198, 195, 55, 68, 48, "Bas-Droite (22% du bas, 20% de la droite)")
            }
            Playstyle.PRECISION_HEADSHOT -> {
                // Precision One-Tap: Elevated responsive General (168) + Sharp Red Dot (176)
                Tuplet8(168, 176, 154, 142, 44, 58, 52, "Bas-Droite (24% du bas, 22% de la droite)")
            }
            Playstyle.SPEED_RUSHER -> {
                // Speed & Rusher: Fast General (184) + Ultra-vivid Red Dot (188)
                Tuplet8(184, 188, 168, 156, 50, 68, 46, "Bas-Droite (22% du bas, 20% de la droite)")
            }
            Playstyle.BALANCED -> {
                // Balanced: Pro All-Round setup (158)
                Tuplet8(158, 164, 146, 134, 42, 56, 50, "Bas-Droite (25% du bas, 23% de la droite)")
            }
            Playstyle.RECOIL_CONTROL -> {
                // Anti-Recoil: Stabilized smooth sensitivity (146)
                Tuplet8(146, 152, 132, 120, 38, 50, 54, "Centre-Bas-Droite (26% du bas, 24% de la droite)")
            }
            Playstyle.SNIPER_PRO -> {
                // Sniper Pro: Controlled General (150) + Surgical Low Sniper Scope (38)
                Tuplet8(150, 155, 136, 124, 38, 48, 52, "Bas-Droite (25% du bas, 22% de la droite)")
            }
        }

        // 6. DYNAMIC BUTTON SIZE FACTOR:
        // A larger fire button takes more vertical space on the bottom of the screen,
        // leaving shorter physical drag distance for the thumb to reach the top.
        // Therefore, Sensitivity MUST scale HIGHER to reach the head in fewer millimetres!
        val buttonSizeBonus = ((recommendedButtonSize - 46) * 1.8f).toInt().coerceIn(-4, 16)

        // 7. INVERSE PROPORTIONALITY LAW (DPI vs SENSIBILITY):
        // If Base Sensitivity is lower -> Target DPI must be HIGHER (+100 to +180) to maintain fast 360° rotations.
        // If Base Sensitivity is higher -> Target DPI is MODERATE (+40 to +75) to prevent overshooting.
        val inverseDpiOffset = when {
            baseGeneral <= 150 -> 130 + (variationSeed % 25)
            baseGeneral <= 165 -> 95 + (variationSeed % 20)
            baseGeneral <= 180 -> 65 + (variationSeed % 20)
            else -> 45 + (variationSeed % 15)
        }

        val calculatedDpi = if (effectiveUseDpi) {
            val maxComfortableDpi = minOf(650, device.recommendedSafeMaxDpi)
            (device.stockDpi + inverseDpiOffset).coerceIn(380, maxComfortableDpi)
        } else {
            device.stockDpi
        }

        // 8. DPI COMPENSATION ON SENSIBILITY:
        // When DPI is low (stock or < 420) or playing WITHOUT DPI modification,
        // Sensitivity (General & Red Dot) is boosted significantly (+14 to +26) to guarantee effortless One-Taps!
        val dpiCompensationOnSensi = if (!effectiveUseDpi || calculatedDpi <= 420) {
            +18
        } else if (calculatedDpi >= 550) {
            -4
        } else {
            0
        }

        // Micro-variations on regeneration for fine-tuning
        val generalVariation = if (variationSeed != 0) ((variationSeed * 3) % 5) - 2 else 0
        val redDotVariation = if (variationSeed != 0) ((variationSeed * 4) % 5) - 2 else 0
        val scopeVariation = if (variationSeed != 0) ((variationSeed * 2) % 5) - 2 else 0
        val buttonVariation = if (variationSeed != 0) ((variationSeed * 2) % 3) - 1 else 0

        val hardwareModifier = touchFactor + refreshFactor + screenFactor + brandFactor + noDpiCompensation

        val finalGeneral = if (playstyle == Playstyle.MAX_SENSI_200) {
            200
        } else {
            (baseGeneral + hardwareModifier + buttonSizeBonus + dpiCompensationOnSensi + generalVariation).coerceIn(120, 200)
        }

        val finalRedDot = if (playstyle == Playstyle.MAX_SENSI_200) {
            200
        } else {
            (baseRedDot + (hardwareModifier / 2) + buttonSizeBonus + (dpiCompensationOnSensi / 2) + redDotVariation).coerceIn(125, 200)
        }

        val final2x = if (playstyle == Playstyle.MAX_SENSI_200) {
            200
        } else {
            (base2x + (hardwareModifier / 2) + (buttonSizeBonus / 2) + scopeVariation).coerceIn(90, 195)
        }

        val final4x = if (playstyle == Playstyle.MAX_SENSI_200) {
            200
        } else {
            (base4x + (hardwareModifier / 2) + (buttonSizeBonus / 2) + scopeVariation).coerceIn(80, 185)
        }
        val finalSniper = (baseSniper + (refreshFactor / 2) + (scopeVariation / 2)).coerceIn(30, 65)
        val finalFreeLook = (baseFreeLook + (hardwareModifier / 2) + generalVariation).coerceIn(40, 80)
        val finalButtonSize = (recommendedButtonSize + buttonVariation).coerceIn(44, 58)

        val dragTechnique = when (playstyle) {
            Playstyle.MAX_SENSI_200 -> "Swipe court et sec : la sensi 200 compense l'inertie pour un demi-tour et one-tap éclair."
            Playstyle.PRECISION_HEADSHOT -> "Tir en « J inversé » sec vers le haut dès que le point blanc touche le plastron de l'adversaire."
            Playstyle.SPEED_RUSHER -> "Drag vertical rapide avec swipe complet du pouce pour forcer le lock tête."
            Playstyle.BALANCED -> "Mouvement semi-circulaire fluide de bas en haut selon la distance de l'ennemi."
            Playstyle.RECOIL_CONTROL -> "Tir ascendant léger pendant les 3 premières balles, puis stabilisation vers le bas."
            Playstyle.SNIPER_PRO -> "Quick-scope avec bouton tir gauche + switch arme instantané."
        }

        val headshotRate = when (playstyle) {
            Playstyle.MAX_SENSI_200 -> 98
            Playstyle.PRECISION_HEADSHOT -> 96
            Playstyle.SPEED_RUSHER -> 94
            Playstyle.BALANCED -> 89
            Playstyle.RECOIL_CONTROL -> 85
            Playstyle.SNIPER_PRO -> 94
        }

        val tips = buildList {
            add("Calibré pour ${device.brand} ${device.model} (${device.refreshRateHz}Hz / ${device.touchSamplingHz}Hz tactile).")
            add("Sensibilité Générale ($finalGeneral/200) adaptée au temps de réponse tactile de votre écran.")
            add("Point Rouge ($finalRedDot/200) : Réglé sur mesure pour déclencher le One-Tap instantané sans dépasser la tête.")
            add("Taille du bouton de tir recommandée : $finalButtonSize% pour maximiser la zone de drag.")

            if (isApple) {
                add("🍎 Réglage iOS iPhone : Pas de DPI requis (Android uniquement). Activez Contrôle du sélectionneur (Glisse 120, Mode Précis) et AssistiveTouch (100%).")
            } else if (!effectiveUseDpi) {
                add("🛡️ Mode Sans DPI (DPI d'origine: ${device.stockDpi}) : Sensi rehaussée pour réussir vos One-Taps sans modifier les options développeurs.")
            } else {
                add("⚙️ Nouveau DPI calibré : $calculatedDpi (D'origine: ${device.stockDpi}, max sûr: ${device.recommendedSafeMaxDpi}).")
            }

            if (device.refreshRateHz >= 120) {
                add("Écran ${device.refreshRateHz}Hz détecté : activez « FPS Élevé » dans Free Fire pour une fluidité sans latence.")
            } else {
                add("Écran 60Hz/90Hz : conservez les graphismes fluides pour maintenir 60 FPS constants.")
            }
        }

        return SensitivityConfig(
            general = finalGeneral,
            redDot = finalRedDot,
            scope2x = final2x,
            scope4x = final4x,
            sniper = finalSniper,
            freeLook = finalFreeLook,
            dpi = calculatedDpi,
            stockDpi = device.stockDpi,
            useDpi = effectiveUseDpi,
            isAppleDevice = isApple,
            iosGlidingSpeed = 120,
            iosTrackingSensitivity = "100% (Max)",
            fireButtonSize = finalButtonSize,
            fireButtonPosition = buttonPos,
            dragTechnique = dragTechnique,
            estimatedHeadshotRate = headshotRate,
            tips = tips
        )
    }

    private data class Tuplet8(
        val general: Int,
        val redDot: Int,
        val scope2x: Int,
        val scope4x: Int,
        val sniper: Int,
        val freeLook: Int,
        val buttonSize: Int,
        val buttonPosition: String
    )
}

