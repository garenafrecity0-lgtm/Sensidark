package com.example

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material.icons.outlined.SportsEsports
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AuthConstants
import com.example.data.model.UserRole
import com.example.ui.screens.AnosBotScreen
import com.example.ui.screens.AuthScreen
import com.example.ui.screens.DpiGuideScreen
import com.example.ui.screens.FavoritesScreen
import com.example.ui.screens.FireButtonSimulatorScreen
import com.example.ui.screens.GeneratorScreen
import com.example.ui.theme.CoffeeCrema
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkObsidian
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.FireCrimson
import com.example.ui.theme.FireGold
import com.example.ui.theme.FireOrange
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.SensiViewModel

enum class SensiNavTab(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
) {
    GENERATOR("Générateur", Icons.Filled.Tune, Icons.Outlined.Tune, "tab_generator"),
    ANOS_BOT("Anos Bot IA", Icons.Filled.Psychology, Icons.Outlined.Psychology, "tab_anos_bot"),
    SIMULATOR("Bouton & Drag", Icons.Filled.SportsEsports, Icons.Outlined.SportsEsports, "tab_simulator"),
    FAVORITES("Favoris", Icons.Filled.Bookmark, Icons.Outlined.BookmarkBorder, "tab_favorites"),
    GUIDE("Tuto DPI", Icons.Filled.MenuBook, Icons.Outlined.MenuBook, "tab_guide")
}

class MainActivity : ComponentActivity() {
    private val viewModel: SensiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val context = LocalContext.current
                val toastMessage by viewModel.copySuccessMessage.collectAsState()
                val isAuthenticated by viewModel.isAuthenticated.collectAsState()

                LaunchedEffect(toastMessage) {
                    toastMessage?.let {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        viewModel.clearToastMessage()
                    }
                }

                if (!isAuthenticated) {
                    AuthScreen(viewModel = viewModel)
                } else {
                    MainSensiApp(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun MainSensiApp(viewModel: SensiViewModel) {
    var selectedTab by rememberSaveable { mutableStateOf(SensiNavTab.GENERATOR) }
    val userRole by viewModel.userRole.collectAsState()
    var showLogoutConfirm by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkObsidian),
        containerColor = DarkObsidian,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .background(DarkSurface)
                    .border(1.dp, DarkBorder)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(FireOrange),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Text(
                        text = "SensiFire Pro",
                        color = TextPrimary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Black
                    )

                    // Role Badge
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(
                                when (userRole) {
                                    UserRole.ADMIN -> FireCrimson.copy(alpha = 0.25f)
                                    UserRole.VIP -> FireGold.copy(alpha = 0.25f)
                                    UserRole.CLIENT -> DarkSurfaceVariant
                                }
                            )
                            .border(
                                1.dp,
                                when (userRole) {
                                    UserRole.ADMIN -> FireCrimson
                                    UserRole.VIP -> FireGold
                                    UserRole.CLIENT -> DarkBorder
                                },
                                RoundedCornerShape(6.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = userRole.badge,
                            color = when (userRole) {
                                UserRole.ADMIN -> FireCrimson
                                UserRole.VIP -> FireGold
                                UserRole.CLIENT -> TextSecondary
                            },
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                IconButton(
                    onClick = { showLogoutConfirm = true },
                    modifier = Modifier.size(32.dp).testTag("btn_logout_action")
                ) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Changer de clé / Déconnexion",
                        tint = TextMuted,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .padding(horizontal = 8.dp, vertical = 6.dp)
            ) {
                NavigationBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(DarkSurface)
                        .border(1.dp, DarkBorder, RoundedCornerShape(20.dp)),
                    containerColor = DarkSurface,
                    tonalElevation = 0.dp
                ) {
                    SensiNavTab.entries.forEach { tab ->
                        val isSelected = selectedTab == tab
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { selectedTab = tab },
                            icon = {
                                Icon(
                                    imageVector = if (isSelected) tab.selectedIcon else tab.unselectedIcon,
                                    contentDescription = tab.title,
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = tab.title,
                                    fontSize = 9.sp,
                                    maxLines = 1,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = if (tab == SensiNavTab.ANOS_BOT) CoffeeCrema else FireOrange,
                                selectedTextColor = if (tab == SensiNavTab.ANOS_BOT) CoffeeCrema else FireOrange,
                                unselectedIconColor = TextMuted,
                                unselectedTextColor = TextMuted,
                                indicatorColor = (if (tab == SensiNavTab.ANOS_BOT) CoffeeCrema else FireOrange).copy(alpha = 0.15f)
                            ),
                            modifier = Modifier.testTag(tab.testTag)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Crossfade(targetState = selectedTab, label = "tabScreenTransition") { tab ->
                when (tab) {
                    SensiNavTab.GENERATOR -> GeneratorScreen(
                        viewModel = viewModel,
                        onNavigateToSimulator = { selectedTab = SensiNavTab.SIMULATOR }
                    )
                    SensiNavTab.ANOS_BOT -> AnosBotScreen(
                        viewModel = viewModel
                    )
                    SensiNavTab.SIMULATOR -> FireButtonSimulatorScreen(
                        viewModel = viewModel,
                        onBack = { selectedTab = SensiNavTab.GENERATOR }
                    )
                    SensiNavTab.FAVORITES -> FavoritesScreen(
                        viewModel = viewModel,
                        onNavigateToGenerator = { selectedTab = SensiNavTab.GENERATOR }
                    )
                    SensiNavTab.GUIDE -> DpiGuideScreen()
                }
            }
        }
    }

    if (showLogoutConfirm) {
        AlertDialog(
            onDismissRequest = { showLogoutConfirm = false },
            containerColor = DarkSurface,
            title = {
                Text("Changer de Clé d'Accès", color = TextPrimary, fontWeight = FontWeight.Bold)
            },
            text = {
                Text(
                    "Voulez-vous vous déconnecter pour entrer une autre clé d'accès (VIP, Admin com.dts ou Client) ?",
                    color = TextSecondary,
                    fontSize = 13.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showLogoutConfirm = false
                        viewModel.logout()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = FireOrange),
                    modifier = Modifier.testTag("btn_confirm_logout")
                ) {
                    Text("Se Déconnecter")
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutConfirm = false }) {
                    Text("Annuler", color = TextMuted)
                }
            }
        )
    }
}
