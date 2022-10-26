package vadiole.quicktiles.tile.adb

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.service.quicksettings.TileService.ACTION_QS_TILE_PREFERENCES

class WirelessDebugLauncherActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // https://android.googlesource.com/platform/packages/apps/Settings/+/master/src/com/android/settings/development/DevelopmentSettingsDashboardFragment.java#229
        val settingsPackage = "com.android.settings"
        val tileComponent = ComponentName(settingsPackage, "$settingsPackage.${getTileName()}")
        val settingsComponent = ComponentName(settingsPackage, "$settingsPackage.Settings\$DevelopmentSettingsDashboardActivity")
        val intent = Intent(ACTION_QS_TILE_PREFERENCES)
            .setComponent(settingsComponent)
            .putExtra(Intent.EXTRA_COMPONENT_NAME, tileComponent)
        startActivity(intent)
        finish()
    }

    private fun getTileName(): String {
        val isOnePlus = Build.BRAND.contains("oneplus", ignoreCase = true)
        return if (isOnePlus) {
            "development.qstile.DevelopmentTiles\$b"
        } else {
            "development.qstile.DevelopmentTiles\$WirelessDebugging"
        }
    }
}
