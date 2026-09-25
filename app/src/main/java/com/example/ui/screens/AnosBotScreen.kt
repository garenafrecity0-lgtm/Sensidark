package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.ai.AnosBotService
import com.example.data.ai.ChatMessage
import com.example.data.ai.MessageSender
import com.example.data.model.AuthConstants
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberGreen
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkObsidian
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.FireGold
import com.example.ui.theme.FireOrange
import com.example.ui.theme.HeadshotRed
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.SensiViewModel

@Composable
fun AnosBotScreen(
    viewModel: SensiViewModel,
    modifier: Modifier = Modifier
) {
    val userRole by viewModel.userRole.collectAsState()

    if (!userRole.isVipOrAdmin) {
        AnosBotLockedView(viewModel = viewModel, modifier = modifier)
    } else {
        AnosBotChatView(viewModel = viewModel, modifier = modifier)
    }
}

@Composable
fun AnosBotLockedView(
    viewModel: SensiViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showVipDialog by remember { mutableStateOf(false) }
    var vipKeyInput by remember { mutableStateOf("") }
    var vipKeyError by remember { mutableStateOf<String?>(null) }

    fun openWhatsApp() {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(AuthConstants.WHATSAPP_URL))
            context.startActivity(intent)
        } catch (_: Exception) {
            Toast.makeText(context, "Contactez le support au ${AuthConstants.WHATSAPP_NUMBER}", Toast.LENGTH_LONG).show()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkObsidian)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // Anos Bot Avatar
        Box(
            modifier = Modifier
                .size(125.dp)
                .clip(CircleShape)
                .border(3.dp, FireGold, CircleShape)
                .border(6.dp, DarkBorder, CircleShape)
        ) {
            Image(
                painter = painterResource(id = R.drawable.anos_bot_avatar_1790295810303),
                contentDescription = "Anos Bot IA",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        // Status badge
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(DarkSurfaceVariant)
                .border(1.dp, DarkBorder, RoundedCornerShape(20.dp))
                .padding(horizontal = 14.dp, vertical = 6.dp)
        ) {
            Icon(
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = null,
                tint = FireGold,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = "ANOS BOT • ASSISTANT IA GAMING",
                color = FireGold,
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.sp
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Intelligence Artificielle Free Fire",
                color = TextPrimary,
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Réservé exclusivement aux membres VIP Permanent",
                color = FireGold,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = DarkSurface),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, DarkBorder, RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = FireGold,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Capacités de l'IA Anos Bot",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = "Anos Bot fonctionne comme ChatGPT et Gemini : une vraie IA conversationnelle entraînée sur toutes les spécificités de Free Fire, les calibres d'armes, les taux de rafraîchissement d'écrans et les sensibilités One-Tap.",
                    color = TextSecondary,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = FireGold, modifier = Modifier.size(16.dp))
                        Text("Conversation IA naturelle et intelligente en temps réel", color = TextPrimary, fontSize = 12.sp)
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = FireGold, modifier = Modifier.size(16.dp))
                        Text("Calculs précis de sensibilité (0 à 200) et de DPI", color = TextPrimary, fontSize = 12.sp)
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = FireGold, modifier = Modifier.size(16.dp))
                        Text("Conseils tactiques personnalisés par arme et smartphone", color = TextPrimary, fontSize = 12.sp)
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = FireGold, modifier = Modifier.size(16.dp))
                        Text("Déblocage de tous les smartphones du monde (+68 marques)", color = TextPrimary, fontSize = 12.sp)
                    }
                }
            }
        }

        // Action Buttons
        Button(
            onClick = { openWhatsApp() },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("btn_buy_vip_anos_whatsapp"),
            colors = ButtonDefaults.buttonColors(containerColor = CyberGreen),
            shape = RoundedCornerShape(14.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Chat,
                    contentDescription = null,
                    tint = Color.Black
                )
                Text(
                    text = "Acheter Clé VIP Permanent (WhatsApp)",
                    color = Color.Black,
                    fontWeight = FontWeight.Black,
                    fontSize = 14.sp
                )
            }
        }

        TextButton(
            onClick = { showVipDialog = true },
            modifier = Modifier.testTag("btn_enter_vip_key_dialog")
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(Icons.Default.Key, contentDescription = null, tint = FireGold, modifier = Modifier.size(18.dp))
                Text(
                    text = "J'ai déjà acheté une clé VIP (Déverrouiller)",
                    color = FireGold,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    if (showVipDialog) {
        AlertDialog(
            onDismissRequest = { showVipDialog = false },
            containerColor = DarkSurfaceElevated,
            title = {
                Text(
                    text = "Activer votre Accès VIP",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "Entrez votre clé d'accès VIP reçue sur WhatsApp pour déverrouiller immédiatement Anos Bot et toutes les marques :",
                        color = TextSecondary,
                        fontSize = 12.sp
                    )

                    OutlinedTextField(
                        value = vipKeyInput,
                        onValueChange = {
                            vipKeyInput = it
                            vipKeyError = null
                        },
                        placeholder = { Text("Clé VIP...", color = TextMuted) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = FireGold,
                            unfocusedBorderColor = DarkBorder
                        ),
                        modifier = Modifier.fillMaxWidth().testTag("input_vip_modal_key")
                    )

                    AnimatedVisibility(visible = vipKeyError != null) {
                        Text(
                            text = vipKeyError ?: "",
                            color = Color(0xFFFF5252),
                            fontSize = 12.sp
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val success = viewModel.upgradeToVip(vipKeyInput)
                        if (success) {
                            showVipDialog = false
                            Toast.makeText(context, "Accès VIP activé avec succès !", Toast.LENGTH_SHORT).show()
                        } else {
                            vipKeyError = "Clé VIP invalide. Contactez WhatsApp si besoin."
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = FireGold),
                    modifier = Modifier.testTag("btn_confirm_vip_key")
                ) {
                    Text("Valider", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showVipDialog = false }) {
                    Text("Annuler", color = TextMuted)
                }
            }
        )
    }
}

@Composable
fun AnosBotChatView(
    viewModel: SensiViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val messages by viewModel.chatMessages.collectAsState()
    val isThinking by viewModel.isAiThinking.collectAsState()
    val userRole by viewModel.userRole.collectAsState()
    val selectedModel by viewModel.selectedModel.collectAsState()
    val geminiApiKey by viewModel.geminiApiKey.collectAsState()

    var inputText by remember { mutableStateOf("") }
    var showApiKeyDialog by remember { mutableStateOf(false) }
    var customApiKeyInput by remember(geminiApiKey) { mutableStateOf(geminiApiKey) }

    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    val hasActiveApiKey = AnosBotService.hasValidApiKey()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkObsidian)
    ) {
        // Top App Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkSurface)
                .border(1.dp, DarkBorder)
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, if (hasActiveApiKey) CyberGreen else FireOrange, CircleShape)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.anos_bot_avatar_1790295810303),
                        contentDescription = "Anos Bot Avatar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "Anos Bot",
                            color = TextPrimary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(if (hasActiveApiKey) CyberGreen.copy(alpha = 0.2f) else FireOrange.copy(alpha = 0.2f))
                                .padding(horizontal = 6.dp, vertical = 1.dp)
                        ) {
                            Text(
                                text = if (hasActiveApiKey) "GEMINI 3.5 FLASH" else "IA CONVERSATIONNELLE",
                                color = if (hasActiveApiKey) CyberGreen else FireOrange,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(if (hasActiveApiKey) CyberGreen else FireGold)
                        )
                        Text(
                            text = if (hasActiveApiKey) "En direct • Google Gemini API active" else "Moteur IA actif • Clé API configurable",
                            color = if (hasActiveApiKey) CyberGreen else FireGold,
                            fontSize = 11.sp
                        )
                    }
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
                Button(
                    onClick = { showApiKeyDialog = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (hasActiveApiKey) CyberGreen.copy(alpha = 0.2f) else FireGold.copy(alpha = 0.2f)
                    ),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                    modifier = Modifier.height(32.dp).testTag("btn_api_key_settings")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Key,
                            contentDescription = "Clé API",
                            tint = if (hasActiveApiKey) CyberGreen else FireGold,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = if (hasActiveApiKey) "API Active" else "Clé API",
                            color = if (hasActiveApiKey) CyberGreen else FireGold,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                IconButton(
                    onClick = { viewModel.clearAnosBotChat() },
                    modifier = Modifier.testTag("btn_clear_chat")
                ) {
                    Icon(
                        imageVector = Icons.Default.CleaningServices,
                        contentDescription = "Effacer l'historique",
                        tint = TextSecondary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        // Active Device Context Banner
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkSurfaceVariant)
                .padding(horizontal = 14.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "📱 Smartphone calibré : ${selectedModel.brand} ${selectedModel.model} (${selectedModel.refreshRateHz}Hz)",
                color = TextSecondary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = userRole.badge,
                color = FireGold,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Quick Suggestion Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val suggestions = listOf(
                "🎯 Pourquoi Général modéré ?",
                "🔴 Où est le Point Rouge ?",
                "🔑 Clé API Gemini",
                "⚡ Sensi One-Tap M1887",
                "🔥 Viseur qui dépasse la tête",
                "📐 Quel DPI pour mon écran ?",
                "🔘 Quelle taille pour mon bouton ?"
            )

            suggestions.forEach { prompt ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(DarkSurfaceVariant)
                        .border(1.dp, DarkBorder, RoundedCornerShape(12.dp))
                        .clickable {
                            viewModel.sendMessageToAnosBot(prompt)
                        }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = prompt,
                        color = TextPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // Messages List
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            items(messages, key = { it.id }) { msg ->
                ChatBubble(message = msg, onCopy = {
                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    clipboard.setPrimaryClip(ClipData.newPlainText("Anos Bot", msg.text))
                    Toast.makeText(context, "Message copié !", Toast.LENGTH_SHORT).show()
                })
            }

            if (isThinking) {
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(DarkSurface)
                            .border(1.dp, DarkBorder, RoundedCornerShape(12.dp))
                            .padding(horizontal = 14.dp, vertical = 10.dp)
                    ) {
                        CircularProgressIndicator(
                            color = FireOrange,
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "Anos Bot réfléchit et prépare sa réponse...",
                            color = TextSecondary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        // Input Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkSurface)
                .border(1.dp, DarkBorder)
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                placeholder = {
                    Text(
                        "Pose ta question à Anos Bot (ex: sensi M1887, DPI)...",
                        color = TextMuted,
                        fontSize = 12.sp
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .testTag("input_anos_bot_message"),
                maxLines = 3,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(
                    onSend = {
                        if (inputText.isNotBlank() && !isThinking) {
                            viewModel.sendMessageToAnosBot(inputText)
                            inputText = ""
                        }
                    }
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = FireOrange,
                    unfocusedBorderColor = DarkBorder,
                    focusedContainerColor = DarkSurfaceVariant,
                    unfocusedContainerColor = DarkSurfaceVariant
                ),
                shape = RoundedCornerShape(16.dp)
            )

            IconButton(
                onClick = {
                    if (inputText.isNotBlank() && !isThinking) {
                        viewModel.sendMessageToAnosBot(inputText)
                        inputText = ""
                    }
                },
                enabled = inputText.isNotBlank() && !isThinking,
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(if (inputText.isNotBlank() && !isThinking) FireOrange else DarkBorder)
                    .testTag("btn_send_anos_bot")
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Envoyer",
                    tint = if (inputText.isNotBlank() && !isThinking) Color.White else TextMuted,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }

    if (showApiKeyDialog) {
        AlertDialog(
            onDismissRequest = { showApiKeyDialog = false },
            containerColor = DarkSurfaceElevated,
            title = {
                Text("Clé API Gemini (Optionnel)", color = TextPrimary, fontWeight = FontWeight.Bold)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        "Vous pouvez insérer votre propre clé API Google AI Studio pour un flux direct avec le modèle Gemini 3.5 Flash :",
                        color = TextSecondary,
                        fontSize = 12.sp
                    )
                    OutlinedTextField(
                        value = customApiKeyInput,
                        onValueChange = { customApiKeyInput = it },
                        placeholder = { Text("AIzaSy...", color = TextMuted) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = FireOrange,
                            unfocusedBorderColor = DarkBorder
                        ),
                        modifier = Modifier.fillMaxWidth().testTag("input_custom_gemini_key")
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.saveCustomGeminiApiKey(customApiKeyInput)
                        showApiKeyDialog = false
                        Toast.makeText(context, "Clé API Gemini enregistrée avec succès !", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = FireOrange)
                ) {
                    Text("Enregistrer")
                }
            },
            dismissButton = {
                TextButton(onClick = { showApiKeyDialog = false }) {
                    Text("Fermer", color = TextMuted)
                }
            }
        )
    }
}

@Composable
fun ChatBubble(
    message: ChatMessage,
    onCopy: () -> Unit
) {
    val isBot = message.sender == MessageSender.ANOS_BOT

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isBot) Arrangement.Start else Arrangement.End
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (isBot) 4.dp else 16.dp,
                        bottomEnd = if (isBot) 16.dp else 4.dp
                    )
                )
                .background(if (isBot) DarkSurface else FireOrange)
                .border(
                    1.dp,
                    if (isBot) DarkBorder else Color.Transparent,
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (isBot) 4.dp else 16.dp,
                        bottomEnd = if (isBot) 16.dp else 4.dp
                    )
                )
                .padding(14.dp)
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
                    Text(
                        text = if (isBot) "🤖 Anos Bot" else "👤 Vous",
                        color = if (isBot) FireGold else Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    if (isBot) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(FireOrange.copy(alpha = 0.2f))
                                .padding(horizontal = 4.dp, vertical = 1.dp)
                        ) {
                            Text("IA", color = FireOrange, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                if (isBot) {
                    IconButton(
                        onClick = onCopy,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copier",
                            tint = TextMuted,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = message.text,
                color = if (isBot) TextPrimary else Color.White,
                fontSize = 13.sp,
                lineHeight = 19.sp
            )
        }
    }
}
