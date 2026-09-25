package com.example.util

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import com.example.data.model.DeviceCatalog
import com.example.data.model.DeviceSpec

object DeviceDetector {

    data class DetectedDeviceInfo(
        val manufacturer: String,
        val model: String,
        val androidVersion: String,
        val refreshRateHz: Int,
        val densityDpi: Int,
        val screenInches: Float,
        val ramGb: Int,
        val matchedSpec: DeviceSpec
    )

    fun detect(context: Context): DetectedDeviceInfo {
        val rawManufacturer = Build.MANUFACTURER.replaceFirstChar { it.uppercase() }
        val rawModel = Build.MODEL
        val androidVersion = "Android ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})"

        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay

        val refreshRate = try {
            display.refreshRate.toInt().coerceAtLeast(60)
        } catch (_: Exception) {
            60
        }

        val metrics = DisplayMetrics()
        display.getRealMetrics(metrics)

        val densityDpi = metrics.densityDpi
        val widthInches = metrics.widthPixels.toFloat() / metrics.xdpi
        val heightInches = metrics.heightPixels.toFloat() / metrics.ydpi
        val screenInches = try {
            val calc = Math.sqrt((widthInches * widthInches + heightInches * heightInches).toDouble()).toFloat()
            if (calc in 4.0f..14.0f) calc else 6.67f
        } catch (_: Exception) {
            6.67f
        }

        val actManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memInfo = ActivityManager.MemoryInfo()
        actManager.getMemoryInfo(memInfo)
        val ramGb = ((memInfo.totalMem / (1024L * 1024L * 1024L)).toInt() + 1).coerceIn(2, 32)

        // Try to match against catalog
        var matchedSpec: DeviceSpec? = null
        val brandKeys = DeviceCatalog.PRESET_DEVICES.keys

        for (brandKey in brandKeys) {
            if (rawManufacturer.contains(brandKey, ignoreCase = true) || brandKey.contains(rawManufacturer, ignoreCase = true)) {
                val models = DeviceCatalog.PRESET_DEVICES[brandKey] ?: emptyList()
                matchedSpec = models.firstOrNull {
                    rawModel.contains(it.model, ignoreCase = true) || it.model.contains(rawModel, ignoreCase = true)
                } ?: models.firstOrNull()
                break
            }
        }

        if (matchedSpec == null) {
            matchedSpec = DeviceSpec(
                brand = rawManufacturer.ifBlank { "Appareil Universel" },
                model = rawModel.ifBlank { "Smartphone Android" },
                refreshRateHz = refreshRate,
                screenInch = screenInches,
                stockDpi = densityDpi,
                recommendedSafeMaxDpi = (densityDpi + 260).coerceAtMost(960),
                touchSamplingHz = if (refreshRate >= 120) 240 else 180,
                ramGb = ramGb
            )
        }

        return DetectedDeviceInfo(
            manufacturer = rawManufacturer,
            model = rawModel,
            androidVersion = androidVersion,
            refreshRateHz = refreshRate,
            densityDpi = densityDpi,
            screenInches = String.format("%.2f", screenInches).toFloatOrNull() ?: 6.67f,
            ramGb = ramGb,
            matchedSpec = matchedSpec
        )
    }
}
