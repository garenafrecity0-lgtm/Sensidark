package com.example.data.model

object DeviceCatalog {
    val BRANDS = listOf(
        "Samsung",
        "Apple (iPhone)",
        "Xiaomi",
        "Redmi",
        "POCO",
        "Infinix",
        "Tecno",
        "Realme",
        "OnePlus",
        "Vivo",
        "iQOO",
        "Oppo",
        "Motorola",
        "Google Pixel",
        "Asus (ROG Gaming)",
        "RedMagic (Nubia)",
        "Honor",
        "Huawei",
        "Itel",
        "Sony Xperia",
        "Nothing Phone",
        "Black Shark",
        "Lenovo (Legion)",
        "Meizu",
        "ZTE",
        "TCL",
        "Nokia",
        "HMD",
        "LG",
        "HTC",
        "Sharp (Aquos)",
        "Kyocera",
        "Fujitsu (Arrows)",
        "Umidigi",
        "Oukitel",
        "Blackview",
        "Doogee",
        "Ulefone",
        "Cubot",
        "AGM Gaming",
        "Cat (Caterpillar)",
        "Fairphone",
        "BQ",
        "Wiko",
        "Archos",
        "Micromax",
        "Lava",
        "Karbonn",
        "Symphony",
        "Walton",
        "Casper",
        "General Mobile",
        "Vestel",
        "Condor",
        "Evertek",
        "Bmobile",
        "Lanix",
        "BLU",
        "Positivo",
        "Multilaser",
        "LeEco",
        "Coolpad",
        "Gionee",
        "Smartisan",
        "Essential",
        "Razer Phone",
        "Unihertz",
        "Autre / Modèle Personnalisé"
    )

    val PRESET_DEVICES: Map<String, List<DeviceSpec>> = mapOf(
        "Samsung" to listOf(
            DeviceSpec("Samsung", "Galaxy S24 Ultra", 120, 6.8f, 411, 800, 360, 12),
            DeviceSpec("Samsung", "Galaxy S24 / S24+", 120, 6.7f, 411, 750, 360, 12),
            DeviceSpec("Samsung", "Galaxy S23 Ultra", 120, 6.8f, 411, 800, 360, 12),
            DeviceSpec("Samsung", "Galaxy S23 / S23 FE", 120, 6.4f, 403, 720, 240, 8),
            DeviceSpec("Samsung", "Galaxy S22 Ultra", 120, 6.8f, 411, 780, 240, 12),
            DeviceSpec("Samsung", "Galaxy S21 / S21 FE", 120, 6.4f, 403, 700, 240, 8),
            DeviceSpec("Samsung", "Galaxy Note 20 Ultra", 120, 6.9f, 411, 750, 240, 12),
            DeviceSpec("Samsung", "Galaxy A55 5G", 120, 6.6f, 390, 660, 240, 8),
            DeviceSpec("Samsung", "Galaxy A54 5G", 120, 6.4f, 403, 640, 240, 8),
            DeviceSpec("Samsung", "Galaxy A35 / A34", 120, 6.6f, 390, 620, 240, 6),
            DeviceSpec("Samsung", "Galaxy A25 / A24", 120, 6.5f, 396, 600, 180, 6),
            DeviceSpec("Samsung", "Galaxy A15 / A14", 90, 6.5f, 399, 560, 180, 4),
            DeviceSpec("Samsung", "Galaxy A05s / A05", 90, 6.7f, 393, 540, 120, 4),
            DeviceSpec("Samsung", "Galaxy Z Fold 6 / Fold 5", 120, 7.6f, 374, 850, 360, 12),
            DeviceSpec("Samsung", "Galaxy Z Flip 6 / Flip 5", 120, 6.7f, 426, 750, 360, 8),
            DeviceSpec("Samsung", "Galaxy M54 / M34", 120, 6.7f, 393, 620, 240, 8)
        ),
        "Apple (iPhone)" to listOf(
            DeviceSpec("Apple (iPhone)", "iPhone 16 Pro Max", 120, 6.9f, 460, 800, 240, 8),
            DeviceSpec("Apple (iPhone)", "iPhone 16 Pro", 120, 6.3f, 460, 750, 240, 8),
            DeviceSpec("Apple (iPhone)", "iPhone 16 / 16 Plus", 60, 6.7f, 460, 650, 180, 8),
            DeviceSpec("Apple (iPhone)", "iPhone 15 Pro Max", 120, 6.7f, 460, 800, 240, 8),
            DeviceSpec("Apple (iPhone)", "iPhone 15 Pro", 120, 6.1f, 460, 750, 240, 8),
            DeviceSpec("Apple (iPhone)", "iPhone 15 / 15 Plus", 60, 6.1f, 460, 650, 180, 6),
            DeviceSpec("Apple (iPhone)", "iPhone 14 Pro Max", 120, 6.7f, 460, 780, 240, 6),
            DeviceSpec("Apple (iPhone)", "iPhone 14 / 14 Plus", 60, 6.1f, 460, 620, 180, 6),
            DeviceSpec("Apple (iPhone)", "iPhone 13 Pro Max", 120, 6.7f, 458, 750, 240, 6),
            DeviceSpec("Apple (iPhone)", "iPhone 13 / 13 mini", 60, 6.1f, 460, 600, 180, 4),
            DeviceSpec("Apple (iPhone)", "iPhone 12 Pro Max / 12", 60, 6.7f, 458, 600, 180, 6),
            DeviceSpec("Apple (iPhone)", "iPhone 11 Pro Max / 11", 60, 6.5f, 458, 580, 120, 4),
            DeviceSpec("Apple (iPhone)", "iPhone XR / XS Max", 60, 6.1f, 326, 560, 120, 4),
            DeviceSpec("Apple (iPhone)", "iPhone 8 Plus / SE", 60, 4.7f, 326, 540, 120, 3)
        ),
        "Xiaomi" to listOf(
            DeviceSpec("Xiaomi", "Xiaomi 14 Ultra / 14", 120, 6.73f, 522, 850, 480, 16),
            DeviceSpec("Xiaomi", "Xiaomi 13T Pro / 13", 144, 6.67f, 446, 800, 480, 12),
            DeviceSpec("Xiaomi", "Xiaomi 12 Pro / 12", 120, 6.73f, 521, 750, 480, 12),
            DeviceSpec("Xiaomi", "Xiaomi 11T Pro / 11T", 120, 6.67f, 395, 700, 480, 8),
            DeviceSpec("Xiaomi", "Xiaomi Mi 11 Ultra", 120, 6.81f, 515, 780, 480, 12)
        ),
        "Redmi" to listOf(
            DeviceSpec("Redmi", "Redmi Note 13 Pro+ 5G", 120, 6.67f, 446, 720, 480, 12),
            DeviceSpec("Redmi", "Redmi Note 13 Pro / 13 4G", 120, 6.67f, 395, 650, 240, 8),
            DeviceSpec("Redmi", "Redmi Note 12 Pro+ / 12", 120, 6.67f, 395, 620, 240, 8),
            DeviceSpec("Redmi", "Redmi Note 11 Pro / 11", 90, 6.43f, 409, 580, 180, 6),
            DeviceSpec("Redmi", "Redmi Note 10 Pro", 120, 6.67f, 395, 600, 240, 6),
            DeviceSpec("Redmi", "Redmi 13C / 12 / 10C", 90, 6.74f, 260, 520, 180, 4),
            DeviceSpec("Redmi", "Redmi K70 Pro / K60", 120, 6.67f, 526, 800, 480, 16)
        ),
        "POCO" to listOf(
            DeviceSpec("POCO", "POCO F6 Pro / F6", 120, 6.67f, 526, 800, 480, 16),
            DeviceSpec("POCO", "POCO X6 Pro 5G", 120, 6.67f, 446, 750, 480, 12),
            DeviceSpec("POCO", "POCO X5 Pro 5G", 120, 6.67f, 395, 680, 240, 8),
            DeviceSpec("POCO", "POCO F5 / F5 Pro", 120, 6.67f, 526, 780, 480, 12),
            DeviceSpec("POCO", "POCO F4 GT (Gaming)", 120, 6.67f, 395, 800, 480, 12),
            DeviceSpec("POCO", "POCO M6 Pro / M5", 120, 6.67f, 395, 600, 240, 8),
            DeviceSpec("POCO", "POCO X3 Pro / NFC", 120, 6.67f, 395, 640, 240, 6)
        ),
        "Infinix" to listOf(
            DeviceSpec("Infinix", "Infinix GT 20 Pro 5G (Gaming)", 144, 6.78f, 393, 760, 360, 12),
            DeviceSpec("Infinix", "Infinix GT 10 Pro 5G", 120, 6.67f, 395, 720, 360, 8),
            DeviceSpec("Infinix", "Infinix Note 40 Pro+ 5G", 120, 6.78f, 393, 700, 360, 12),
            DeviceSpec("Infinix", "Infinix Note 40 Pro / 40", 120, 6.78f, 393, 680, 240, 8),
            DeviceSpec("Infinix", "Infinix Note 30 VIP / 30 Pro", 120, 6.67f, 395, 650, 360, 8),
            DeviceSpec("Infinix", "Infinix Hot 40 Pro / 40", 120, 6.78f, 396, 620, 240, 8),
            DeviceSpec("Infinix", "Infinix Hot 30 / 30 Play", 90, 6.78f, 396, 580, 180, 8),
            DeviceSpec("Infinix", "Infinix Zero 30 5G / Ultra", 144, 6.78f, 388, 720, 360, 12),
            DeviceSpec("Infinix", "Infinix Smart 8 / Smart 7", 90, 6.6f, 267, 500, 120, 4)
        ),
        "Tecno" to listOf(
            DeviceSpec("Tecno", "Tecno Pova 6 Pro 5G (Gaming)", 120, 6.78f, 393, 740, 360, 12),
            DeviceSpec("Tecno", "Tecno Pova 5 Pro / Pova 5", 120, 6.78f, 396, 680, 240, 8),
            DeviceSpec("Tecno", "Tecno Camon 30 Premier 5G", 120, 6.77f, 451, 750, 360, 12),
            DeviceSpec("Tecno", "Tecno Camon 30 Pro / 30", 144, 6.78f, 393, 720, 360, 8),
            DeviceSpec("Tecno", "Tecno Camon 20 Pro 5G", 120, 6.67f, 395, 650, 240, 8),
            DeviceSpec("Tecno", "Tecno Spark 20 Pro+ / 20 Pro", 120, 6.78f, 393, 620, 240, 8),
            DeviceSpec("Tecno", "Tecno Spark 20 / 20C", 90, 6.6f, 267, 540, 180, 4),
            DeviceSpec("Tecno", "Tecno Spark 10 Pro", 90, 6.8f, 396, 580, 180, 8),
            DeviceSpec("Tecno", "Tecno Phantom X2 Pro", 120, 6.8f, 387, 720, 360, 12)
        ),
        "Realme" to listOf(
            DeviceSpec("Realme", "Realme GT 6 / GT 6T", 120, 6.78f, 450, 800, 360, 16),
            DeviceSpec("Realme", "Realme GT Neo 5 / GT 3", 144, 6.74f, 451, 820, 480, 16),
            DeviceSpec("Realme", "Realme 12 Pro+ 5G / 12 Pro", 120, 6.7f, 394, 700, 240, 12),
            DeviceSpec("Realme", "Realme 11 Pro+ / 11", 120, 6.7f, 394, 660, 240, 8),
            DeviceSpec("Realme", "Realme Narzo 70 Pro / 60", 120, 6.67f, 395, 640, 240, 8),
            DeviceSpec("Realme", "Realme C67 / C55", 90, 6.72f, 392, 580, 180, 8)
        ),
        "OnePlus" to listOf(
            DeviceSpec("OnePlus", "OnePlus 12 / 12R", 120, 6.82f, 450, 820, 360, 16),
            DeviceSpec("OnePlus", "OnePlus 11 / 11R", 120, 6.7f, 525, 780, 360, 16),
            DeviceSpec("OnePlus", "OnePlus 10 Pro / 10T", 120, 6.7f, 525, 750, 360, 12),
            DeviceSpec("OnePlus", "OnePlus Nord 4 / Nord 3", 120, 6.74f, 450, 720, 360, 12),
            DeviceSpec("OnePlus", "OnePlus Nord CE 4 / CE 3", 120, 6.7f, 394, 660, 240, 8)
        ),
        "Vivo" to listOf(
            DeviceSpec("Vivo", "Vivo X100 Ultra / X100 Pro", 120, 6.78f, 452, 850, 360, 16),
            DeviceSpec("Vivo", "Vivo X90 Pro / X90", 120, 6.78f, 452, 780, 360, 12),
            DeviceSpec("Vivo", "Vivo V30 Pro / V30", 120, 6.78f, 453, 720, 360, 12),
            DeviceSpec("Vivo", "Vivo V29 / V27", 120, 6.78f, 453, 680, 240, 8),
            DeviceSpec("Vivo", "Vivo Y200 / Y100", 120, 6.67f, 395, 620, 240, 8)
        ),
        "iQOO" to listOf(
            DeviceSpec("iQOO", "iQOO 12 Pro / iQOO 12", 144, 6.78f, 517, 850, 480, 16),
            DeviceSpec("iQOO", "iQOO Neo 9 Pro", 144, 6.78f, 452, 820, 480, 16),
            DeviceSpec("iQOO", "iQOO Z9 Turbo / Z9", 144, 6.78f, 453, 750, 360, 12),
            DeviceSpec("iQOO", "iQOO 11 / 11 Pro", 144, 6.78f, 517, 800, 360, 12)
        ),
        "Oppo" to listOf(
            DeviceSpec("Oppo", "Oppo Find X7 Ultra / X7", 120, 6.82f, 510, 820, 360, 16),
            DeviceSpec("Oppo", "Oppo Reno 12 Pro / 12", 120, 6.7f, 394, 720, 240, 12),
            DeviceSpec("Oppo", "Oppo Reno 11 Pro / 11", 120, 6.7f, 394, 680, 240, 12),
            DeviceSpec("Oppo", "Oppo A98 / A78 5G", 120, 6.72f, 391, 620, 240, 8),
            DeviceSpec("Oppo", "Oppo Find N3 Flip / Fold", 120, 6.8f, 403, 750, 240, 12)
        ),
        "Motorola" to listOf(
            DeviceSpec("Motorola", "Motorola Edge 50 Ultra / Pro", 144, 6.7f, 446, 800, 360, 16),
            DeviceSpec("Motorola", "Motorola Edge 40 Pro / 40", 144, 6.55f, 402, 750, 360, 12),
            DeviceSpec("Motorola", "Moto G84 5G / G73", 120, 6.5f, 405, 640, 240, 8),
            DeviceSpec("Motorola", "Moto G54 5G / G34", 120, 6.5f, 405, 600, 240, 8),
            DeviceSpec("Motorola", "Motorola Razr 50 Ultra", 165, 6.9f, 413, 850, 360, 12)
        ),
        "Google Pixel" to listOf(
            DeviceSpec("Google Pixel", "Pixel 9 Pro XL / 9 Pro", 120, 6.8f, 486, 750, 240, 16),
            DeviceSpec("Google Pixel", "Pixel 9", 120, 6.3f, 422, 700, 240, 12),
            DeviceSpec("Google Pixel", "Pixel 8 Pro", 120, 6.7f, 489, 740, 240, 12),
            DeviceSpec("Google Pixel", "Pixel 8 / 8a", 120, 6.2f, 428, 680, 240, 8),
            DeviceSpec("Google Pixel", "Pixel 7 Pro / 7", 120, 6.7f, 512, 700, 240, 12),
            DeviceSpec("Google Pixel", "Pixel 6 Pro / 6", 120, 6.7f, 512, 680, 240, 8)
        ),
        "Asus (ROG Gaming)" to listOf(
            DeviceSpec("Asus (ROG Gaming)", "ROG Phone 8 Pro / 8", 165, 6.78f, 388, 900, 720, 24),
            DeviceSpec("Asus (ROG Gaming)", "ROG Phone 7 Ultimate / 7", 165, 6.78f, 395, 900, 720, 16),
            DeviceSpec("Asus (ROG Gaming)", "ROG Phone 6 Pro / 6D", 165, 6.78f, 395, 880, 720, 16),
            DeviceSpec("Asus (ROG Gaming)", "ROG Phone 5s / 5", 144, 6.78f, 395, 850, 360, 12),
            DeviceSpec("Asus (ROG Gaming)", "Zenfone 11 Ultra / 10", 144, 6.78f, 388, 780, 360, 16)
        ),
        "RedMagic (Nubia)" to listOf(
            DeviceSpec("RedMagic (Nubia)", "RedMagic 9S Pro+ / 9 Pro", 120, 6.8f, 400, 920, 960, 24),
            DeviceSpec("RedMagic (Nubia)", "RedMagic 8S Pro / 8 Pro", 120, 6.8f, 400, 900, 960, 16),
            DeviceSpec("RedMagic (Nubia)", "RedMagic 7S Pro / 7", 165, 6.8f, 387, 880, 720, 16),
            DeviceSpec("RedMagic (Nubia)", "Nubia Z60 Ultra", 120, 6.8f, 400, 800, 360, 16)
        ),
        "Honor" to listOf(
            DeviceSpec("Honor", "Honor Magic 6 Pro / 6", 120, 6.8f, 453, 800, 360, 16),
            DeviceSpec("Honor", "Honor Magic 5 Pro", 120, 6.81f, 460, 760, 360, 12),
            DeviceSpec("Honor", "Honor 200 Pro / 200", 120, 6.78f, 437, 720, 360, 12),
            DeviceSpec("Honor", "Honor 90 / 90 Lite", 120, 6.7f, 435, 660, 240, 8),
            DeviceSpec("Honor", "Honor X9b / X8b", 120, 6.78f, 429, 620, 240, 8)
        ),
        "Huawei" to listOf(
            DeviceSpec("Huawei", "Huawei Pura 70 Ultra / Pro", 120, 6.8f, 460, 820, 360, 16),
            DeviceSpec("Huawei", "Huawei Mate 60 Pro+ / 60", 120, 6.82f, 440, 800, 360, 16),
            DeviceSpec("Huawei", "Huawei P60 Pro", 120, 6.67f, 444, 760, 360, 12),
            DeviceSpec("Huawei", "Huawei Nova 12 Pro / 12", 120, 6.78f, 429, 700, 300, 8),
            DeviceSpec("Huawei", "Huawei Nova 11 / 10", 120, 6.7f, 395, 650, 240, 8)
        ),
        "Itel" to listOf(
            DeviceSpec("Itel", "Itel S24 / S23+", 90, 6.6f, 267, 560, 180, 8),
            DeviceSpec("Itel", "Itel P55 5G / P55+", 90, 6.6f, 267, 540, 180, 8),
            DeviceSpec("Itel", "Itel Color Pro 5G", 90, 6.6f, 267, 520, 180, 6),
            DeviceSpec("Itel", "Itel A70 / A60s", 60, 6.6f, 267, 480, 120, 4)
        ),
        "Sony Xperia" to listOf(
            DeviceSpec("Sony Xperia", "Xperia 1 VI / 1 V", 120, 6.5f, 396, 800, 240, 12),
            DeviceSpec("Sony Xperia", "Xperia 5 V / 5 IV", 120, 6.1f, 449, 750, 240, 8),
            DeviceSpec("Sony Xperia", "Xperia 10 VI / 10 V", 60, 6.1f, 449, 600, 120, 8)
        ),
        "Nothing Phone" to listOf(
            DeviceSpec("Nothing Phone", "Nothing Phone (2)", 120, 6.7f, 393, 750, 240, 12),
            DeviceSpec("Nothing Phone", "Nothing Phone (2a) / 2a Plus", 120, 6.7f, 394, 720, 240, 12),
            DeviceSpec("Nothing Phone", "Nothing Phone (1)", 120, 6.55f, 402, 680, 240, 8),
            DeviceSpec("Nothing Phone", "CMF Phone 1", 120, 6.67f, 395, 660, 240, 8)
        ),
        "Black Shark" to listOf(
            DeviceSpec("Black Shark", "Black Shark 5 Pro", 144, 6.67f, 395, 900, 720, 16),
            DeviceSpec("Black Shark", "Black Shark 5 / 5 RS", 144, 6.67f, 395, 880, 720, 12),
            DeviceSpec("Black Shark", "Black Shark 4S Pro / 4", 144, 6.67f, 395, 850, 720, 12)
        ),
        "Lenovo (Legion)" to listOf(
            DeviceSpec("Lenovo (Legion)", "Legion Y90 (Dual Fan)", 144, 6.92f, 388, 920, 720, 18),
            DeviceSpec("Lenovo (Legion)", "Legion Duel 2 / Phone Duel", 144, 6.92f, 388, 900, 720, 16)
        ),
        "Meizu" to listOf(
            DeviceSpec("Meizu", "Meizu 21 Pro / 21", 120, 6.79f, 412, 800, 360, 16),
            DeviceSpec("Meizu", "Meizu 20 Pro / 20 Infinity", 120, 6.81f, 512, 780, 360, 12)
        ),
        "ZTE" to listOf(
            DeviceSpec("ZTE", "ZTE Axon 60 Ultra / 50 Ultra", 120, 6.78f, 452, 780, 360, 16),
            DeviceSpec("ZTE", "ZTE Blade V50 / V40", 90, 6.6f, 395, 580, 180, 6)
        ),
        "TCL" to listOf(
            DeviceSpec("TCL", "TCL 50 Pro NXTPAPER 5G", 120, 6.8f, 396, 680, 240, 8),
            DeviceSpec("TCL", "TCL 40 SE / 403", 90, 6.75f, 260, 540, 180, 6)
        ),
        "Nokia" to listOf(
            DeviceSpec("Nokia", "Nokia G42 5G / G60", 90, 6.56f, 269, 580, 180, 6),
            DeviceSpec("Nokia", "Nokia XR21 (Rugged)", 120, 6.49f, 406, 620, 240, 6),
            DeviceSpec("Nokia", "Nokia C32 / C22", 60, 6.5f, 270, 500, 120, 4)
        ),
        "HMD" to listOf(
            DeviceSpec("HMD", "HMD Skyline (Repairable)", 144, 6.55f, 402, 750, 360, 12),
            DeviceSpec("HMD", "HMD Pulse Pro / Pulse", 90, 6.65f, 265, 540, 180, 6)
        ),
        "LG" to listOf(
            DeviceSpec("LG", "LG V60 ThinQ 5G", 60, 6.8f, 395, 600, 180, 8),
            DeviceSpec("LG", "LG Velvet 5G / Wing", 60, 6.8f, 395, 580, 180, 8),
            DeviceSpec("LG", "LG G8X / G8 ThinQ", 60, 6.4f, 403, 580, 120, 6)
        ),
        "HTC" to listOf(
            DeviceSpec("HTC", "HTC U24 Pro / U23 Pro", 120, 6.8f, 387, 720, 240, 12),
            DeviceSpec("HTC", "HTC Desire 22 Pro", 120, 6.6f, 400, 660, 180, 8)
        ),
        "Sharp (Aquos)" to listOf(
            DeviceSpec("Sharp (Aquos)", "Aquos R9 / R8 Pro (240Hz)", 240, 6.6f, 453, 900, 480, 12),
            DeviceSpec("Sharp (Aquos)", "Aquos Sense 8 / Sense 7", 90, 6.1f, 437, 620, 180, 6)
        ),
        "Kyocera" to listOf(
            DeviceSpec("Kyocera", "DuraForce Pro 3 (Rugged)", 60, 5.38f, 410, 580, 120, 6),
            DeviceSpec("Kyocera", "Torque G06 5G", 120, 5.4f, 437, 620, 180, 6)
        ),
        "Fujitsu (Arrows)" to listOf(
            DeviceSpec("Fujitsu (Arrows)", "Arrows N F-51C 5G", 90, 6.24f, 424, 600, 180, 8),
            DeviceSpec("Fujitsu (Arrows)", "Arrows We / We2 Plus", 60, 5.7f, 295, 520, 120, 4)
        ),
        "Umidigi" to listOf(
            DeviceSpec("Umidigi", "Umidigi A15 Pro 5G / A15", 90, 6.7f, 396, 620, 180, 8),
            DeviceSpec("Umidigi", "Umidigi Bison GT2 5G", 90, 6.5f, 405, 600, 180, 8),
            DeviceSpec("Umidigi", "Umidigi G5 Mecha", 90, 6.6f, 269, 540, 120, 8)
        ),
        "Oukitel" to listOf(
            DeviceSpec("Oukitel", "Oukitel WP30 Pro (5G Rugged)", 120, 6.78f, 396, 680, 240, 12),
            DeviceSpec("Oukitel", "Oukitel WP19 / WP21", 120, 6.78f, 396, 650, 240, 8),
            DeviceSpec("Oukitel", "Oukitel C36 / C35", 90, 6.56f, 269, 520, 120, 6)
        ),
        "Blackview" to listOf(
            DeviceSpec("Blackview", "Blackview BL9000 5G Dual Screen", 120, 6.78f, 396, 700, 240, 12),
            DeviceSpec("Blackview", "Blackview BV9300 / BV8900", 120, 6.7f, 388, 640, 180, 12),
            DeviceSpec("Blackview", "Blackview Shark 8 / Color 8", 120, 6.78f, 396, 620, 240, 8)
        ),
        "Doogee" to listOf(
            DeviceSpec("Doogee", "Doogee V30 Pro 5G", 120, 6.58f, 401, 680, 240, 12),
            DeviceSpec("Doogee", "Doogee S110 / S100 Pro", 120, 6.58f, 401, 640, 240, 12),
            DeviceSpec("Doogee", "Doogee Blade 10 Ultra", 90, 6.56f, 269, 540, 180, 8)
        ),
        "Ulefone" to listOf(
            DeviceSpec("Ulefone", "Ulefone Armor 26 Ultra 5G", 120, 6.78f, 396, 720, 240, 12),
            DeviceSpec("Ulefone", "Ulefone Armor 23 Ultra", 120, 6.78f, 396, 700, 240, 12),
            DeviceSpec("Ulefone", "Ulefone Note 17 Pro", 120, 6.78f, 388, 650, 240, 12)
        ),
        "Cubot" to listOf(
            DeviceSpec("Cubot", "Cubot KingKong AX (Slim Rugged)", 120, 6.58f, 401, 640, 180, 12),
            DeviceSpec("Cubot", "Cubot Max 5 (Gaming 144Hz)", 144, 6.95f, 396, 740, 360, 12),
            DeviceSpec("Cubot", "Cubot P80 / X70", 90, 6.58f, 401, 580, 180, 8)
        ),
        "AGM Gaming" to listOf(
            DeviceSpec("AGM Gaming", "AGM Glory G1 Pro 5G", 60, 6.53f, 395, 620, 180, 8),
            DeviceSpec("AGM Gaming", "AGM H6 / H5 Pro", 90, 6.56f, 269, 560, 180, 8)
        ),
        "Cat (Caterpillar)" to listOf(
            DeviceSpec("Cat (Caterpillar)", "Cat S75 5G Satellite", 120, 6.58f, 401, 620, 180, 6),
            DeviceSpec("Cat (Caterpillar)", "Cat S62 Pro / S53", 60, 5.7f, 424, 560, 120, 6)
        ),
        "Fairphone" to listOf(
            DeviceSpec("Fairphone", "Fairphone 5 5G", 90, 6.46f, 459, 640, 180, 8),
            DeviceSpec("Fairphone", "Fairphone 4", 60, 6.3f, 409, 580, 120, 6)
        ),
        "BQ" to listOf(
            DeviceSpec("BQ", "BQ Aquaris X2 Pro / X2", 60, 5.65f, 427, 560, 120, 4),
            DeviceSpec("BQ", "BQ Aquaris X Pro", 60, 5.2f, 424, 540, 120, 4)
        ),
        "Wiko" to listOf(
            DeviceSpec("Wiko", "Wiko 10 / T50", 60, 6.74f, 260, 520, 120, 6),
            DeviceSpec("Wiko", "Wiko Power U30 / View 5", 60, 6.82f, 264, 500, 120, 4)
        ),
        "Archos" to listOf(
            DeviceSpec("Archos", "Archos Diamond / Oxygen", 60, 6.39f, 403, 540, 120, 4),
            DeviceSpec("Archos", "Archos Core 55 / Sense", 60, 5.5f, 267, 480, 120, 3)
        ),
        "Micromax" to listOf(
            DeviceSpec("Micromax", "Micromax IN Note 2 / 1", 60, 6.43f, 409, 560, 120, 4),
            DeviceSpec("Micromax", "Micromax IN 2b / 2c", 60, 6.52f, 269, 500, 120, 4)
        ),
        "Lava" to listOf(
            DeviceSpec("Lava", "Lava Agni 2 5G (Curved)", 120, 6.78f, 388, 700, 240, 8),
            DeviceSpec("Lava", "Lava Blaze Curve 5G", 120, 6.67f, 395, 680, 240, 8),
            DeviceSpec("Lava", "Lava Yuva 3 Pro / Storm", 90, 6.56f, 269, 540, 180, 6)
        ),
        "Karbonn" to listOf(
            DeviceSpec("Karbonn", "Karbonn Titanium Frames S9", 60, 5.45f, 295, 480, 120, 3),
            DeviceSpec("Karbonn", "Karbonn Vue 1", 60, 5.34f, 201, 460, 120, 2)
        ),
        "Symphony" to listOf(
            DeviceSpec("Symphony", "Symphony Helio 80 / 50", 90, 6.7f, 395, 580, 180, 6),
            DeviceSpec("Symphony", "Symphony Z60 / Z47", 90, 6.6f, 269, 520, 120, 4)
        ),
        "Walton" to listOf(
            DeviceSpec("Walton", "Walton Primo S8 Pro / S8", 90, 6.78f, 396, 600, 180, 6),
            DeviceSpec("Walton", "Walton Primo RX9 / R10", 90, 6.53f, 395, 560, 180, 4)
        ),
        "Casper" to listOf(
            DeviceSpec("Casper", "Casper VIA X40 / X30 Plus", 120, 6.67f, 395, 660, 240, 8),
            DeviceSpec("Casper", "Casper VIA M35 / M30", 90, 6.5f, 269, 540, 120, 4)
        ),
        "General Mobile" to listOf(
            DeviceSpec("General Mobile", "GM 24 Pro / 23 SE", 120, 6.7f, 394, 660, 240, 8),
            DeviceSpec("General Mobile", "GM Phoenix 5G", 120, 6.78f, 388, 700, 240, 8)
        ),
        "Vestel" to listOf(
            DeviceSpec("Vestel", "Vestel Venus Z40 / Z30", 60, 6.35f, 396, 540, 120, 4),
            DeviceSpec("Vestel", "Vestel Venus V7 / V6", 60, 6.22f, 270, 500, 120, 3)
        ),
        "Condor" to listOf(
            DeviceSpec("Condor", "Condor Allure M3 / M2", 60, 6.2f, 402, 540, 120, 4),
            DeviceSpec("Condor", "Condor Plume P8 Pro / P6", 60, 5.5f, 267, 480, 120, 3)
        ),
        "Evertek" to listOf(
            DeviceSpec("Evertek", "Evertek EverStar Pro / Plus", 60, 5.5f, 267, 480, 120, 2),
            DeviceSpec("Evertek", "Evertek V9 / V8", 60, 5.7f, 282, 500, 120, 3)
        ),
        "Bmobile" to listOf(
            DeviceSpec("Bmobile", "Bmobile AX1095 / AX1082", 60, 5.5f, 267, 480, 120, 2),
            DeviceSpec("Bmobile", "Bmobile BL50 Pro", 60, 6.0f, 269, 500, 120, 3)
        ),
        "Lanix" to listOf(
            DeviceSpec("Lanix", "Lanix Alpha 9V / 95", 90, 6.52f, 269, 540, 120, 4),
            DeviceSpec("Lanix", "Lanix Ilium M9V / L950", 60, 6.0f, 269, 500, 120, 3)
        ),
        "BLU" to listOf(
            DeviceSpec("BLU", "BLU Bold N3 / N2 5G", 120, 6.78f, 396, 680, 240, 8),
            DeviceSpec("BLU", "BLU G93 / G91 Pro", 90, 6.8f, 396, 600, 180, 6),
            DeviceSpec("BLU", "BLU F92e 5G / View 4", 90, 6.5f, 269, 540, 120, 4)
        ),
        "Positivo" to listOf(
            DeviceSpec("Positivo", "Positivo Twist 5 Pro / 4", 60, 6.26f, 269, 500, 120, 2),
            DeviceSpec("Positivo", "Positivo Vision R15", 90, 6.5f, 269, 540, 120, 4)
        ),
        "Multilaser" to listOf(
            DeviceSpec("Multilaser", "Multilaser G Max 2 / G Pro", 60, 6.5f, 269, 520, 120, 4),
            DeviceSpec("Multilaser", "Multilaser F 2 / E", 60, 5.5f, 215, 460, 120, 2)
        ),
        "LeEco" to listOf(
            DeviceSpec("LeEco", "LeEco Le Pro3 / Le Max 2", 60, 5.7f, 515, 600, 120, 6),
            DeviceSpec("LeEco", "LeEco Le 2 / Cool 1", 60, 5.5f, 403, 560, 120, 4)
        ),
        "Coolpad" to listOf(
            DeviceSpec("Coolpad", "Coolpad Cool 20 Pro 5G", 120, 6.58f, 401, 640, 180, 8),
            DeviceSpec("Coolpad", "Coolpad Legacy / CP3705A", 60, 6.36f, 395, 520, 120, 3)
        ),
        "Gionee" to listOf(
            DeviceSpec("Gionee", "Gionee M30 / M12 Pro", 60, 6.55f, 269, 540, 120, 8),
            DeviceSpec("Gionee", "Gionee Marathon M5 / S11", 60, 5.99f, 403, 520, 120, 4)
        ),
        "Smartisan" to listOf(
            DeviceSpec("Smartisan", "Smartisan Nut R2 5G", 90, 6.67f, 387, 720, 180, 12),
            DeviceSpec("Smartisan", "Smartisan Nut Pro 3", 60, 6.39f, 403, 620, 120, 8)
        ),
        "Essential" to listOf(
            DeviceSpec("Essential", "Essential Phone PH-1 (Titanium)", 60, 5.71f, 504, 600, 120, 4)
        ),
        "Razer Phone" to listOf(
            DeviceSpec("Razer Phone", "Razer Phone 2 (UltraMotion 120Hz)", 120, 5.72f, 513, 850, 240, 8),
            DeviceSpec("Razer Phone", "Razer Phone 1 (120Hz)", 120, 5.72f, 513, 800, 240, 8)
        ),
        "Unihertz" to listOf(
            DeviceSpec("Unihertz", "Unihertz Tank 3 Pro (Projector 5G)", 120, 6.79f, 396, 750, 240, 16),
            DeviceSpec("Unihertz", "Unihertz Jelly Star / Titan Pocket", 60, 3.03f, 330, 480, 120, 8)
        ),
        "Autre / Modèle Personnalisé" to listOf(
            DeviceSpec("Autre", "Smartphone Gaming 144Hz+", 144, 6.78f, 395, 850, 360, 12),
            DeviceSpec("Autre", "Smartphone Écran Fluide 120Hz", 120, 6.67f, 395, 720, 240, 8),
            DeviceSpec("Autre", "Smartphone Équilibré 90Hz", 90, 6.5f, 390, 600, 180, 6),
            DeviceSpec("Autre", "Smartphone Standard 60Hz", 60, 6.3f, 320, 520, 120, 4)
        )
    )

    fun getModelsForBrand(brand: String): List<DeviceSpec> {
        val directMatch = PRESET_DEVICES[brand]
        if (directMatch != null) return directMatch

        val partialMatch = PRESET_DEVICES.entries.firstOrNull {
            it.key.contains(brand, ignoreCase = true) || brand.contains(it.key, ignoreCase = true)
        }?.value

        return partialMatch ?: PRESET_DEVICES["Autre / Modèle Personnalisé"] ?: emptyList()
    }
}
