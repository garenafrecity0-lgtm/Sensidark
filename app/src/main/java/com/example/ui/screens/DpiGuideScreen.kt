package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberGreen
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkObsidian
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.FireCrimson
import com.example.ui.theme.FireGold
import com.example.ui.theme.FireOrange
import com.example.ui.theme.HeadshotRed
import com.example.ui.theme.SafeGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WarningAmber

@Composable
fun DpiGuideScreen(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkObsidian)
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Title
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Guide VIP & Réglages Pro",
                    color = TextPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(FireGold.copy(alpha = 0.2f))
                        .border(1.dp, FireGold, RoundedCornerShape(6.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "VIP ⭐",
                        color = FireGold,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }
            Text(
                text = "Vitesse du pointeur, DPI, options développeurs & optimisations One-Tap",
                color = TextSecondary,
                fontSize = 12.sp
            )
        }

        // ==========================================
        // 🌟 SECTION VIP : VITESSE DU POINTEUR
        // ==========================================
        Card(
            colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    1.5.dp,
                    Brush.horizontalGradient(listOf(FireGold, FireOrange, CyberCyan)),
                    RoundedCornerShape(16.dp)
                )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Brush.linearGradient(listOf(FireGold, FireOrange))),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Speed,
                            contentDescription = null,
                            tint = DarkObsidian,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                text = "Vitesse du Pointeur (Pointer Speed)",
                                color = TextPrimary,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Black
                            )
                        }
                        Text(
                            text = "Le secret des joueurs professionnels Free Fire",
                            color = FireGold,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Explication : À quoi ça sert ?
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(DarkObsidian)
                        .border(1.dp, DarkBorder, RoundedCornerShape(10.dp))
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "🎯 À quoi sert la vitesse du pointeur ?",
                        color = CyberCyan,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "• **Élimine la latence tactile (Input Lag) :** Réduit le temps de réponse entre le moment où ton pouce touche l'écran et la réaction du jeu.\n" +
                               "• **Accélère le décollage One-Tap :** Permet à l'impulsion vers le haut (Flick) d'atteindre instantanément la tête de l'adversaire sans rester bloquée sur le torse.\n" +
                               "• **Fluidifie les rotations 360° :** Rend les mouvements ultra-rapides pour poser le mur de glace en une fraction de seconde.",
                        color = TextSecondary,
                        fontSize = 11.sp,
                        lineHeight = 16.sp
                    )
                }

                // Tuto pas à pas : Comment l'augmenter
                Text(
                    text = "⚙️ Comment l'augmenter sur votre téléphone :",
                    color = TextPrimary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )

                DpiStepItem(
                    stepNumber = "1",
                    title = "Ouvrir les Paramètres Android",
                    desc = "Allez dans Paramètres > Gestion globale (sur Samsung) ou Système / Paramètres supplémentaires (Xiaomi, Infinix, Tecno, Realme)."
                )

                DpiStepItem(
                    stepNumber = "2",
                    title = "Accéder à la section Saisie",
                    desc = "Appuyez sur « Langue et saisie » ou « Souris et pavé tactile »."
                )

                DpiStepItem(
                    stepNumber = "3",
                    title = "Régler la Vitesse du Pointeur au MAXIMUM",
                    desc = "Glissez le curseur « Vitesse du pointeur » à 100% (tout à droite vers Rapide). Réglez également la « Vitesse de défilement de la molette » au maximum."
                )
            }
        }

        // ==========================================
        // 👑 LES 5 RÉGLAGES SECRETS PRO & VIP
        // ==========================================
        Card(
            colors = CardDefaults.cardColors(containerColor = DarkSurface),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, FireGold.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Bolt,
                        contentDescription = null,
                        tint = FireGold,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "5 Réglages Essentiels VIP pour Headshots",
                        color = FireGold,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black
                    )
                }

                // Réglage 1: Échelles d'animation 0.5x
                SecretSettingItem(
                    number = "1",
                    title = "Échelles d'animation à 0.5x (Options Développeurs)",
                    desc = "Mettez « Échelle d'animation des fenêtres », « transitions » et « durée d'animation » sur 0.5x. Supprime tout délai d'affichage du GPU."
                )

                // Réglage 2: Fréquence d'écran 90Hz/120Hz
                SecretSettingItem(
                    number = "2",
                    title = "Forcer 90Hz / 120Hz (Paramètres > Écran)",
                    desc = "Activez la « Fluidité des mouvements : Élevée ». Un écran 120Hz divise l'input lag tactile par 2 par rapport au 60Hz standard."
                )

                // Réglage 3: Délai avant appui très court
                SecretSettingItem(
                    number = "3",
                    title = "Délai de pression continue très court (Accessibilité)",
                    desc = "Dans Accessibilité > Interaction et dextérité, réglez le « Délai avant appui maintenu » sur Très court (0.2s ou 0.3s)."
                )

                // Réglage 4: Taille mémoire tampon 4M/16M
                SecretSettingItem(
                    number = "4",
                    title = "Mémoire tampon du journaliseur à 4M/16M",
                    desc = "Dans les Options Développeurs, passez la mémoire tampon de 256K à 4 Mo ou 16 Mo pour éliminer les micro-saccades en plein duel 1v1."
                )

                // Réglage 5: Batterie non restreinte
                SecretSettingItem(
                    number = "5",
                    title = "Batterie « Non restreinte » pour Free Fire",
                    desc = "Allez dans Paramètres > Applications > Free Fire > Batterie > sélectionnez « Non restreinte » pour empêcher le bridage CPU."
                )
            }
        }

        // Warning Alert: Anti-Black Screen
        Card(
            colors = CardDefaults.cardColors(containerColor = DarkSurface),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, WarningAmber.copy(alpha = 0.5f), RoundedCornerShape(14.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(WarningAmber.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = WarningAmber,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "RÈGLE D'OR : SÉCURITÉ DPI",
                        color = WarningAmber,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        text = "Notez TOUJOURS votre DPI d'origine avant de le modifier. Ne dépassez jamais +250 au-dessus de votre DPI de base (limite maximale : 800 - 960) pour éviter que l'écran ne devienne noir.",
                        color = TextSecondary,
                        fontSize = 11.sp,
                        lineHeight = 16.sp
                    )
                }
            }
        }

        // Tutorial Steps DPI
        Card(
            colors = CardDefaults.cardColors(containerColor = DarkSurface),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, DarkBorder, RoundedCornerShape(14.dp))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "📱 Comment Activer et Changer le DPI (Largeur Minimale)",
                    color = CyberCyan,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                DpiStepItem(
                    stepNumber = "1",
                    title = "Débloquer les Options Développeur",
                    desc = "Allez dans Paramètres > À propos de l'appareil > Tapez 7 fois de suite sur « Numéro de build » (ou version MIUI/HyperOS pour Xiaomi)."
                )

                DpiStepItem(
                    stepNumber = "2",
                    title = "Ouvrir les Options Développeur",
                    desc = "Retournez dans Paramètres > Système (ou Paramètres supplémentaires) > Options pour les développeurs."
                )

                DpiStepItem(
                    stepNumber = "3",
                    title = "Trouver « Largeur minimale »",
                    desc = "Faites défiler jusqu'à la section « Tracé / Dessin » et cliquez sur « Largeur minimale » (Smallest width)."
                )

                DpiStepItem(
                    stepNumber = "4",
                    title = "Mémoriser puis appliquer",
                    desc = "Notez la valeur actuelle (ex: 392). Entrez ensuite la valeur DPI conseillée par Anos Sensi V2 (ex: 540)."
                )
            }
        }

        // Secret Drag Shot Techniques
        Card(
            colors = CardDefaults.cardColors(containerColor = DarkSurface),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, HeadshotRed.copy(alpha = 0.4f), RoundedCornerShape(14.dp))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "🎯 Techniques Secrètes de Levée de Mire (Drag Shot)",
                    color = HeadshotRed,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "1. Le Drag en « J inversé » (Corps-à-Corps)",
                        color = TextPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Avec le pompe M1887 ou MP40 à courte distance : abaissez votre pouce d'un millimètre puis montez en diagonale vers le haut. La balle se lock instantanément sur la tête.",
                        color = TextSecondary,
                        fontSize = 11.sp
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "2. La Règle du Point Blanc (Placement du Viseur)",
                        color = TextPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Ne placez JAMAIS le point blanc directement sur l'ennemi avant de tirer. Placez-le à côté de ses pieds ou de son épaule : au moment du tir, l'aim-assist se téléporte sur la boîte crânienne.",
                        color = TextSecondary,
                        fontSize = 11.sp
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "3. Pourquoi réduire la taille du bouton ?",
                        color = TextPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Un bouton à 40%-46% libère un espace de glisse supérieur sur l'écran. Votre pouce a toute la place pour monter jusqu'au sommet sans bloquer sur le bord.",
                        color = TextSecondary,
                        fontSize = 11.sp
                    )
                }
            }
        }

        // Graphics & FPS Guide
        Card(
            colors = CardDefaults.cardColors(containerColor = DarkSurface),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, DarkBorder, RoundedCornerShape(14.dp))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "⚡ Réglages Graphiques & Fluidité (Anti-Lag)",
                    color = SafeGreen,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "• FPS Élevé : TOUJOURS activé dans Free Fire. Même sur un écran 60Hz, cela réduit l'input lag tactile de plus de 40ms.",
                    color = TextSecondary,
                    fontSize = 11.sp
                )
                Text(
                    text = "• Ombres : Désactivez les ombres. Elles consomment jusqu'à 25% de la batterie et provoquent des micro-freezes pendant les duels intenses.",
                    color = TextSecondary,
                    fontSize = 11.sp
                )
                Text(
                    text = "• Graphismes : Mettez en « Doux » ou « Standard » si votre téléphone chauffe. Un jeu fluide à 60 FPS constants est 10 fois plus précis qu'un jeu en Ultra qui drop à 35 FPS.",
                    color = TextSecondary,
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Composable
private fun DpiStepItem(
    stepNumber: String,
    title: String,
    desc: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(FireOrange),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stepNumber,
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Black
            )
        }

        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = title,
                color = TextPrimary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = desc,
                color = TextSecondary,
                fontSize = 11.sp,
                lineHeight = 15.sp
            )
        }
    }
}

@Composable
private fun SecretSettingItem(
    number: String,
    title: String,
    desc: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(22.dp)
                .clip(CircleShape)
                .background(FireGold.copy(alpha = 0.2f))
                .border(1.dp, FireGold, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number,
                color = FireGold,
                fontSize = 11.sp,
                fontWeight = FontWeight.Black
            )
        }

        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = title,
                color = TextPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = desc,
                color = TextSecondary,
                fontSize = 11.sp,
                lineHeight = 15.sp
            )
        }
    }
}
