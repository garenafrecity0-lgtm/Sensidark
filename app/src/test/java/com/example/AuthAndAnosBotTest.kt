package com.example

import com.example.data.ai.AnosBotService
import com.example.data.ai.ChatMessage
import com.example.data.ai.MessageSender
import com.example.data.model.AuthConstants
import com.example.data.model.UserRole
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class AuthAndAnosBotTest {

    @Test
    fun testAdminKeyRecognition() {
        val key = AuthConstants.ADMIN_SECRET_KEY
        assertEquals("Zax11", key)

        val role = when {
            key.equals("Zax11", ignoreCase = true) || key == AuthConstants.ADMIN_SECRET_KEY -> UserRole.ADMIN
            key.startsWith("VIP") -> UserRole.VIP
            else -> UserRole.CLIENT
        }

        assertEquals(UserRole.ADMIN, role)
        assertTrue(role.isVipOrAdmin)
    }

    @Test
    fun testClientKeyRecognition() {
        val clientKey = AuthConstants.CLIENT_DEFAULT_KEY
        assertEquals("CLIENT-FREE-2025", clientKey)

        val role = when {
            clientKey == AuthConstants.ADMIN_SECRET_KEY -> UserRole.ADMIN
            clientKey.startsWith("VIP") -> UserRole.VIP
            else -> UserRole.CLIENT
        }

        assertEquals(UserRole.CLIENT, role)
        assertFalse(role.isVipOrAdmin)
    }

    @Test
    fun testVipKeyRecognition() {
        val vipKey = "VIP-PERMANENT-2025"

        val role = when {
            vipKey == AuthConstants.ADMIN_SECRET_KEY -> UserRole.ADMIN
            vipKey.startsWith("VIP") -> UserRole.VIP
            else -> UserRole.CLIENT
        }

        assertEquals(UserRole.VIP, role)
        assertTrue(role.isVipOrAdmin)
    }

    @Test
    fun testWhatsAppContactUrl() {
        assertTrue(AuthConstants.WHATSAPP_URL.contains("23407071776576"))
        assertEquals("+23407071776576", AuthConstants.WHATSAPP_NUMBER)
    }

    @Test
    fun testAnosBotOfflineResponse() = runBlocking {
        val responseM1887 = AnosBotService.sendMessage(
            history = emptyList(),
            userPrompt = "Quelle sensi pour M1887 One-Tap ?"
        )
        assertNotNull(responseM1887)
        assertTrue("Response should mention M1887 or One-Tap", responseM1887.contains("M1887") || responseM1887.contains("One-Tap") || responseM1887.contains("Sensibilité"))
        assertTrue("Response should mention Général or Bouton", responseM1887.contains("Général") || responseM1887.contains("Bouton"))

        val responseDpi = AnosBotService.sendMessage(
            history = emptyList(),
            userPrompt = "Quel DPI pour mon smartphone ?"
        )
        assertNotNull(responseDpi)
        assertTrue("Response should mention DPI", responseDpi.contains("DPI"))
    }

    @Test
    fun testMoreThan60BrandsAvailable() {
        assertTrue("DeviceCatalog must contain more than 60 brands", com.example.data.model.DeviceCatalog.BRANDS.size >= 60)
        assertTrue(com.example.data.model.DeviceCatalog.BRANDS.contains("Samsung"))
        assertTrue(com.example.data.model.DeviceCatalog.BRANDS.contains("Apple (iPhone)"))
        assertTrue(com.example.data.model.DeviceCatalog.BRANDS.contains("Infinix"))
        assertTrue(com.example.data.model.DeviceCatalog.BRANDS.contains("Tecno"))
        assertTrue(com.example.data.model.DeviceCatalog.BRANDS.contains("Asus (ROG Gaming)"))
        assertTrue(com.example.data.model.DeviceCatalog.BRANDS.contains("Nothing Phone"))
        assertTrue(com.example.data.model.DeviceCatalog.BRANDS.contains("Black Shark"))
    }

    @Test
    fun testGeneralSensitivityForPrecisionOneTapIsCalibrated() {
        val device = com.example.data.model.DeviceCatalog.getModelsForBrand("Samsung").first()
        val precisionStyle = com.example.data.model.Playstyle.PRECISION_HEADSHOT

        val config = com.example.data.generator.SensitivityEngine.calculate(device, precisionStyle)

        // General is dynamically calculated in responsive pro range (150 to 190) according to button size and DPI
        assertTrue("General sensitivity for One-Tap should be responsive (between 150 and 190)", config.general in 150..190)
        assertTrue("Red Dot should be crisp and responsive (between 160 and 195)", config.redDot in 160..195)
        assertTrue("Fire Button Size should be ergonomic (between 46 and 56%)", config.fireButtonSize in 46..56)
    }

    @Test
    fun testAnosBotResponsesForUserQuestions() = runBlocking {
        // Test question about general sensitivity for one-tap being too low
        val responseTooLow = AnosBotService.sendMessage(
            history = emptyList(),
            userPrompt = "T'es général pour les one tape est trop bas"
        )
        assertNotNull(responseTooLow)
        assertTrue(responseTooLow.contains("One-Tap") || responseTooLow.contains("Générale") || responseTooLow.contains("200"))

        // Test question about general sensitivity discussion
        val responsePrecisionGeneral = AnosBotService.sendMessage(
            history = emptyList(),
            userPrompt = "Tu sais que pour une précision le général ne devrait pas être trop élevé pas vrai"
        )
        assertNotNull(responsePrecisionGeneral)
        assertTrue(responsePrecisionGeneral.contains("raison") || responsePrecisionGeneral.contains("précision"))
        assertTrue(responsePrecisionGeneral.contains("Général") || responsePrecisionGeneral.contains("Point Rouge"))

        // Test question about Point Rouge sensitivity
        val responsePointRouge = AnosBotService.sendMessage(
            history = emptyList(),
            userPrompt = "je sais pas pourquoi la sensi du point rouge ne s'affiche pas"
        )
        assertNotNull(responsePointRouge)
        assertTrue(responsePointRouge.contains("Point Rouge") || responsePointRouge.contains("curseur"))

        // Test question about API key
        val responseApiKey = AnosBotService.sendMessage(
            history = emptyList(),
            userPrompt = "l'ai ne répond toujours pas comme une ai as tu oublié la clé api"
        )
        assertNotNull(responseApiKey)
        assertTrue(responseApiKey.contains("clé") || responseApiKey.contains("Gemini") || responseApiKey.contains("API"))

        // Test question about 200 general sensitivity
        val responseSensi200 = AnosBotService.sendMessage(
            history = emptyList(),
            userPrompt = "Le truc c'est que le général reste dans les 150+, ou 160+ ou 170+ a 190+ mais jamais a 200"
        )
        assertNotNull(responseSensi200)
        assertTrue(responseSensi200.contains("200") || responseSensi200.contains("Général"))

        // Test question about low free look and sniper scope
        val responseLowScopes = AnosBotService.sendMessage(
            history = emptyList(),
            userPrompt = "Le Free look et sniper scope doivent toujours être bas"
        )
        assertNotNull(responseLowScopes)
        assertTrue(responseLowScopes.contains("Sniper") || responseLowScopes.contains("Free Look") || responseLowScopes.contains("Regard Libre"))
    }

    @Test
    fun testDpiChangesOnSensitivityRegeneration() {
        val device = com.example.data.model.DeviceCatalog.getModelsForBrand("Samsung").first()
        val playstyle = com.example.data.model.Playstyle.PRECISION_HEADSHOT

        val config1 = com.example.data.generator.SensitivityEngine.calculate(device, playstyle, 1)
        val config2 = com.example.data.generator.SensitivityEngine.calculate(device, playstyle, 2)
        val config3 = com.example.data.generator.SensitivityEngine.calculate(device, playstyle, 3)

        // DPI must vary across configurations
        val dpis = setOf(config1.dpi, config2.dpi, config3.dpi)
        assertTrue("DPI must change between different sensitivity generations", dpis.size >= 2)
    }
}
