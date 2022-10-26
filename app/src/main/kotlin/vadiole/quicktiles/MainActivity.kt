package vadiole.quicktiles

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.TypedValue
import android.view.Gravity
import android.view.HapticFeedbackConstants
import android.view.MotionEvent
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.ViewCompat.setOnApplyWindowInsetsListener
import androidx.core.view.WindowCompat
import androidx.core.view.WindowCompat.setDecorFitsSystemWindows
import androidx.core.view.WindowInsetsCompat.Type.navigationBars
import androidx.core.view.WindowInsetsCompat.Type.statusBars

class MainActivity : Activity(), ResourcesOwner {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDecorFitsSystemWindows(window, false)
        setOnApplyWindowInsetsListener(window.decorView) { v, insets ->
            v.setPadding(
                0, insets.getInsets(statusBars()).top,
                0, insets.getInsets(navigationBars()).bottom
            )
            insets
        }

        setContentView(
            LinearLayout(this).apply {
                setPadding(24.dp, 24.dp, 24.dp, 44.dp)
                gravity = Gravity.BOTTOM
                orientation = LinearLayout.VERTICAL

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    addView(
                        TextView(context).apply {
                            layoutParams = LinearLayout.LayoutParams(matchParent, wrapContent)
                            setPadding(0, 0, 0, 24.dp)
                            gravity = Gravity.LEFT
                            setTextSize(TypedValue.COMPLEX_UNIT_PX, 16f.dp)
                            setTextColor(Color.GRAY)
                            text = "1. Go to Developer settings > Disable permission monitoring, and toggle it on"
                            setOnTouchListener { view, motionEvent ->
                                when (motionEvent.actionMasked) {
                                    MotionEvent.ACTION_DOWN -> {
                                        clearAnimation()
                                        animate()
                                            .setInterpolator(AccelerateDecelerateInterpolator())
                                            .scaleX(0.95f)
                                            .scaleY(0.95f)
                                            .setDuration(200)
                                            .withLayer()
                                            .start()
                                    }
                                    MotionEvent.ACTION_UP -> {
                                        clearAnimation()
                                        animate()
                                            .setInterpolator(AccelerateDecelerateInterpolator())
                                            .scaleX(1f)
                                            .scaleY(1f)
                                            .setDuration(200)
                                            .withLayer()
                                            .start()
                                    }
                                }
                                return@setOnTouchListener false
                            }
                            setOnClickListener {
                                startActivity(Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS))
                            }
                        }
                    )
                }

                addView(
                    TextView(context).apply {
                        layoutParams = LinearLayout.LayoutParams(matchParent, wrapContent)
                        setPadding(0, 0, 0, 8.dp)
                        gravity = Gravity.LEFT
                        setTextSize(TypedValue.COMPLEX_UNIT_PX, 16f.dp)
                        setTextColor(Color.GRAY)
                        text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            "2. Run in terminal:"
                        } else {
                            "Run in terminal:"
                        }
                    }
                )

                addView(
                    TextView(context).apply {
                        layoutParams = LinearLayout.LayoutParams(matchParent, wrapContent)
                        gravity = Gravity.LEFT
                        setTextColor(Color.GRAY)
                        paint.typeface = Typeface.MONOSPACE
                        paint.isSubpixelText = true
                        paint.isAntiAlias = true
                        setTextSize(TypedValue.COMPLEX_UNIT_PX, 14f.dp)
                        text = "adb shell pm grant --user 0 ${BuildConfig.APPLICATION_ID} android.permission.WRITE_SECURE_SETTINGS"
                        setOnTouchListener { view, motionEvent ->
                            when (motionEvent.actionMasked) {
                                MotionEvent.ACTION_DOWN -> {
                                    clearAnimation()
                                    animate()
                                        .setInterpolator(AccelerateDecelerateInterpolator())
                                        .scaleX(0.95f)
                                        .scaleY(0.95f)
                                        .setDuration(200)
                                        .withLayer()
                                        .start()
                                }
                                MotionEvent.ACTION_UP -> {
                                    clearAnimation()
                                    animate()
                                        .setInterpolator(AccelerateDecelerateInterpolator())
                                        .scaleX(1f)
                                        .scaleY(1f)
                                        .setDuration(200)
                                        .withLayer()
                                        .start()
                                }
                            }
                            return@setOnTouchListener false
                        }
                        setOnLongClickListener {
                            val systemService = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                            val myClip = ClipData.newPlainText("Adb command", text)
                            systemService.setPrimaryClip(myClip)
                            Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
                            performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                            true
                        }
                        setOnClickListener {
                            val systemService = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                            val myClip = ClipData.newPlainText("Adb command", text)
                            systemService.setPrimaryClip(myClip)
                            Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
                            performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                        }
                    }
                )
            }
        )
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        updateSystemBars(newConfig)
        window.decorView.setBackgroundColor(getColor(R.color.window_background))
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if (hasFocus) {
            updateSystemBars(resources.configuration)
        }
    }

    private fun updateSystemBars(configuration: Configuration) {
        val isNightMode = configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
        val insetsController = WindowCompat.getInsetsController(window, window.decorView)
        insetsController.isAppearanceLightStatusBars = !isNightMode
        insetsController.isAppearanceLightNavigationBars = !isNightMode
    }
}