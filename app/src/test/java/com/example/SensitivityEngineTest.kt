package com.example

import com.example.data.generator.SensitivityEngine
import com.example.data.model.DeviceCatalog
import com.example.data.model.DeviceSpec
import com.example.data.model.Playstyle
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class SensitivityEngineTest {

    @Test
    fun testSensitivityWithin0To200Bounds() {
        val testDevice = DeviceSpec(
            brand = "Samsung",
            model = "Galaxy S24 Ultra",
            refreshRateHz = 120,
            screenInch = 6.8f,
            stockDpi = 411,
            recommendedSafeMaxDpi = 800,
            touchSamplingHz = 360,
            ramGb = 12
        )

        Playstyle.entries.forEach { style ->
            val config = SensitivityEngine.calculate(testDevice, style)

            assertTrue("General must be >= 0", config.general >= 0)
            assertTrue("General must be <= 200", config.general <= 200)

            assertTrue("RedDot must be >= 0", config.redDot >= 0)
            assertTrue("RedDot must be <= 200", config.redDot <= 200)

            assertTrue("Scope2x must be >= 0", config.scope2x >= 0)
            assertTrue("Scope2x must be <= 200", config.scope2x <= 200)

            assertTrue("Scope4x must be >= 0", config.scope4x >= 0)
            assertTrue("Scope4x must be <= 200", config.scope4x <= 200)

            assertTrue("Sniper must be >= 0", config.sniper >= 0)
            assertTrue("Sniper must be <= 200", config.sniper <= 200)

            assertTrue("FreeLook must be >= 0", config.freeLook >= 0)
            assertTrue("FreeLook must be <= 200", config.freeLook <= 200)

            assertTrue("FireButtonSize must be between 30 and 70", config.fireButtonSize in 30..70)
            assertTrue("DPI must not exceed recommendedSafeMaxDpi", config.dpi <= testDevice.recommendedSafeMaxDpi)
        }
    }

    @Test
    fun testDeviceCatalogBrandsAndModelsNotEmpty() {
        assertTrue("Device brands must not be empty", DeviceCatalog.BRANDS.isNotEmpty())
        assertTrue("Catalog must contain Samsung", DeviceCatalog.BRANDS.contains("Samsung"))
        assertTrue("Catalog must contain Apple (iPhone)", DeviceCatalog.BRANDS.contains("Apple (iPhone)"))
        assertTrue("Catalog must contain Xiaomi", DeviceCatalog.BRANDS.contains("Xiaomi"))
        assertTrue("Catalog must contain Redmi", DeviceCatalog.BRANDS.contains("Redmi"))
        assertTrue("Catalog must contain POCO", DeviceCatalog.BRANDS.contains("POCO"))
        assertTrue("Catalog must contain Infinix", DeviceCatalog.BRANDS.contains("Infinix"))
        assertTrue("Catalog must contain Tecno", DeviceCatalog.BRANDS.contains("Tecno"))

        val samsungModels = DeviceCatalog.getModelsForBrand("Samsung")
        assertTrue("Samsung models list must not be empty", samsungModels.isNotEmpty())
    }

    @Test
    fun testPlaystyleModifiersDifferences() {
        val testDevice = DeviceSpec(
            brand = "Infinix",
            model = "GT 20 Pro 5G",
            refreshRateHz = 144,
            screenInch = 6.78f,
            stockDpi = 393,
            recommendedSafeMaxDpi = 760,
            touchSamplingHz = 360,
            ramGb = 12
        )

        val speedConfig = SensitivityEngine.calculate(testDevice, Playstyle.SPEED_RUSHER)
        val sniperConfig = SensitivityEngine.calculate(testDevice, Playstyle.SNIPER_PRO)

        assertTrue(
            "Speed rusher general sensi should be higher than or equal to sniper",
            speedConfig.general >= sniperConfig.general
        )
        assertTrue(
            "Speed rusher sniper scope sensi should be higher than sniper mode",
            speedConfig.sniper > sniperConfig.sniper
        )
    }

    @Test
    fun testMaxSensi200Generation() {
        val testDevice = DeviceSpec(
            brand = "Xiaomi",
            model = "Redmi Note 13 Pro+ 5G",
            refreshRateHz = 120,
            screenInch = 6.67f,
            stockDpi = 392,
            recommendedSafeMaxDpi = 720,
            touchSamplingHz = 240,
            ramGb = 12
        )

        val maxConfig = SensitivityEngine.calculate(testDevice, Playstyle.MAX_SENSI_200)
        assertEquals(200, maxConfig.general)
        assertEquals(200, maxConfig.redDot)
        assertTrue("Sniper scope must always stay low (<= 75)", maxConfig.sniper <= 75)
        assertTrue("Free Look must always stay low (<= 85)", maxConfig.freeLook <= 85)
        assertTrue(maxConfig.scope2x >= 195)
        assertTrue(maxConfig.scope4x >= 190)
    }

    @Test
    fun testSniperAndFreeLookAlwaysStayLow() {
        val testDevice = DeviceSpec(
            brand = "Samsung",
            model = "Galaxy S24 Ultra",
            refreshRateHz = 120,
            screenInch = 6.8f,
            stockDpi = 411,
            recommendedSafeMaxDpi = 800,
            touchSamplingHz = 360,
            ramGb = 12
        )

        Playstyle.entries.forEach { style ->
            val config = SensitivityEngine.calculate(testDevice, style)
            assertTrue("Sniper scope must stay low (<= 75) for $style", config.sniper <= 75)
            assertTrue("Free Look must stay low (<= 85) for $style", config.freeLook <= 85)
        }
    }

    @Test
    fun testBrandAndHardwareSensitivityDifferences() {
        val iphone = DeviceSpec(
            brand = "Apple (iPhone)",
            model = "iPhone 15 Pro Max",
            refreshRateHz = 120,
            screenInch = 6.7f,
            stockDpi = 460,
            recommendedSafeMaxDpi = 800,
            touchSamplingHz = 240,
            ramGb = 8
        )

        val budget60Hz = DeviceSpec(
            brand = "Itel",
            model = "A70",
            refreshRateHz = 60,
            screenInch = 6.6f,
            stockDpi = 320,
            recommendedSafeMaxDpi = 580,
            touchSamplingHz = 120,
            ramGb = 4
        )

        val iphoneConfig = SensitivityEngine.calculate(iphone, Playstyle.PRECISION_HEADSHOT)
        val budgetConfig = SensitivityEngine.calculate(budget60Hz, Playstyle.PRECISION_HEADSHOT)

        // The 60Hz/120Hz touch device gets compensated with higher sensitivity values compared to iOS ultra-low latency
        assertTrue("Sensitivities should adapt to phone hardware differences", budgetConfig.general != iphoneConfig.general)
        assertTrue(iphoneConfig.general in 150..185)
        assertTrue(budgetConfig.general in 160..198)
    }

    @Test
    fun testIPhoneDoesNotUseDpiAndUsesAppleGlidingSpeed() {
        val iphone = DeviceSpec(
            brand = "Apple (iPhone)",
            model = "iPhone 16 Pro Max",
            refreshRateHz = 120,
            screenInch = 6.9f,
            stockDpi = 460,
            recommendedSafeMaxDpi = 800,
            touchSamplingHz = 240,
            ramGb = 8
        )

        val config = SensitivityEngine.calculate(iphone, Playstyle.PRECISION_HEADSHOT, useDpi = true)
        assertTrue("iPhone must have isAppleDevice true", config.isAppleDevice)
        assertFalse("iPhone must have useDpi false (no DPI on iOS)", config.useDpi)
        assertEquals(120, config.iosGlidingSpeed)
        assertTrue(config.tips.any { it.contains("iOS") || it.contains("iPhone") })
    }

    @Test
    fun testSensitivityCalculationWithAndWithoutDpi() {
        val samsung = DeviceSpec(
            brand = "Samsung",
            model = "Galaxy A55 5G",
            refreshRateHz = 120,
            screenInch = 6.6f,
            stockDpi = 390,
            recommendedSafeMaxDpi = 660,
            touchSamplingHz = 240,
            ramGb = 8
        )

        val withDpiConfig = SensitivityEngine.calculate(samsung, Playstyle.PRECISION_HEADSHOT, useDpi = true)
        val withoutDpiConfig = SensitivityEngine.calculate(samsung, Playstyle.PRECISION_HEADSHOT, useDpi = false)

        assertTrue("withDpi should use custom calculated DPI", withDpiConfig.useDpi)
        assertFalse("withoutDpi should have useDpi false", withoutDpiConfig.useDpi)
        assertEquals(samsung.stockDpi, withoutDpiConfig.dpi)
        assertTrue(withDpiConfig.dpi > samsung.stockDpi)
        assertTrue("Without DPI general sensitivity is compensated", withoutDpiConfig.general >= withDpiConfig.general)
    }
}
