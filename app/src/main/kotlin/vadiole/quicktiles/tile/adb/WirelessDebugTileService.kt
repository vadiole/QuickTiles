package vadiole.quicktiles.tile.adb

import vadiole.quicktiles.R
import vadiole.quicktiles.tile.ToggleTileService

class WirelessDebugTileService : ToggleTileService() {
    override val key: String = "adb_wifi_enabled"
    override val enabledDrawableRes: Int = R.drawable.ic_adb_enabled
    override val disabledDrawableRes: Int = R.drawable.ic_adb_disabled
    override val labelRes: Int = R.string.tile_wifi_debug
}