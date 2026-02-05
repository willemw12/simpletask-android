package nl.mpcjanssen.simpletask

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import java.util.*

fun Activity.setAppearanceStatusBars() {
    val isDarkTheme = TodoApplication.config.isDarkTheme || TodoApplication.config.isBlackTheme
    WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = !isDarkTheme
}

private fun Activity.applyLocale() {
    if (TodoApplication.config.forceEnglish) {
        val conf = resources.configuration
        @Suppress("DEPRECATION")
        conf.locale = Locale.ENGLISH
        @Suppress("DEPRECATION")
        resources.updateConfiguration(conf, resources.displayMetrics)
    }
}

abstract class ThemedNoActionBarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(TodoApplication.config.activeTheme)
        setAppearanceStatusBars()
        applyLocale()
        super.onCreate(savedInstanceState)
    }
}

abstract class ThemedActionBarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(TodoApplication.config.activeActionBarTheme)
        setAppearanceStatusBars()
        applyLocale()
        super.onCreate(savedInstanceState)
    }
}

abstract class ThemedPreferenceActivity : AppCompatPreferenceActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(TodoApplication.config.activeActionBarTheme)
        setAppearanceStatusBars()
        applyLocale()
        super.onCreate(savedInstanceState)
    }
}
