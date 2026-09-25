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
        variationSeed: Int = 0
    ): SensitivityConfig {
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
            brandLower.contains("apple") || brandLower.contains("iphone") -> -2 // iOS touch engine is ultra-direct
            brandLower.contains("rog") || brandLower.contains("redmagic") || brandLower.contains("black shark") -> -2 // Gaming phones
            brandLower.contains("infinix") || brandLower.contains("tecno") || brandLower.contains("itel") -> +2 // Transsion Dar-link tuning
            brandLower.contains("samsung") -> 0
            brandLower.contains("xiaomi") || brandLower.contains("redmi") || brandLower.contains("poco") -> +1
            brandLower.contains("realme") || brandLower.contains("oppo") || brandLower.contains("oneplus") -> 0
            else -> 0
        }

        // 5. Base Playstyle Sensitivity Profiles
        val (baseGeneral, baseRedDot, base2x, base4x, baseSniper, baseFreeLook, recommendedButtonSize, buttonPos) = when (playstyle) {
            Playstyle.MAX_SENSI_200 -> {
                // Full 200/200 on Main Scopes, but Low Free Look (80) and Low Sniper (65) for stability
                Tuplet8(200, 200, 198, 195, 65, 80, 42, "Bas-Droite (20% du bas, 18% de la droite)")
            }
            Playstyle.PRECISION_HEADSHOT -> {
                // Precision One-Tap: High General (182) + Red Dot (190), Low Sniper (52) & Low Free Look (65)
                Tuplet8(182, 190, 172, 162, 52, 65, 44, "Bas-Droite (25% du bas, 22% de la droite)")
            }
            Playstyle.SPEED_RUSHER -> {
                // Speed & Rusher: Fast General (192) + Red Dot (194), Controlled Sniper (62) & Low Free Look (75)
                Tuplet8(192, 194, 180, 170, 62, 75, 42, "Bas-Droite (22% du bas, 20% de la droite)")
            }
            Playstyle.BALANCED -> {
                // Balanced: All-Round setup, Low Sniper (50) & Low Free Look (60)
                Tuplet8(174, 182, 162, 152, 50, 60, 46, "Bas-Droite (26% du bas, 24% de la droite)")
            }
            Playstyle.RECOIL_CONTROL -> {
                // Recoil Control: Stabilized medium sensitivity, Low Sniper (48) & Low Free Look (55)
                Tuplet8(162, 170, 148, 138, 48, 55, 50, "Centre-Bas-Droite (28% du bas, 25% de la droite)")
            }
            Playstyle.SNIPER_PRO -> {
                // Sniper Pro: Steady general + Ultra-Low Sniper Scope (45) for surgical precision, Low Free Look (58)
                Tuplet8(160, 168, 145, 135, 45, 58, 48, "Bas-Droite (26% du bas, 22% de la droite)")
            }
        }

        // Distinct DPI offsets pool to guarantee DPI changes with each variation
        val dpiOffsets = listOf(110, 150, 190, 230, 270, 310, 130, 170, 210, 250, 290)
        val seedIndex = if (variationSeed != 0) {
            kotlin.math.abs(variationSeed) % dpiOffsets.size
        } else {
            when (playstyle) {
                Playstyle.MAX_SENSI_200 -> 5      // +310
                Playstyle.PRECISION_HEADSHOT -> 2 // +190
                Playstyle.SPEED_RUSHER -> 4       // +270
                Playstyle.BALANCED -> 1           // +150
                Playstyle.RECOIL_CONTROL -> 0     // +110
                Playstyle.SNIPER_PRO -> 1         // +150
            }
        }

        val chosenDpiOffset = dpiOffsets[seedIndex]
        val calculatedDpi = (device.stockDpi + chosenDpiOffset).coerceIn(360, device.recommendedSafeMaxDpi)

        // Micro-variations on regeneration for fine-tuning
        val generalVariation = if (variationSeed != 0) ((variationSeed * 3) % 7) - 3 else 0
        val redDotVariation = if (variationSeed != 0) ((variationSeed * 5) % 7) - 3 else 0
        val scopeVariation = if (variationSeed != 0) ((variationSeed * 2) % 5) - 2 else 0
        val buttonVariation = if (variationSeed != 0) ((variationSeed * 2) % 5) - 2 else 0

        val hardwareModifier = touchFactor + refreshFactor + screenFactor + brandFactor

        val finalGeneral = if (playstyle == Playstyle.MAX_SENSI_200) {
            200
        } else {
            (baseGeneral + hardwareModifier + generalVariation).coerceIn(0, 200)
        }

        val finalRedDot = if (playstyle == Playstyle.MAX_SENSI_200) {
            200
        } else {
            (baseRedDot + (hardwareModifier / 2) + redDotVariation).coerceIn(0, 200)
        }

        val final2x = (base2x + (hardwareModifier / 2) + scopeVariation).coerceIn(0, 200)
        val final4x = (base4x + (hardwareModifier / 2) + scopeVariation).coerceIn(0, 200)
        val finalSniper = (baseSniper + (refreshFactor / 2) + (scopeVariation / 2)).coerceIn(30, 75)
        val finalFreeLook = (baseFreeLook + (hardwareModifier / 2) + generalVariation).coerceIn(40, 85)
        val finalButtonSize = (recommendedButtonSize + buttonVariation).coerceIn(35, 65)

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

        val tips = listOf(
            "Calibré pour ${device.brand} ${device.model} (${device.refreshRateHz}Hz / ${device.touchSamplingHz}Hz tactile).",
            "Sensibilité Générale ($finalGeneral/200) adaptée au temps de réponse tactile de votre écran.",
            "Point Rouge ($finalRedDot/200) : Réglé sur mesure pour déclencher le One-Tap instantané sans dépasser la tête.",
            "Taille du bouton de tir recommandée : $finalButtonSize% pour maximiser la zone de drag.",
            "DPI conseillé : $calculatedDpi (D'origine: ${device.stockDpi}, max sûr: ${device.recommendedSafeMaxDpi}).",
            if (device.refreshRateHz >= 120) {
                "Écran ${device.refreshRateHz}Hz détecté : activez « FPS Élevé » dans Free Fire pour une fluidité sans latence."
            } else {
                "Écran 60Hz/90Hz : conservez les graphismes fluides pour maintenir 60 FPS constants."
            }
        )

        return SensitivityConfig(
            general = finalGeneral,
            redDot = finalRedDot,
            scope2x = final2x,
            scope4x = final4x,
            sniper = finalSniper,
            freeLook = finalFreeLook,
            dpi = calculatedDpi,
            stockDpi = device.stockDpi,
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

