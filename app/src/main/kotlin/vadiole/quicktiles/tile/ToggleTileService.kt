package vadiole.quicktiles.tile

import android.graphics.drawable.Icon
import android.provider.Settings
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService

abstract class ToggleTileService : TileService() {
    abstract val key: String
    abstract val enabledDrawableRes: Int
    abstract val disabledDrawableRes: Int
    abstract val labelRes: Int
    private val disabledIcon get() = Icon.createWithResource(this, disabledDrawableRes)
    private val enabledIcon get() = Icon.createWithResource(this, enabledDrawableRes)

    private var enabled: Boolean
        get() = Settings.Global.getInt(contentResolver, key, Disabled) != Disabled
        set(value) {
            Settings.Global.putInt(contentResolver, key, if (value) Enabled else Disabled)
        }

    override fun onStartListening() {
        super.onStartListening()
        updateTile(enabled)
    }

    override fun onClick() {
        enabled = !enabled
        updateTile(enabled)
    }

    private fun updateTile(enabled: Boolean) {
        qsTile.apply {
            label = getString(labelRes)
            contentDescription = getString(labelRes)
            state = if (enabled) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
            icon = if (enabled) enabledIcon else disabledIcon
            updateTile()
        }
    }

    companion object {
        private const val Enabled = 1
        private const val Disabled = 0
    }
}