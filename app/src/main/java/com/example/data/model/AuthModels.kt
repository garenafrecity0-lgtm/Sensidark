package com.example.data.model

enum class UserRole(
    val title: String,
    val badge: String,
    val description: String
) {
    CLIENT(
        title = "Mode Client",
        badge = "CLIENT GRATUIT",
        description = "Accès limité à la sensibilité de votre appareil auto-détecté."
    ),
    VIP(
        title = "Mode VIP Permanent",
        badge = "VIP ⭐",
        description = "Tous modèles mondiaux débloqués + IA Anos Bot (Café Noir) illimitée."
    ),
    ADMIN(
        title = "Mode Administrateur",
        badge = "ADMIN 👑",
        description = "Accès maître total aux réglages, diagnostics et modèles mondiaux."
    );

    val isVipOrAdmin: Boolean
        get() = this == VIP || this == ADMIN
}

object AuthConstants {
    const val ADMIN_SECRET_KEY = "com.dts"
    const val CLIENT_DEFAULT_KEY = "CLIENT-FREE-2025"
    const val WHATSAPP_NUMBER = "+23407071776576"
    const val WHATSAPP_URL = "https://wa.me/23407071776576?text=Bonjour,%20je%20souhaite%20acheter%20une%20cl%C3%A9%20d'acc%C3%A8s%20VIP%20permanent%20pour%20SensiFire%20Pro"
}
