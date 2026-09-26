package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DeviceUnknown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import com.example.data.model.AuthConstants
import com.example.data.model.UserRole
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.DeviceCatalog
import com.example.data.model.Playstyle
import com.example.ui.components.SensiSliderItem
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberGreen
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkBorderGlowing
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
import com.example.ui.viewmodel.SensiViewModel

@Composable
fun GeneratorScreen(
    viewModel: SensiViewModel,
    onNavigateToSimulator: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val detectedInfo = viewModel.detectedInfo
    val selectedBrand by viewModel.selectedBrand.collectAsState()
    val selectedModel by viewModel.selectedModel.collectAsState()
    val selectedPlaystyle by viewModel.selectedPlaystyle.collectAsState()
    val useDpi by viewModel.useDpi.collectAsState()
    val currentConfig by viewModel.currentConfig.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val userRole by viewModel.userRole.collectAsState()

    var showModelDropdown by remember { mutableStateOf(false) }
    var showCustomSaveDialog by remember { mutableStateOf(false) }
    var customPresetName by remember { mutableStateOf("") }
    var showVipUpgradeDialog by remember { mutableStateOf(false) }
    var vipKeyInput by remember { mutableStateOf("") }
    var vipKeyError by remember { mutableStateOf<String?>(null) }
    var showAdminCatalogDialog by remember { mutableStateOf(false) }
    var adminSearchQuery by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkObsidian)
            .verticalScroll(scrollState)
            .padding(bottom = 32.dp)
    ) {
        // Hero Header Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ff_banner_header_1790294588791),
                contentDescription = "Header Banner",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Dark gradient overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Black.copy(alpha = 0.35f),
                                DarkObsidian.copy(alpha = 0.85f),
                                DarkObsidian
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(FireOrange)
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "SENSI 0 - 200",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(HeadshotRed.copy(alpha = 0.3f))
                            .border(1.dp, HeadshotRed.copy(alpha = 0.6f), RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "FREE FIRE HEADSHOT",
                            color = HeadshotRed,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "ANOS SENSI V2",
                    color = TextPrimary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.5.sp
                )
                Text(
                    text = "Sensi 0-200 • DPI Optimal • Taille Bouton de Tir",
                    color = TextSecondary,
                    fontSize = 12.sp
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Role and VIP Status Banner
            if (!userRole.isVipOrAdmin) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, FireGold.copy(alpha = 0.5f), RoundedCornerShape(14.dp))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .clip(CircleShape)
                                    .background(FireGold.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = FireGold,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = "Mode Client : Appareil Détecté Uniquement",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Passez en VIP pour débloquer +68 marques & l'IA Anos Bot",
                                    color = TextSecondary,
                                    fontSize = 10.sp
                                )
                            }
                        }

                        Button(
                            onClick = { showVipUpgradeDialog = true },
                            colors = ButtonDefaults.buttonColors(containerColor = FireGold),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.testTag("btn_upgrade_vip_header")
                        ) {
                            Text("Passer VIP", color = Color.Black, fontSize = 11.sp, fontWeight = FontWeight.Black)
                        }
                    }
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(DarkSurfaceVariant)
                        .border(1.dp, CyberGreen.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = if (userRole == UserRole.ADMIN) FireCrimson else FireGold,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = if (userRole == UserRole.ADMIN) "Mode Administrateur Actif (Accès 68 Marques)" else "Mode VIP Actif (68 Marques Mondiales Débloquées)",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = { showAdminCatalogDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = if (userRole == UserRole.ADMIN) FireCrimson else FireGold),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                        modifier = Modifier.height(28.dp).testTag("btn_open_admin_catalog_badge")
                    ) {
                        Text(
                            text = "Catalogue",
                            color = if (userRole == UserRole.ADMIN) Color.White else Color.Black,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Section Admin Spécifique avec Barres d'Animation Haute Performance
            if (userRole == UserRole.ADMIN) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, FireCrimson.copy(alpha = 0.7f), RoundedCornerShape(16.dp))
                        .testTag("card_admin_animated_panel")
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Header Admin avec indicateur clignotant
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                AdminPulsingDot()
                                Text(
                                    text = "👑 CONSOLE ADMIN : SYSTÈME ACTIF",
                                    color = FireCrimson,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 0.5.sp
                                )
                            }
                            Text(
                                text = "${DeviceCatalog.BRANDS.size} marques débloquées",
                                color = TextMuted,
                                fontSize = 10.sp
                            )
                        }

                        // Barres d'Animation Equalizer Audio/Tactile en temps réel
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(DarkSurfaceVariant)
                                .border(1.dp, DarkBorder, RoundedCornerShape(10.dp))
                                .padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "📊 ANALYSEUR DE FLUX TACTILE & MOTEUR GRAPHIQUE",
                                    color = FireGold,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "LIVE OVERCLOCK",
                                    color = CyberGreen,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Black
                                )
                            }

                            // Les 18 barres d'égaliseur animées
                            AdminEqualizerAnimationBars()
                        }

                        // Barres d'Animation Télémétrie et Puissance Admin
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                text = "TÉLÉMÉTRIE & PERFORMANCES MATÉRIELLES",
                                color = TextSecondary,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )

                            AdminTelemetryAnimatedBar(
                                title = "⚡ Overclock Échantillonnage Tactile",
                                subtext = "480Hz Turbo Response • Latence 1.2ms",
                                baseProgress = 0.98f,
                                accentColor = FireOrange,
                                durationMs = 1200
                            )

                            AdminTelemetryAnimatedBar(
                                title = "🎯 Calibrage Vectoriel Aim-Lock & One-Tap",
                                subtext = "Compensation Micro-Drag • Anti-Décrochage",
                                baseProgress = 0.96f,
                                accentColor = HeadshotRed,
                                durationMs = 1400
                            )

                            AdminTelemetryAnimatedBar(
                                title = "🔥 Débit GPU & Rendu Trame (Bypass V-Sync)",
                                subtext = "120 FPS Verrouillé • Buffer Optimisé",
                                baseProgress = 0.94f,
                                accentColor = FireCrimson,
                                durationMs = 1600
                            )

                            AdminTelemetryAnimatedBar(
                                title = "🛡️ Stabilité Kernel & Réduction Input-Lag",
                                subtext = "Priorité Haute Thread Tactile OS",
                                baseProgress = 0.99f,
                                accentColor = CyberCyan,
                                durationMs = 1000
                            )
                        }

                        Button(
                            onClick = { showAdminCatalogDialog = true },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("btn_open_admin_60_brands"),
                            colors = ButtonDefaults.buttonColors(containerColor = FireCrimson),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(Icons.Default.PhoneAndroid, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                                Text("Explorer les 68 Marques et Modèles", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }

            // Auto-Detected Device Card
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, CyberCyan.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(CyberGreen)
                            )
                            Text(
                                text = "APPAREIL DÉTECTÉ EN DIRECT",
                                color = CyberGreen,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                        }

                        IconButton(
                            onClick = { viewModel.resetToAutoDetected() },
                            modifier = Modifier
                                .size(28.dp)
                                .testTag("btn_reset_detected")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Réinitialiser sur mon appareil",
                                tint = CyberCyan,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(CyberCyan.copy(alpha = 0.15f))
                                .border(1.dp, CyberCyan.copy(alpha = 0.3f), RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.PhoneAndroid,
                                contentDescription = null,
                                tint = CyberCyan,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "${detectedInfo.manufacturer} ${detectedInfo.model}",
                                color = TextPrimary,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Écran ${detectedInfo.screenInches}\" • ${detectedInfo.refreshRateHz}Hz • DPI ${detectedInfo.densityDpi} • ${detectedInfo.ramGb} Go RAM",
                                color = TextSecondary,
                                fontSize = 11.sp
                            )
                        }

                        Button(
                            onClick = { viewModel.resetToAutoDetected() },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = CyberCyan.copy(alpha = 0.2f)),
                            modifier = Modifier.testTag("btn_calibrate_detected")
                        ) {
                            Text(
                                text = "Calibrer",
                                color = CyberCyan,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Brand Selection (Horizontal Chip Scroll)
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "1. Choisir la Marque de l'appareil",
                            color = TextPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        if (!userRole.isVipOrAdmin) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(FireGold.copy(alpha = 0.2f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text("🔒 VIP", color = FireGold, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                    Text(
                        text = "${DeviceCatalog.BRANDS.size} marques au monde",
                        color = TextMuted,
                        fontSize = 11.sp
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    DeviceCatalog.BRANDS.forEach { brand ->
                        val isSelected = brand == selectedBrand
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(
                                    if (isSelected) FireOrange.copy(alpha = 0.25f)
                                    else DarkSurfaceVariant
                                )
                                .border(
                                    width = 1.dp,
                                    color = if (isSelected) FireOrange else DarkBorder,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .clickable {
                                    if (userRole.isVipOrAdmin) {
                                        viewModel.selectBrand(brand)
                                    } else {
                                        showVipUpgradeDialog = true
                                    }
                                }
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                                .testTag("chip_brand_${brand.lowercase().replace(" ", "_")}")
                        ) {
                            Text(
                                text = brand,
                                color = if (isSelected) FireOrange else TextSecondary,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }

            // Model Selection
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "2. Sélectionner le Modèle",
                        color = TextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    if (!userRole.isVipOrAdmin) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(FireGold.copy(alpha = 0.2f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text("🔒 VIP", color = FireGold, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                val availableModels = DeviceCatalog.getModelsForBrand(selectedBrand)

                Box(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(DarkSurfaceVariant)
                            .border(1.dp, DarkBorder, RoundedCornerShape(12.dp))
                            .clickable {
                                if (userRole.isVipOrAdmin) {
                                    showModelDropdown = true
                                } else {
                                    showVipUpgradeDialog = true
                                }
                            }
                            .padding(14.dp)
                            .testTag("dropdown_model_trigger"),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = selectedModel.model,
                                color = TextPrimary,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${selectedModel.refreshRateHz}Hz • Écran ${selectedModel.screenInch}\" • DPI défaut ${selectedModel.stockDpi}",
                                color = TextSecondary,
                                fontSize = 11.sp
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Changer modèle",
                            tint = FireOrange
                        )
                    }

                    DropdownMenu(
                        expanded = showModelDropdown,
                        onDismissRequest = { showModelDropdown = false },
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .background(DarkSurfaceElevated)
                            .border(1.dp, DarkBorder, RoundedCornerShape(8.dp))
                    ) {
                        availableModels.forEach { model ->
                            DropdownMenuItem(
                                text = {
                                    Column {
                                        Text(
                                            text = model.model,
                                            color = TextPrimary,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "Fréquence: ${model.refreshRateHz}Hz | Écran: ${model.screenInch}\" | Stock DPI: ${model.stockDpi}",
                                            color = TextSecondary,
                                            fontSize = 11.sp
                                        )
                                    }
                                },
                                onClick = {
                                    viewModel.selectModel(model)
                                    showModelDropdown = false
                                }
                            )
                        }
                    }
                }
            }

            // Playstyle Selection
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "3. Choisir le Style de Jeu",
                        color = TextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = selectedPlaystyle.badge,
                        color = FireGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Playstyle.entries.forEach { style ->
                        val isSelected = style == selectedPlaystyle
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) DarkSurfaceElevated else DarkSurface
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .width(200.dp)
                                .border(
                                    width = 1.dp,
                                    color = if (isSelected) FireGold else DarkBorder,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable { viewModel.selectPlaystyle(style) }
                                .testTag("style_${style.name.lowercase()}")
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = style.badge,
                                        color = if (isSelected) FireGold else TextMuted,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                    if (isSelected) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = null,
                                            tint = FireGold,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }

                                Text(
                                    text = style.title,
                                    color = if (isSelected) TextPrimary else TextSecondary,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = style.subtitle,
                                    color = TextMuted,
                                    fontSize = 10.sp,
                                    maxLines = 2
                                )
                            }
                        }
                    }
                }
            }

            // OPTION DPI & CONFIGURATION SYSTÈME (AVEC OU SANS DPI / APPLE iOS)
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "4. Option DPI & Optimisation Système",
                    color = TextPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                if (selectedModel.isApple) {
                    // Apple iOS Info Card
                    Card(
                        colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, CyberCyan.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text("🍎", fontSize = 24.sp)
                            Column {
                                Text(
                                    text = "iPhone / iOS : Pas d'option DPI (Spécifique à Android)",
                                    color = CyberCyan,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                                Text(
                                    text = "Apple utilise le Contrôle du sélectionneur (Glisse: 120, Mode Précis) et AssistiveTouch (100%). Sensi optimisée sans DPI !",
                                    color = TextSecondary,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                } else {
                    // Android Toggle: Avec DPI vs Sans DPI
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Avec DPI Button
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (useDpi) CyberCyan.copy(alpha = 0.2f) else DarkSurfaceVariant)
                                .border(1.dp, if (useDpi) CyberCyan else DarkBorder, RoundedCornerShape(12.dp))
                                .clickable { viewModel.setUseDpi(true) }
                                .padding(vertical = 10.dp, horizontal = 12.dp)
                                .testTag("btn_toggle_avec_dpi"),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "⚡ AVEC DPI",
                                    color = if (useDpi) CyberCyan else TextSecondary,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 12.sp
                                )
                                Text(
                                    text = "DPI Optimisé & Glisse Max",
                                    color = if (useDpi) TextPrimary else TextMuted,
                                    fontSize = 10.sp
                                )
                            }
                        }

                        // Sans DPI Button
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (!useDpi) SafeGreen.copy(alpha = 0.2f) else DarkSurfaceVariant)
                                .border(1.dp, if (!useDpi) SafeGreen else DarkBorder, RoundedCornerShape(12.dp))
                                .clickable { viewModel.setUseDpi(false) }
                                .padding(vertical = 10.dp, horizontal = 12.dp)
                                .testTag("btn_toggle_sans_dpi"),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "🛡️ SANS DPI",
                                    color = if (!useDpi) SafeGreen else TextSecondary,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 12.sp
                                )
                                Text(
                                    text = "DPI d'origine (${selectedModel.stockDpi})",
                                    color = if (!useDpi) TextPrimary else TextMuted,
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }
                }
            }

            // Headshot Estimation & Drag Technique Banner
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, HeadshotRed.copy(alpha = 0.35f), RoundedCornerShape(14.dp))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    listOf(HeadshotRed.copy(alpha = 0.4f), Color.Transparent)
                                )
                            )
                            .border(2.dp, HeadshotRed, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "${currentConfig.estimatedHeadshotRate}%",
                                color = HeadshotRed,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Black
                            )
                            Text(
                                text = "HEADSHOT",
                                color = TextMuted,
                                fontSize = 7.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Technique Drag Recommandée",
                            color = FireGold,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = currentConfig.dragTechnique,
                            color = TextPrimary,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            // BOUTONS GENERATION ET BOOST MAX 200
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { viewModel.generateNewSensitivity() },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp)
                        .testTag("btn_generate_new_sensi_dpi"),
                    colors = ButtonDefaults.buttonColors(containerColor = FireOrange),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "🎲 NOUVEAU DPI",
                            color = Color.White,
                            fontWeight = FontWeight.Black,
                            fontSize = 11.sp
                        )
                    }
                }

                Button(
                    onClick = { viewModel.setMaxSensi200() },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp)
                        .testTag("btn_boost_max_200"),
                    colors = ButtonDefaults.buttonColors(containerColor = HeadshotRed),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "🔥",
                            fontSize = 16.sp
                        )
                        Text(
                            text = "BOOST SENSI 200",
                            color = Color.White,
                            fontWeight = FontWeight.Black,
                            fontSize = 11.sp
                        )
                    }
                }
            }

            // TABLEAU RECAPITULATIF DIRECT DES SENSIBILITES (HUD VISUEL)
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, FireOrange.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                    .testTag("card_sensi_hud_summary")
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(Icons.Default.Tune, contentDescription = null, tint = FireGold, modifier = Modifier.size(16.dp))
                            Text(
                                text = "TABLEAU DES VALEURS EN JEU (0 - 200)",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 0.5.sp
                            )
                        }
                        Text(
                            text = if (currentConfig.isAppleDevice) {
                                "🍎 iOS Glisse: 120 • Bouton: ${currentConfig.fireButtonSize}%"
                            } else if (!currentConfig.useDpi) {
                                "🛡️ SANS DPI (Stock: ${currentConfig.stockDpi}) • Bouton: ${currentConfig.fireButtonSize}%"
                            } else {
                                "DPI : ${currentConfig.dpi} • Bouton: ${currentConfig.fireButtonSize}%"
                            },
                            color = if (currentConfig.isAppleDevice || currentConfig.useDpi) CyberCyan else SafeGreen,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // 6 Grid values
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // General
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (currentConfig.general == 200) HeadshotRed.copy(alpha = 0.2f) else DarkSurfaceVariant)
                                .border(1.dp, if (currentConfig.general == 200) HeadshotRed else DarkBorder, RoundedCornerShape(10.dp))
                                .padding(vertical = 8.dp, horizontal = 6.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("🎯 GÉNÉRAL", color = if (currentConfig.general == 200) HeadshotRed else TextSecondary, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                            Text("${currentConfig.general}", color = if (currentConfig.general == 200) HeadshotRed else FireOrange, fontSize = 20.sp, fontWeight = FontWeight.Black)
                            Text(if (currentConfig.general == 200) "🔥 MAX 200" else "Modéré / Précis", color = if (currentConfig.general == 200) HeadshotRed else TextMuted, fontSize = 8.sp, fontWeight = if (currentConfig.general == 200) FontWeight.Bold else FontWeight.Normal)
                        }

                        // Point Rouge (Highlighted!)
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(HeadshotRed.copy(alpha = 0.15f))
                                .border(1.5.dp, HeadshotRed, RoundedCornerShape(10.dp))
                                .padding(vertical = 8.dp, horizontal = 6.dp)
                                .testTag("badge_point_rouge_prominent"),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("🔴 PT ROUGE", color = HeadshotRed, fontSize = 9.sp, fontWeight = FontWeight.Black)
                            Text("${currentConfig.redDot}", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Black)
                            Text("One-Tap M1887", color = HeadshotRed, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                        }

                        // Mire 2X
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(DarkSurfaceVariant)
                                .border(1.dp, DarkBorder, RoundedCornerShape(10.dp))
                                .padding(vertical = 8.dp, horizontal = 6.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("🔍 MIRE 2X", color = TextSecondary, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                            Text("${currentConfig.scope2x}", color = CyberCyan, fontSize = 20.sp, fontWeight = FontWeight.Black)
                            Text("Mi-Distance", color = TextMuted, fontSize = 8.sp)
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Mire 4X
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(DarkSurfaceVariant)
                                .border(1.dp, DarkBorder, RoundedCornerShape(10.dp))
                                .padding(vertical = 8.dp, horizontal = 6.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("🔭 MIRE 4X", color = TextSecondary, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                            Text("${currentConfig.scope4x}", color = FireGold, fontSize = 20.sp, fontWeight = FontWeight.Black)
                            Text("Longue Portée", color = TextMuted, fontSize = 8.sp)
                        }

                        // Sniper
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(DarkSurfaceVariant)
                                .border(1.dp, DarkBorder, RoundedCornerShape(10.dp))
                                .padding(vertical = 8.dp, horizontal = 6.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("🎯 SNIPER", color = TextSecondary, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                            Text("${currentConfig.sniper}", color = Color(0xFFB388FF), fontSize = 20.sp, fontWeight = FontWeight.Black)
                            Text("AWM / M82B", color = TextMuted, fontSize = 8.sp)
                        }

                        // Regard Libre
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(DarkSurfaceVariant)
                                .border(1.dp, DarkBorder, RoundedCornerShape(10.dp))
                                .padding(vertical = 8.dp, horizontal = 6.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("👁️ REGARD", color = TextSecondary, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                            Text("${currentConfig.freeLook}", color = CyberGreen, fontSize = 20.sp, fontWeight = FontWeight.Black)
                            Text("Vue 360°", color = TextMuted, fontSize = 8.sp)
                        }
                    }
                }
            }

            // SENSITIVITIES (0 to 200) Sliders
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "RÉGLAGES SENSIBILITÉS (0 - 200)",
                        color = TextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.5.sp
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "DPI actuel : ${currentConfig.dpi}",
                            color = CyberCyan,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(
                            onClick = { viewModel.generateNewSensitivity() },
                            modifier = Modifier.size(28.dp).testTag("btn_shuffle_sensi_mini")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Changer sensi",
                                tint = FireOrange,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }

                SensiSliderItem(
                    title = "Général",
                    iconEmoji = "🎯",
                    description = "Caméra libre 360° et levée de mire rapide sans viser",
                    value = currentConfig.general,
                    accentColor = FireOrange,
                    onValueChange = { viewModel.updateGeneral(it) }
                )

                SensiSliderItem(
                    title = "Point Rouge",
                    iconEmoji = "🔴",
                    description = "Visée sans lunette (Crucial pour One-Tap M1887 & Desert Eagle)",
                    value = currentConfig.redDot,
                    accentColor = HeadshotRed,
                    onValueChange = { viewModel.updateRedDot(it) }
                )

                SensiSliderItem(
                    title = "Mire 2X",
                    iconEmoji = "🔍",
                    description = "Zoom optique x2 pour mi-distance",
                    value = currentConfig.scope2x,
                    accentColor = CyberCyan,
                    onValueChange = { viewModel.updateScope2x(it) }
                )

                SensiSliderItem(
                    title = "Mire 4X",
                    iconEmoji = "🔭",
                    description = "Zoom optique x4 pour longue distance",
                    value = currentConfig.scope4x,
                    accentColor = FireGold,
                    onValueChange = { viewModel.updateScope4x(it) }
                )

                SensiSliderItem(
                    title = "Viseur Sniper",
                    iconEmoji = "🎯",
                    description = "Lunette AWM, M82B, Kar98k (Visée millimétrée)",
                    value = currentConfig.sniper,
                    accentColor = Color(0xFFB388FF),
                    onValueChange = { viewModel.updateSniper(it) }
                )

                SensiSliderItem(
                    title = "Regard Libre",
                    iconEmoji = "👁️",
                    description = "Caméra regard libre sans modifier la direction de course",
                    value = currentConfig.freeLook,
                    accentColor = CyberGreen,
                    onValueChange = { viewModel.updateFreeLook(it) }
                )
            }

            // DPI & FIRE BUTTON Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // DPI Card (Adaptive for iOS / Android Sans DPI / Android Avec DPI)
                Card(
                    colors = CardDefaults.cardColors(containerColor = DarkSurface),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .weight(1f)
                        .border(
                            1.dp,
                            if (currentConfig.isAppleDevice) CyberCyan.copy(alpha = 0.5f) else if (!currentConfig.useDpi) SafeGreen.copy(alpha = 0.5f) else DarkBorder,
                            RoundedCornerShape(14.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = if (currentConfig.isAppleDevice) "🍎 RÉGLAGE iOS" else if (!currentConfig.useDpi) "🛡️ SANS MODIF DPI" else "⚙️ DPI CONSEILLÉ",
                            color = if (currentConfig.isAppleDevice) CyberCyan else if (!currentConfig.useDpi) SafeGreen else CyberCyan,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Row(
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = if (currentConfig.isAppleDevice) "120" else "${currentConfig.dpi}",
                                color = TextPrimary,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Black
                            )
                            Text(
                                text = if (currentConfig.isAppleDevice) "Glisse" else "DPI",
                                color = TextSecondary,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(bottom = 3.dp)
                            )
                        }
                        Text(
                            text = if (currentConfig.isAppleDevice) "Contrôle Sélectionneur" else if (!currentConfig.useDpi) "DPI Stock : ${currentConfig.stockDpi}" else "Stock : ${currentConfig.stockDpi} DPI",
                            color = TextMuted,
                            fontSize = 10.sp
                        )
                        Text(
                            text = if (currentConfig.isAppleDevice) "Suivi AssistiveTouch: 100%" else if (!currentConfig.useDpi) "Sensi One-Tap compensée" else "Limite sûre : ${selectedModel.recommendedSafeMaxDpi}",
                            color = SafeGreen,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                // Fire Button Size Card
                Card(
                    colors = CardDefaults.cardColors(containerColor = DarkSurface),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, FireOrange.copy(alpha = 0.4f), RoundedCornerShape(14.dp))
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "🔘 BOUTON DE TIR",
                            color = FireOrange,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Row(
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(
                                text = "${currentConfig.fireButtonSize}",
                                color = TextPrimary,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Black
                            )
                            Text(
                                text = "%",
                                color = FireOrange,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 3.dp)
                            )
                        }
                        Text(
                            text = "Placement : Bas-Droite",
                            color = TextSecondary,
                            fontSize = 10.sp
                        )
                        Text(
                            text = "Course de tir optimale",
                            color = SafeGreen,
                            fontSize = 10.sp
                        )
                    }
                }
            }

            // Interactive Simulator CTA Card
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, FireOrange.copy(alpha = 0.6f), RoundedCornerShape(14.dp))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "🎮 SIMULATEUR DE TIR HUD",
                                color = FireGold,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black
                            )
                        }
                        Text(
                            text = "Ajustez la taille du bouton et entraînez votre Drag Shot en direct !",
                            color = TextSecondary,
                            fontSize = 11.sp
                        )
                    }

                    Button(
                        onClick = onNavigateToSimulator,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = FireOrange),
                        modifier = Modifier.testTag("btn_open_simulator")
                    ) {
                        Text(
                            text = "Tester",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            // Action Buttons (Copy & Save)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = { viewModel.copyConfigurationToClipboard(context) },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("btn_copy_config"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = FireOrange)
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copier",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Copier Tout",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }

                OutlinedButton(
                    onClick = { showCustomSaveDialog = true },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("btn_save_preset"),
                    shape = RoundedCornerShape(12.dp),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = Brush.horizontalGradient(listOf(FireGold, CyberCyan))
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.BookmarkBorder,
                        contentDescription = "Sauvegarder",
                        tint = FireGold,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Sauvegarder",
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }

            // Pro Tips Card
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, DarkBorder, RoundedCornerShape(14.dp))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "💡 Conseils Pro pour ${selectedModel.model}",
                        color = FireGold,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )

                    currentConfig.tips.forEach { tip ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(text = "•", color = FireOrange, fontWeight = FontWeight.Bold)
                            Text(
                                text = tip,
                                color = TextSecondary,
                                fontSize = 11.sp,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }

    // Save Preset Dialog
    if (showCustomSaveDialog) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showCustomSaveDialog = false },
            title = {
                Text(
                    text = "Sauvegarder le Preset",
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Donnez un nom à cette configuration pour la retrouver dans vos favoris :",
                        color = TextSecondary,
                        fontSize = 12.sp
                    )
                    OutlinedTextField(
                        value = customPresetName,
                        onValueChange = { customPresetName = it },
                        placeholder = { Text("${selectedModel.model} - ${selectedPlaystyle.title}") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary,
                            focusedBorderColor = FireOrange,
                            unfocusedBorderColor = DarkBorder
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.saveCurrentPreset(customPresetName.ifBlank { null })
                        showCustomSaveDialog = false
                        customPresetName = ""
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = FireOrange)
                ) {
                    Text("Enregistrer")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showCustomSaveDialog = false }) {
                    Text("Annuler", color = TextSecondary)
                }
            },
            containerColor = DarkSurfaceElevated
        )
    }

    if (showVipUpgradeDialog) {
        AlertDialog(
            onDismissRequest = { showVipUpgradeDialog = false },
            containerColor = DarkSurfaceElevated,
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = FireGold
                    )
                    Text("Mode VIP Requis", color = Color.White, fontWeight = FontWeight.Bold)
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Text(
                        text = "En Mode Client gratuit, la sensibilité est automatiquement calibrée pour votre smartphone détecté (${detectedInfo.matchedSpec.brand} ${detectedInfo.matchedSpec.model}).",
                        color = TextSecondary,
                        fontSize = 13.sp
                    )
                    Text(
                        text = "Passez en Mode VIP pour débloquer plus de 20 marques et des centaines de smartphones au monde, ainsi que l'IA Anos Bot (Gemini) !",
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Button(
                        onClick = {
                            try {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(AuthConstants.WHATSAPP_URL))
                                context.startActivity(intent)
                            } catch (_: Exception) {
                                Toast.makeText(context, "Contactez WhatsApp au ${AuthConstants.WHATSAPP_NUMBER}", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = CyberGreen),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Outlined.Chat, contentDescription = null, tint = Color.Black)
                            Text("Acheter Clé VIP Permanent (WhatsApp)", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("Ou entrez votre clé VIP :", color = TextMuted, fontSize = 11.sp)
                        OutlinedTextField(
                            value = vipKeyInput,
                            onValueChange = {
                                vipKeyInput = it
                                vipKeyError = null
                            },
                            placeholder = { Text("Clé VIP ou Admin...", color = TextMuted) },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = FireGold,
                                unfocusedBorderColor = DarkBorder
                            ),
                            modifier = Modifier.fillMaxWidth().testTag("input_vip_upgrade_key")
                        )
                        AnimatedVisibility(visible = vipKeyError != null) {
                            Text(vipKeyError ?: "", color = Color(0xFFFF5252), fontSize = 11.sp)
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val success = viewModel.upgradeToVip(vipKeyInput)
                        if (success) {
                            showVipUpgradeDialog = false
                            Toast.makeText(context, "Mode VIP Débloqué avec succès ! ⭐", Toast.LENGTH_SHORT).show()
                        } else {
                            vipKeyError = "Clé invalide. Contactez WhatsApp pour en acheter une."
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = FireGold),
                    modifier = Modifier.testTag("btn_confirm_vip_upgrade")
                ) {
                    Text("Valider", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showVipUpgradeDialog = false }) {
                    Text("Fermer", color = TextMuted)
                }
            }
        )
    }

    // Modal Explorateur Admin des 68 marques et modèles mondiaux
    if (showAdminCatalogDialog) {
        val filteredBrands = if (adminSearchQuery.isBlank()) {
            DeviceCatalog.BRANDS
        } else {
            DeviceCatalog.BRANDS.filter { brand ->
                brand.contains(adminSearchQuery, ignoreCase = true) ||
                        DeviceCatalog.getModelsForBrand(brand).any { it.model.contains(adminSearchQuery, ignoreCase = true) }
            }
        }

        AlertDialog(
            onDismissRequest = { showAdminCatalogDialog = false },
            containerColor = DarkSurfaceElevated,
            title = {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.PhoneAndroid, contentDescription = null, tint = FireCrimson)
                        Text(
                            text = "Catalogue Mondial (+68 Marques)",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        )
                    }
                    Text(
                        text = "${DeviceCatalog.BRANDS.size} marques de smartphones répertoriées",
                        color = TextSecondary,
                        fontSize = 12.sp
                    )
                }
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(420.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = adminSearchQuery,
                        onValueChange = { adminSearchQuery = it },
                        placeholder = { Text("Rechercher marque ou modèle (ex: Infinix, S24, Pova)...", fontSize = 12.sp, color = TextMuted) },
                        singleLine = true,
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = TextMuted) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = FireCrimson,
                            unfocusedBorderColor = DarkBorder,
                            focusedContainerColor = DarkSurfaceVariant,
                            unfocusedContainerColor = DarkSurfaceVariant
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Brands & Models List
                    androidx.compose.foundation.lazy.LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(filteredBrands) { brand ->
                            val models = DeviceCatalog.getModelsForBrand(brand)
                            Card(
                                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(1.dp, if (brand == selectedBrand) FireOrange else DarkBorder, RoundedCornerShape(12.dp))
                            ) {
                                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = brand,
                                            color = if (brand == selectedBrand) FireOrange else TextPrimary,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "${models.size} modèles",
                                            color = TextMuted,
                                            fontSize = 11.sp
                                        )
                                    }

                                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                        models.forEach { spec ->
                                            val isCurrentModel = spec.model == selectedModel.model
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clip(RoundedCornerShape(8.dp))
                                                    .background(if (isCurrentModel) FireOrange.copy(alpha = 0.2f) else DarkSurfaceVariant)
                                                    .clickable {
                                                        viewModel.selectBrand(brand)
                                                        viewModel.selectModel(spec)
                                                        showAdminCatalogDialog = false
                                                        Toast.makeText(context, "${spec.brand} ${spec.model} sélectionné et calibré !", Toast.LENGTH_SHORT).show()
                                                    }
                                                    .padding(horizontal = 10.dp, vertical = 6.dp),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Column(modifier = Modifier.weight(1f)) {
                                                    Text(
                                                        text = spec.model,
                                                        color = if (isCurrentModel) FireOrange else Color.White,
                                                        fontSize = 12.sp,
                                                        fontWeight = if (isCurrentModel) FontWeight.Bold else FontWeight.Normal
                                                    )
                                                    Text(
                                                        text = "${spec.refreshRateHz}Hz • Écran ${spec.screenInch}\" • DPI défaut ${spec.stockDpi}",
                                                        color = TextMuted,
                                                        fontSize = 10.sp
                                                    )
                                                }

                                                Text(
                                                    text = if (isCurrentModel) "ACTIF" else "Choisir",
                                                    color = if (isCurrentModel) FireOrange else CyberCyan,
                                                    fontSize = 11.sp,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showAdminCatalogDialog = false }) {
                    Text("Fermer", color = TextPrimary)
                }
            }
        )
    }
}

@Composable
fun AdminPulsingDot(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse_dot")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_dot_alpha"
    )

    Box(
        modifier = modifier
            .size(10.dp)
            .clip(CircleShape)
            .background(FireCrimson.copy(alpha = alpha)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(5.dp)
                .clip(CircleShape)
                .background(Color.White)
        )
    }
}

@Composable
fun AdminEqualizerAnimationBars(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "equalizer_bars")
    val barDurations = listOf(350, 500, 280, 420, 600, 310, 480, 260, 520, 380, 440, 290, 560, 340, 460, 320, 510, 390)
    val animatedFractions = barDurations.mapIndexed { index, duration ->
        transition.animateFloat(
            initialValue = 0.15f + ((index % 4) * 0.05f),
            targetValue = 0.85f + ((index % 3) * 0.07f),
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = duration, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "bar_height_$index"
        )
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(38.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        animatedFractions.forEachIndexed { index, fractionState ->
            val heightFraction = fractionState.value.coerceIn(0.1f, 1f)
            val barColor = when (index % 4) {
                0 -> FireCrimson
                1 -> FireOrange
                2 -> FireGold
                else -> CyberCyan
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 1.5.dp)
                    .fillMaxHeight(heightFraction)
                    .clip(RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp))
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                barColor,
                                barColor.copy(alpha = 0.35f)
                            )
                        )
                    )
            )
        }
    }
}

@Composable
fun AdminTelemetryAnimatedBar(
    title: String,
    subtext: String,
    baseProgress: Float,
    accentColor: Color,
    durationMs: Int,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "telemetry_progress_$title")
    val animatedProgressOffset by infiniteTransition.animateFloat(
        initialValue = -0.03f,
        targetValue = 0.02f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMs, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "telemetry_fluctuation"
    )

    val currentProgress = (baseProgress + animatedProgressOffset).coerceIn(0.85f, 1.0f)
    val percentageInt = (currentProgress * 100).toInt()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(DarkSurface)
            .border(1.dp, DarkBorder, RoundedCornerShape(10.dp))
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = subtext,
                    color = TextMuted,
                    fontSize = 9.sp
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(accentColor.copy(alpha = 0.2f))
                    .border(1.dp, accentColor.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "$percentageInt%",
                    color = accentColor,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black
                )
            }
        }

        // Animated Bar track
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.Black.copy(alpha = 0.5f))
        ) {
            // Fill
            Box(
                modifier = Modifier
                    .fillMaxWidth(currentProgress)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                accentColor.copy(alpha = 0.6f),
                                accentColor,
                                Color.White.copy(alpha = 0.8f)
                            )
                        )
                    )
            )
        }
    }
}
