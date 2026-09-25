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
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkObsidian
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
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
            Text(
                text = "Guide DPI & Réglages Pro",
                color = TextPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Black
            )
            Text(
                text = "Optimisez votre téléphone pour 100% de Headshots sans bug",
                color = FireGold,
                fontSize = 12.sp
            )
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

        // Tutorial Steps
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
                    text = "📱 Comment Activer et Changer le DPI",
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
                    desc = "Notez la valeur actuelle (ex: 392). Entrez ensuite la valeur DPI conseillée par SensiFire Pro (ex: 540)."
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
                        text = "Un bouton à 42%-48% libère un espace de glisse de 30% supérieur sur l'écran. Votre pouce a toute la place pour monter jusqu'au sommet sans bloquer sur le bord.",
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
