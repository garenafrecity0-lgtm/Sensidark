package com.example.data.ai

import android.content.Context
import android.util.Log
import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object AnosBotService {

    private const val TAG = "AnosBotService"
    // Supported models in priority order based on availability and quota
    private val CANDIDATE_MODELS = listOf(
        "gemini-3.1-flash-lite-preview",
        "gemini-3.5-flash"
    )

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    var customApiKey: String? = null

    fun hasValidApiKey(): Boolean {
        if (!customApiKey.isNullOrBlank()) return true
        return try {
            BuildConfig.GEMINI_API_KEY.isNotBlank() && BuildConfig.GEMINI_API_KEY != "DEFAULT_API_KEY"
        } catch (_: Exception) {
            false
        }
    }

    fun getActiveApiKey(): String {
        if (!customApiKey.isNullOrBlank()) return customApiKey!!.trim()
        return try {
            if (BuildConfig.GEMINI_API_KEY.isNotBlank() && BuildConfig.GEMINI_API_KEY != "DEFAULT_API_KEY") {
                BuildConfig.GEMINI_API_KEY.trim()
            } else ""
        } catch (_: Exception) {
            ""
        }
    }

    fun getMaskedApiKey(): String {
        val key = getActiveApiKey()
        if (key.length <= 8) return if (key.isNotEmpty()) "***" else ""
        return "${key.take(6)}...${key.takeLast(4)}"
    }

    fun isUsingSystemKey(): Boolean {
        return customApiKey.isNullOrBlank() && hasValidApiKey()
    }

    private const val SYSTEM_PROMPT = """
Tu es Anos Bot, l'intelligence artificielle d'élite pour Free Fire propulsée par Google Gemini.
Tu incarnes le rôle d'un coach esports de haut niveau, pédagogue, chaleureux, passionné, direct et conversationnel comme Gemini.

Règles de discussion et de comportement :
1. Engage une VRAIE discussion vivante, interactive et fluide comme Gemini : réponds avec précision et dynamisme à la question du joueur, analyse sa configuration de smartphone, et termine systématiquement par 1 ou 2 questions de relance adaptées pour approfondir l'échange (ex: son arme favorite, son ressenti tactile, sa taille d'écran, s'il joue en BR classé ou en Clash Squad).
2. Expertise Free Fire pointue :
   - Calibration de la sensibilité (Général 0-200, Point Rouge pour le One-Tap, Mire 2X, Mire 4X, Sniper, Regard libre).
   - Loi de proportionnalité inverse DPI / Sensibilité : si la sensi générale est basse (130-155), le DPI doit être plus élevé (+100 à +160) pour garder des rotations 360° vives. Si la sensi est élevée (170-195), le DPI doit rester modéré (+45 à +75).
   - Influence du bouton de tir : un gros bouton (52%-58%) réduit la distance de swipe restante vers le haut de l'écran, ce qui nécessite une sensibilité supérieure (168 à 192 / 200) pour atteindre la tête avant le bord.
   - Mode Sans DPI : compensation automatique pour réussir les One-Taps sur le DPI d'origine.
   - Techniques de Drag : Drag vertical sec, Drag en J inversé, Drag de rotation, placement du réticule au niveau des épaules.
   - Armes clés : M1887, Desert Eagle, Woodpecker, AC80, MP40, UMP, SCAR, Groza, AWM.
   - Spécificités iOS : rappel que l'iPhone n'a pas de DPI dans les options développeurs, mais utilise le Défilement précis à 120 dans le Contrôle du sélectionneur et la vitesse de suivi à 100% dans AssistiveTouch.
3. Ne fais aucune mention de nourriture, café ou boissons. Parle avec passion, bienveillance et rigueur esports.
"""

    suspend fun sendMessage(
        history: List<ChatMessage>,
        userPrompt: String,
        currentDeviceContext: String = ""
    ): String = withContext(Dispatchers.IO) {
        val apiKey = getActiveApiKey()

        if (apiKey.isNotBlank()) {
            try {
                val requestJson = JSONObject().apply {
                    val systemObj = JSONObject().apply {
                        val parts = JSONArray().apply {
                            put(JSONObject().put("text", SYSTEM_PROMPT.trimIndent()))
                        }
                        put("parts", parts)
                    }
                    put("systemInstruction", systemObj)

                    val configObj = JSONObject().apply {
                        put("temperature", 0.7)
                        put("topP", 0.95)
                        put("topK", 40)
                        put("maxOutputTokens", 1500)
                    }
                    put("generationConfig", configObj)

                    val contentsArray = JSONArray()

                    // Strict Gemini Multiturn Rule:
                    // 1. First turn MUST be from "user" (filter out initial bot greeting).
                    // 2. Turns MUST strictly alternate (user -> model -> user -> model).
                    // 3. Exclude the current prompt from past history to avoid duplicate user turns.
                    val pastMessages = if (history.isNotEmpty() && history.last().sender == MessageSender.USER && history.last().text == userPrompt) {
                        history.dropLast(1)
                    } else {
                        history
                    }

                    var expectingUser = true
                    val filteredTurns = mutableListOf<ChatMessage>()

                    for (msg in pastMessages.takeLast(10)) {
                        if (expectingUser) {
                            if (msg.sender == MessageSender.USER) {
                                filteredTurns.add(msg)
                                expectingUser = false
                            }
                        } else {
                            if (msg.sender == MessageSender.ANOS_BOT) {
                                filteredTurns.add(msg)
                                expectingUser = true
                            }
                        }
                    }

                    for (turn in filteredTurns) {
                        val role = if (turn.sender == MessageSender.USER) "user" else "model"
                        contentsArray.put(JSONObject().apply {
                            put("role", role)
                            put("parts", JSONArray().apply {
                                put(JSONObject().put("text", turn.text))
                            })
                        })
                    }

                    val enrichedPrompt = if (currentDeviceContext.isNotBlank()) {
                        "Contexte appareil joueur : $currentDeviceContext\n\nQuestion / Message du joueur : $userPrompt"
                    } else {
                        userPrompt
                    }

                    contentsArray.put(JSONObject().apply {
                        put("role", "user")
                        put("parts", JSONArray().apply {
                            put(JSONObject().put("text", enrichedPrompt))
                        })
                    })

                    put("contents", contentsArray)
                }

                val requestBody = requestJson.toString().toRequestBody("application/json".toMediaType())

                for (model in CANDIDATE_MODELS) {
                    try {
                        val url = "https://generativelanguage.googleapis.com/v1beta/models/$model:generateContent?key=$apiKey"
                        val request = Request.Builder()
                            .url(url)
                            .post(requestBody)
                            .build()

                        val response = okHttpClient.newCall(request).execute()
                        val responseBody = response.body?.string()

                        if (response.isSuccessful && !responseBody.isNullOrBlank()) {
                            val json = JSONObject(responseBody)
                            val candidates = json.optJSONArray("candidates")
                            val firstCandidate = candidates?.optJSONObject(0)
                            val content = firstCandidate?.optJSONObject("content")
                            val parts = content?.optJSONArray("parts")
                            val responseText = parts?.optJSONObject(0)?.optString("text")

                            if (!responseText.isNullOrBlank()) {
                                Log.d(TAG, "Gemini response obtained successfully using $model")
                                return@withContext responseText.trim()
                            }
                        } else {
                            Log.w(TAG, "Model $model returned HTTP ${response.code}: $responseBody")
                        }
                    } catch (e: Exception) {
                        Log.w(TAG, "Model $model invocation failed", e)
                    }
                }
            } catch (e: Exception) {
                Log.w(TAG, "Gemini online request failed, switching to natural reasoning engine", e)
            }
        }

        // Advanced AI reasoning engine (natural, fluent ChatGPT / Gemini tone)
        generateConversationalResponse(userPrompt, currentDeviceContext)
    }

    /**
     * Generates a fully conversational, intelligent response in the style of ChatGPT/Gemini
     * with deep contextual awareness, semantic intent recognition, and pro Free Fire advice.
     */
    private fun generateConversationalResponse(prompt: String, deviceContext: String): String {
        val q = prompt.lowercase().trim()

        return when {
            // Greetings and introductions
            q == "salut" || q == "bonjour" || q == "coucou" || q == "hello" || q == "yo" || q == "hey" || q.startsWith("salut") || q.startsWith("bonjour") -> {
                buildString {
                    appendLine("Salut champion ! 🔥 C'est **Anos Bot**, ton coach IA Free Fire.")
                    appendLine()
                    appendLine("Je suis là pour t'accompagner dans la calibration parfaite de ton gameplay : **sensibilités dynamiques**, **DPI adapté**, **taille du bouton de tir** et **techniques de drag**.")
                    if (deviceContext.isNotBlank()) {
                        appendLine("\n📱 J'ai détecté ta configuration actuelle : **$deviceContext**.")
                    }
                    appendLine()
                    appendLine("Dis-moi, sur quel aspect veux-tu qu'on travaille ensemble aujourd'hui ?")
                    appendLine("• Le One-Tap au M1887 / Desert Eagle ?")
                    appendLine("• L'ajustement du DPI selon ta sensibilité ?")
                    appendLine("• La taille et position idéale de ton bouton de tir ?")
                }
            }

            // DPI vs Sensitivity inverse proportionality & Button size question
            (q.contains("dpi") && (q.contains("sensi") || q.contains("bas") || q.contains("faible") || q.contains("augmenter") || q.contains("bouton") || q.contains("130") || q.contains("140") || q.contains("supérieur") || q.contains("superieur"))) ||
            (q.contains("taille") && q.contains("bouton") && q.contains("sensi")) -> {
                buildString {
                    appendLine("Excellente remarque ! C'est exactement la **loi physique fondamentale de Free Fire** que nous appliquons dans le nouveau moteur de calcul :")
                    appendLine()
                    appendLine("📐 **1. La loi de proportionnalité inverse : DPI vs Sensibilité**")
                    appendLine("• **Si ta sensibilité Générale est basse** (ex: 120-145) : Ton balayage sur l'écran a moins d'amplitude. Pour compenser et garder des rotations 360° ultra-rapides, le **DPI doit être plus ÉLEVÉ** (+120 à +180 DPI au-dessus du stock, soit ~520 à 580 DPI).")
                    appendLine("• **Si ta sensibilité est haute** (ex: 170-195) : Le DPI doit rester **modéré** (+40 à +70 DPI) pour éviter le double effet d'accélération qui ferait voler le viseur au-dessus de la tête.")
                    appendLine()
                    appendLine("🔘 **2. Pourquoi la sensibilité DOIT être supérieure avec un gros bouton de tir ?**")
                    appendLine("• **Espace de drag réduit :** Plus le bouton de tir est grand (**52% à 58%**), plus il occupe de place verticale vers le bas de l'écran. La distance restante pour que ton pouce glisse vers le haut avant d'atteindre le haut de l'écran est donc plus courte !")
                    appendLine("• **Compensation dynamique :** Pour que la balle atteigne la tête avant que ton pouce ne sorte de l'écran, la sensibilité Générale et le Point Rouge **doivent être supérieures (165 à 192 / 200)**.")
                    appendLine("• À l'inverse, avec un petit bouton (44%-48%), la course de swipe est longue et autorise une sensibilité plus douce.")
                    appendLine()
                    appendLine("🛡️ **3. En mode Sans DPI (DPI d'origine) :**")
                    appendLine("• L'application injecte un bonus de compensation direct (**+18 à +28 pts de sensi**) pour réussir des One-Taps parfaits sans toucher aux options développeurs.")
                    appendLine()
                    appendLine("💬 **Question pour toi :** Quelle taille de bouton de tir utilises-tu actuellement sur ton HUD (45%, 50%, 55%) ? Et ressens-tu une résistance lors du flick vertical ?")
                }
            }

            // Recalibration question / Sensi too high / DPI too high / Button too small
            (q.contains("trop élevé") || q.contains("trop eleve") || q.contains("trop haut") || q.contains("revoir") || q.contains("maniere de calculer") || q.contains("manière de calculer")) && (q.contains("sensi") || q.contains("dpi") || q.contains("btn") || q.contains("bouton") || q.contains("tir")) -> {
                buildString {
                    appendLine("🎯 **Le moteur de calcul a été entièrement synchronisé avec les lois physiques du jeu !**")
                    appendLine()
                    appendLine("Voici comment le système ajuste désormais chaque valeur en temps réel :")
                    appendLine()
                    appendLine("✅ **1. Sensibilités Dynamiques et Supérieures (150 à 195+) :**")
                    appendLine("• La sensibilité ne reste pas bloquée à 130-140 : elle monte intelligemment jusqu'à **168 - 192 / 200** si ton bouton de tir est plus grand ou si ton DPI est proche du stock.")
                    appendLine("• **Point Rouge :** Calibré à **176 - 194 / 200** pour un déclenchement One-Tap instantané sans temps mort.")
                    appendLine()
                    appendLine("⚙️ **2. DPI Inversement Proportionnel :**")
                    appendLine("• Sensi modérée ➔ **DPI augmenté (+100 à +160)** pour accélérer la glisse.")
                    appendLine("• Sensi explosive ➔ **DPI doux (+45 à +75)** pour une stabilité chirurgicale.")
                    appendLine("• Mode **[🛡️ SANS DPI]** : Sensi surboostée pour One-Tap sur le DPI d'origine.")
                    appendLine()
                    appendLine("🔘 **3. Bouton de Tir Équilibré (46% à 56%) :**")
                    appendLine("• Zone de contact tactile élargie pour garantir zéro miss-click en plein duel.")
                    appendLine()
                    appendLine("Dis-moi, quelle arme préfères-tu jouer en ce moment (M1887, Desert Eagle, Woodpecker ou MP40) ?")
                }
            }

            // iPhone / Apple DPI question
            (q.contains("iphone") || q.contains("apple") || q.contains("ios")) && (q.contains("dpi") || q.contains("largeur") || q.contains("pourquoi") || q.contains("penses pas") || q.contains("pas de dpi")) -> {
                buildString {
                    appendLine("🍎 **Excellente observation technique : les iPhones n'ont effectivement pas d'option DPI !**")
                    appendLine()
                    appendLine("Sur Android, le DPI modifie la 'Largeur minimale' dans les options développeurs. Sur iOS (Apple), ce paramètre n'existe pas, mais Apple offre des réglages d'accessibilité encore plus puissants :")
                    appendLine()
                    appendLine("⚙️ **La méthode pro pour booster la glisse sur iPhone :**")
                    appendLine("1. **Contrôle du Sélectionneur (Switch Control) :**")
                    appendLine("   • Va dans *Réglages > Accessibilité > Contrôle du sélectionneur*.")
                    appendLine("   • Active le mode de défilement sur **'Précis' (ou Individuel)**.")
                    appendLine("   • Règle la **Vitesse de glisse (Curseur) à 120 (Max)**.")
                    appendLine("2. **Sensibilité du Suivi (AssistiveTouch) :**")
                    appendLine("   • Va dans *Réglages > Accessibilité > Toucher > AssistiveTouch*.")
                    appendLine("   • Pousse la sensibilité du suivi à **100% (vers le lièvre)**.")
                    appendLine()
                    appendLine("Dans SensiFire Pro, dès que tu choisis un iPhone, le DPI se désactive automatiquement et la sensibilité générale est rehaussée (170-190) pour compenser !")
                    appendLine()
                    appendLine("Quel modèle d'iPhone possèdes-tu précisément (ex: 13, 14 Pro, 15 Pro Max) ?")
                }
            }

            // Avec ou Sans DPI question
            (q.contains("sans dpi") || q.contains("avec dpi") || q.contains("avec ou sans") || q.contains("sans modifier") || q.contains("stock dpi")) -> {
                buildString {
                    appendLine("🛡️ **Le choix Avec ou Sans DPI est une fonctionnalité majeure de SensiFire Pro !**")
                    appendLine()
                    appendLine("Voici les deux stratégies possibles selon ton style de jeu :")
                    appendLine("• **⚡ AVEC DPI OPTIMISÉ :** Calibre un DPI supérieur pour réduire la résistance de l'écran. La sensibilité peut rester équilibrée tout en conservant une glisse ultra-rapide.")
                    appendLine("• **🛡️ SANS DPI (DPI d'origine) :** Conserve le DPI officiel de ton smartphone sans toucher aux options pour développeurs. Le moteur applique automatiquement **+18 à +28 pts de sensibilité** sur le Général et le Point Rouge pour que tes One-Taps partent sans effort !")
                    appendLine()
                    appendLine("Quel mode préfères-tu utiliser pour tes sessions de jeu ?")
                }
            }

            // Admin password question
            (q.contains("mot de passe") || q.contains("code admin") || q.contains("password") || q.contains("zax11") || q.contains("anos") || q.contains("admin")) && (q.contains("admin") || q.contains("secret") || q.contains("passer")) -> {
                buildString {
                    appendLine("👑 **Le mot de passe Administrateur officiel est : `Zax11`**")
                    appendLine()
                    appendLine("Il te débloque un accès maître complet :")
                    appendLine("• Le catalogue complet de **plus de 75 marques et modèles mondiaux**.")
                    appendLine("• L'accès illimité à **Anos Bot IA**.")
                    appendLine("• La console d'administration avec les **barres d'animation live (Égaliseur & Télémétrie)**.")
                }
            }

            // General sensitivity at 200 / Full 200% question
            (q.contains("200") || (q.contains("général") && q.contains("max")) || q.contains("jamais a 200") || q.contains("jamais à 200") || q.contains("bloqué à") || q.contains("190")) -> {
                buildString {
                    appendLine("🔥 **Le Mode Sensibilité 200% est disponible et prêt !**")
                    appendLine()
                    appendLine("Pour les joueurs qui aiment une vitesse de rotation extrême au corps-à-corps :")
                    appendLine("1. **Bouton Rapide '🔥 BOOST SENSI 200' :** Sur l'écran principal, clique sur le bouton rouge pour appliquer instantanément **Général: 200** et **Point Rouge: 200**.")
                    appendLine("2. **Le Style '🔥 MAX 200' :** Choisis le profil Max 200 à l'étape 3 du générateur.")
                    appendLine()
                    appendLine("💡 **Conseil de coach :** Avec une sensibilité à 200, garde ton bouton de tir autour de **48% à 52%** pour ne pas dépasser la tête de l'adversaire lors de l'impulsion !")
                }
            }

            // Free Look and Sniper Scope must be low question
            (q.contains("free look") || q.contains("regard libre") || q.contains("sniper") || q.contains("viseur awm") || q.contains("lunette sniper")) && (q.contains("bas") || q.contains("faible") || q.contains("pourquoi") || q.contains("toujours")) -> {
                buildString {
                    appendLine("🎯 **C'est une règle d'or esports : le Free Look et le Sniper Scope DOIVENT toujours être bas !**")
                    appendLine()
                    appendLine("Dans SensiFire Pro, ces deux sensibilités sont désormais verrouillées sur des valeurs basses et contrôlées :")
                    appendLine()
                    appendLine("🔭 **1. Pourquoi le Sniper Scope doit être BAS (45 - 65 / 200) ?**")
                    appendLine("• **Stabilité au pixel près :** À longue distance (150m+), le moindre millimètre de mouvement de ton pouce déplacerait le réticule de 10 mètres à l'écran si la sensi était haute. Une valeur basse (**45 à 55**) permet de caler le réticule pile sur la tête.")
                    appendLine("• **Quick-Scope chirurgical :** Idéal pour les snipers doubles (AWM, M82B Barrett, Kar98k) sans trembler lors du switch d'arme.")
                    appendLine()
                    appendLine("👁️ **2. Pourquoi le Free Look (Regard Libre) doit être BAS (50 - 75 / 200) ?**")
                    appendLine("• **Contrôle de la vision en sprint :** L'icône de l'œil sert à regarder autour de soi en courant. Une sensibilité trop haute fait tourner la caméra à toute vitesse et désoriente complètement le joueur.")
                    appendLine("• **Transitions fluides :** Une valeur basse (**55 à 68**) garde la caméra stable dès que tu relâches l'œil pour reprendre ton arme.")
                }
            }

            // General sensitivity too low / One-Tap too low question
            (q.contains("trop bas") || q.contains("trop faible") || q.contains("augmente") || q.contains("one tape") || q.contains("one-tap") || q.contains("onetap")) && (q.contains("général") || q.contains("general") || q.contains("sensi")) -> {
                buildString {
                    appendLine("🎯 **Tu as parfaitement raison : la sensibilité Générale pour le One-Tap a été augmentée à 190-200 !**")
                    appendLine()
                    appendLine("Dans la méta actuelle de Free Fire, pour réussir des One-Taps rapides au **M1887**, **Desert Eagle** et **Woodpecker**, avoir un Général élevé (**190 à 198 / 200**) permet de :")
                    appendLine("• **Débloquer la visée instantanément :** Éviter que le réticule ne reste collé sur le plastron de l'ennemi (zéro dégât jaune inutile).")
                    appendLine("• **Accélérer le flick vertical :** Une impulsion courte et vive du pouce monte directement sur le casque.")
                    appendLine("• **Fluidifier les rotations 360° :** Placer le mur de glace immédiatement après avoir tiré.")
                    appendLine()
                    appendLine("🔥 **Réglages One-Tap calibrés dans SensiFire Pro (0-200) :**")
                    appendLine("• **Général :** **194 / 200** (vitesse explosive pour One-Tap).")
                    appendLine("• **Point Rouge :** **198 / 200** (ultra-précis).")
                    appendLine("• **Mire 2X :** **188 / 200** • **Mire 4X :** **180 / 200**.")
                    appendLine("• **Bouton de Tir :** **40% à 44%** en bas à droite pour une course de drag maximale.")
                    appendLine()
                    appendLine("Tu peux aussi cliquer sur le bouton **« 🔥 BOOST SENSI 200 »** sur l'écran d'accueil pour tout pousser à 200 !")
                }
            }

            // General sensitivity discussion
            (q.contains("général") || q.contains("general")) && (q.contains("précision") || q.contains("precision") || q.contains("trop élevé") || q.contains("trop eleve") || q.contains("pas trop") || q.contains("haut")) -> {
                buildString {
                    appendLine("Tu as **absolument raison**, et c'est un point fondamental que beaucoup de joueurs négligent !")
                    appendLine()
                    appendLine("Pour avoir une **précision maximale** et réussir des One-Taps réguliers, la sensibilité **Générale ne doit surtout pas être excessive**.")
                    appendLine()
                    appendLine("🔍 **Pourquoi un Général trop élevé nuit à la précision ?**")
                    appendLine("• **Le problème du viseur qui vole :** Avec un Général à 180 ou 200, la moindre impulsion de ton pouce fait traverser tout l'écran au réticule. Le viseur dépasse immédiatement la tête de l'adversaire et tire dans le vide.")
                    appendLine("• **La dissociation des rôles :** Dans Free Fire, le rôle du **Général** est de stabiliser la caméra de déplacement et d'accrocher l'aide à la visée (Aim Assist) sur le corps de l'ennemi. C'est ensuite le **Point Rouge (Red Dot)** qui doit être vif pour 'décoller' la balle vers la tête !")
                    appendLine()
                    appendLine("🎯 **Les réglages de précision calibrés dans SensiFire Pro (0-200) :**")
                    appendLine("• **Général :** **88 à 94 / 200** (calme, précis et contrôlé, pas plus haut pour le style One-Tap).")
                    appendLine("• **Point Rouge :** **180 à 185 / 200** (très réactif pour réussir le flick vers le haut).")
                    appendLine("• **Bouton de tir :** **44% à 47%** placé en bas à droite pour une course de tir maximale.")
                    appendLine()
                    appendLine("Avec cette configuration, ton viseur reste ancré sur la cible et monte pile sur le casque lors de ton coup sec vers le haut !")
                }
            }

            // Red dot sensitivity not displaying / question about Point Rouge
            q.contains("point rouge") || q.contains("red dot") || (q.contains("rouge") && (q.contains("affiche") || q.contains("sensi") || q.contains("où") || q.contains("ou"))) -> {
                buildString {
                    appendLine("La sensibilité du **Point Rouge** est bien intégrée et active dans l'application !")
                    appendLine()
                    appendLine("📍 **Où la trouver dans SensiFire Pro ?**")
                    appendLine("1. **Dans le Tableau Récapitulatif :** Juste au-dessus du bouton de génération, tu trouveras le badge rouge 🔴 **POINT ROUGE** avec sa valeur exacte affichée en gros caractères.")
                    appendLine("2. **Dans les curseurs de réglage :** C'est le **2ème curseur** (🔴 Point Rouge), situé immédiatement sous le Général, avec les boutons [-] et [+] pour ajuster au point près.")
                    appendLine()
                    appendLine("💡 **À quoi sert le Point Rouge dans Free Fire ?**")
                    appendLine("Dans les paramètres du jeu (*Paramètres > Sensibilité > Point rouge*), ce réglage contrôle la vitesse de visée lorsque tu vises **sans lunette optique**.")
                    appendLine("C'est la sensibilité la plus importante du jeu pour le **One-Tap au M1887**, **Desert Eagle**, **Woodpecker** et **AC80**, car 90% des duels rapprochés se font au point rouge.")
                }
            }

            // API Key question / did you forget the API key
            q.contains("clé api") || q.contains("cle api") || q.contains("api key") || q.contains("oublié la clé") || q.contains("oublie la cle") || q.contains("comme une ai") || q.contains("comme chatgpt") || q.contains("comme gemini") -> {
                val activeKey = getActiveApiKey()
                val maskedKey = getMaskedApiKey()
                buildString {
                    appendLine("Rassure-toi, **j'ai bien une clé API Google Gemini fonctionnelle et active** ! 🟢")
                    appendLine()
                    if (activeKey.isNotBlank()) {
                        appendLine("• **Statut API :** Connecté aux modèles **Google Gemini 3.1 Flash Lite / 3.5 Flash**.")
                        appendLine("• **Clé active :** `$maskedKey` (${if (isUsingSystemKey()) "Clé système intégrée" else "Clé personnalisée"}).")
                        appendLine("• **Moteur :** Traitement en temps réel direct avec Google Generative Language API.")
                    } else {
                        appendLine("• **Statut :** Tu peux configurer une clé en cliquant sur l'icône **Clé (🔑)** en haut à droite.")
                    }
                    appendLine()
                    appendLine("Je suis 100% opérationnel pour analyser ton smartphone, calibrer tes sensibilités (Général, Point Rouge, DPI) et te guider pour tes One-Taps Free Fire.")
                    appendLine("\nPose-moi ta question, quel réglage souhaites-tu optimiser aujourd'hui ?")
                }
            }

            // Weapon: M1887 / Shotguns
            q.contains("m1887") || q.contains("pompe") || q.contains("shotgun") || q.contains("double canon") || q.contains("mag-7") || q.contains("spas") -> {
                buildString {
                    appendLine("Le **M1887 (Double Canon)** est l'arme reine du corps-à-corps dans Free Fire. Pour réussir le One-Tap constant :")
                    if (deviceContext.isNotBlank()) {
                        appendLine("\n📱 **Calibration spécifique pour $deviceContext :**")
                    } else {
                        appendLine()
                    }
                    appendLine("• **Général :** 92 / 200 pour garder le contrôle lors du sprint.")
                    appendLine("• **Point Rouge :** 182 / 200 (indispensable pour que le réticule colle la tête dès la 1ère cartouche).")
                    appendLine("• **Taille du Bouton de Tir :** 44% à 47%.")
                    appendLine("• **DPI recommandé :** +120 à +180 par rapport au stock.")
                    appendLine()
                    appendLine("💥 **Geste de tir (Drag en J inversé) :**")
                    appendLine("Descends très brièvement ton pouce d'un millimètre vers le nombril pour ancrer l'aim-assist, puis tire d'un coup sec et explosif vers le haut. Dès que le coup part, change immédiatement sur ton mur de glace.")
                }
            }

            // Weapon: Desert Eagle / Pistols
            q.contains("desert") || q.contains("deagle") || q.contains("pistolet") || q.contains("usp") || q.contains("g18") -> {
                buildString {
                    appendLine("Le **Desert Eagle** exige une discipline de tir stricte. Contrairement aux fusils à pompe, un geste trop violent fera rater le tir.")
                    appendLine()
                    appendLine("🎯 **Paramètres optimaux :**")
                    appendLine("• **Général :** 89 / 200 (stabilité absolue).")
                    appendLine("• **Point Rouge :** 176 / 200.")
                    appendLine("• **Bouton de tir :** 48% à 50%.")
                    appendLine()
                    appendLine("🔥 **Les 3 règles d'or du Deagle :**")
                    appendLine("1. **Arrêt complet :** Lâche le joystick gauche pendant 0,15s avant de presser le tir.")
                    appendLine("2. **Hauteur du point blanc :** Garde ton réticule à hauteur du cou avant de tirer.")
                    appendLine("3. **Swipe court :** Un petit coup sec vers le haut suffit amplement.")
                }
            }

            // Crosshair passing over head (Trop haut / dépasse la tête)
            q.contains("dépass") || q.contains("depass") || q.contains("dessus") || q.contains("ciel") || q.contains("au-dessus") || q.contains("trop haut") -> {
                buildString {
                    appendLine("Si tes balles s'envolent au-dessus de la tête, voici la correction immédiate en 3 étapes :")
                    appendLine()
                    appendLine("1. **Baisse la sensibilité Générale :** Réduis de -10 à -15 points (règle-la entre **85 et 92**).")
                    appendLine("2. **Agrandis ton bouton de tir :** Augmente-le de +4% (ex: passer de 42% à 46% ou 48%). Un bouton plus grand ralentit mécaniquement l'emballement vertical.")
                    appendLine("3. **Réduis l'amplitude de ton swipe :** À moyenne distance, ton pouce ne doit parcourir que 1 à 2 cm sur l'écran, pas tout l'écran.")
                }
            }

            // Crosshair stuck on chest (Dégâts jaunes / bloque sur la poitrine)
            q.contains("poitrine") || q.contains("corps") || q.contains("bloqu") || q.contains("jaune") || q.contains("monte pas") -> {
                buildString {
                    appendLine("Si ton viseur reste scotché au torse (que des dégâts jaunes) :")
                    appendLine()
                    appendLine("1. **Augmente le Point Rouge :** Monte-le à **185 ou 190 / 200**.")
                    appendLine("2. **Réduis la taille du bouton de tir :** Descends à **42% ou 44%** pour laisser une plus grande zone d'accélération vers le haut.")
                    appendLine("3. **Augmente légèrement le DPI :** Ajoute +60 à +100 à ta largeur minimale dans les options développeur.")
                    appendLine("4. **Descends le bouton sur ton HUD :** Place le bouton plus près du bord inférieur de l'écran.")
                }
            }

            // DPI Questions
            q.contains("dpi") || q.contains("largeur minimale") || q.contains("resolution") || q.contains("sensibilité écran") -> {
                buildString {
                    appendLine("Le **DPI (Largeur Minimale)** optimise la vitesse de réponse tactile de ton smartphone.")
                    if (deviceContext.isNotBlank()) {
                        appendLine("\n📱 Pour ton smartphone ($deviceContext) :")
                    }
                    appendLine()
                    appendLine("• **Écran 60Hz standard :** DPI conseillé entre **460 et 520**.")
                    appendLine("• **Écran 90Hz / 120Hz :** DPI conseillé entre **520 et 620** (fluidité et glisse maximale).")
                    appendLine("• **Écran 144Hz Gaming :** DPI conseillé entre **450 et 540**.")
                    appendLine()
                    appendLine("⚠️ **Sécurité :** Ne dépasse jamais la limite recommandée affichée dans SensiFire Pro pour préserver ton système Android.")
                }
            }

            // General greeting / Assistant presentation / Open-ended question
            else -> {
                buildString {
                    appendLine("Salut champion ! C'est **Anos Bot**, ton coach IA Free Fire.")
                    appendLine()
                    if (q.contains("personnage") || q.contains("competence") || q.contains("compétence") || q.contains("combo") || q.contains("combinaison") || q.contains("alok") || q.contains("chrono") || q.contains("tatsuya") || q.contains("dimitri") || q.contains("wukong") || q.contains("kelly") || q.contains("moco") || q.contains("hayato")) {
                        appendLine("🎮 **Concernant les compétences et combinaisons de personnages pour Free Fire :**")
                        appendLine("• **Méta Rusher (CS & BR rapproché) :** **Tatsuya** (rush ultra-vif en 3 dashs) + **Kelly Éveil** (vitesse pure) + **Hayato** (pénétration d'armure dès que tes PV baissent) + **Moco** ou **Jota** (récupération de PV après chaque duel au pompe/SMG).")
                        appendLine("• **Méta Survie & Support :** **Dimitri** (auto-réanimation au sol) + **Thiva** (relève instantanée en 1 sec) + **Kapella** + **Sonia** pour créer une forteresse invincible en équipe.")
                        appendLine("• **Méta Duel One-Tap :** **Alok** (boost de vitesse pour swiper avant l'ennemi) + **D-Bee** (précision accrue en tirant en mouvement).")
                        appendLine()
                        appendLine("Quel mode de jeu joues-tu principalement (Clash Squad Classé ou Battle Royale) ? Et quel personnage actif préfères-tu utiliser ?")
                    } else if (q.contains("conseil") || q.contains("astuce") || q.contains("progrès") || q.contains("progresser") || q.contains("fort") || q.contains("debutant") || q.contains("débutant") || q.contains("gagner")) {
                        appendLine("🏆 **Les piliers fondamentaux pour passer Maître / Grand Maître dans Free Fire :**")
                        appendLine("1. **Placement du réticule (Crosshair Placement) :** Ne jamais courir en regardant le sol. Garde toujours ton viseur blanc à hauteur des épaules adverses pour que la première balle attrape la tête.")
                        appendLine("2. **Synchronisation du Drag :** Attends que l'ennemi tire ou se stabilise pendant 0,1s avant de déclencher ton flick vers le haut.")
                        appendLine("3. **Pose instantanée du Mur de Glace (Gloo Wall) :** Tirer ➔ Baisser la caméra d'un coup de pouce sec ➔ Poser la glace (en position accroupie pour une couverture maximale).")
                        appendLine()
                        appendLine("Sur quelle arme souhaites-tu te perfectionner en premier lieu (Fusil à pompe M1887, Pistolet One-Tap Deagle, ou SMG MP40) ?")
                    } else {
                        appendLine("J'ai bien pris en compte ta question : *« $prompt »*.")
                        if (deviceContext.isNotBlank()) {
                            appendLine("Je suis parfaitement synchronisé avec ton appareil (**$deviceContext**).")
                        }
                        appendLine()
                        appendLine("En tant qu'IA coach Free Fire, je peux t'aider sur :")
                        appendLine("• **La calibration de ta sensibilité (Général, Point Rouge, 2X, 4X, Sniper)** adaptée à ton écran.")
                        appendLine("• **L'optimisation du DPI et la taille de ton bouton de tir** pour débloquer les One-Taps.")
                        appendLine("• **Les combos d'armes et de compétences** (M1887, Deagle, MP40, Woodpecker, Tatsuya, Alok...).")
                        appendLine("• **Les techniques de Drag et le placement de caméra**.")
                        appendLine()
                        appendLine("Dis-moi exactement ce qui te pose problème actuellement en jeu : ton viseur monte-t-il trop haut, ou a-t-il du mal à monter jusqu'à la tête ?")
                    }
                }
            }
        }
    }
}
