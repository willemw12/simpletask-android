/*
 * Copyright (c) 2017 Stephen Michel <s@smichel.me>
 * SPDX-License-Identifier: GPL-3.0+
 */

@file:Suppress("unused")
package me.smichel.android.KPreferences

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

private typealias Callback<T> = (T) -> Unit

// These are to cut down on verbosity in Preference subclasses.
// For clarity, they should not be used elsewhere.
private typealias B = Boolean
private typealias L = Long
private typealias I = Int
private typealias S = String
private typealias SS = Set<String>

abstract class Preferences : SharedPreferences.OnSharedPreferenceChangeListener {
    val prefs: SharedPreferences
    val context: Context

    constructor(context: Context) {
        this.context = context
        prefs = PreferenceManager.getDefaultSharedPreferences(context)
    }

    constructor(context: Context, name: String, mode: Int = Context.MODE_PRIVATE) {
        this.context = context
        prefs = context.getSharedPreferences(name, mode)
    }

    private val callbacks = mutableMapOf<String, () -> Unit>()

    var listeningForChanges: Boolean = false
        set(value) {
            if (value != field) {
                field = value
                if (value) {
                    prefs.registerOnSharedPreferenceChangeListener(this)
                } else {
                    prefs.unregisterOnSharedPreferenceChangeListener(this)
                }
            }
        }

    protected fun registerCallback(key: String, onChange: () -> Unit) {
        callbacks[key] = onChange
        listeningForChanges = true
    }

    protected fun registerCallbacks(keys: List<String>, onChange: () -> Unit) {
        keys.forEach { key ->
            registerCallback(key, onChange)
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        callbacks[key]?.invoke()
    }

    private fun str(resId: Int): String = context.getString(resId)

    inner abstract class Preference<T: Any?>(inline val key: String, inline val default: T) : ReadWriteProperty<Any?, T> {
        constructor(resId: Int, default: T) : this(str(resId), default)
        constructor(key: String, default: T, onChange: Callback<T>) : this(key, default) {
            registerCallback(key){ onChange(prefValue) }
        }
        constructor(resId: Int, default: T, onChange: Callback<T>) : this(str(resId), default, onChange)

        protected abstract var prefValue: T

        override fun getValue(thisRef: Any?, property: KProperty<*>) = prefValue

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            prefValue = value
        }
    }

    inner class BooleanPreference : Preference<B> {
        constructor(resId: Int, default: B) : super(resId, default)
        constructor(key: String, default: B) : super(key, default)
        constructor(key: String, default: B, onChange: Callback<B>) : super(key, default, onChange)
        constructor(resId: Int, default: B, onChange: Callback<B>) : super(resId, default, onChange)

        override var prefValue: B
            get() = prefs.getBoolean(key, default)
            set(value) = prefs.edit().putBoolean(key, value).apply()
    }

    inner class IntPreference : Preference<I> {
        constructor(key: String, default: I) : super(key, default)
        constructor(resId: I, default: I) : super(resId, default)
        constructor(key: String, default: I, onChange: Callback<I>) : super(key, default, onChange)
        constructor(resId: I, default: I, onChange: Callback<I>) : super(resId, default, onChange)

        override var prefValue: I
            get() = prefs.getInt(key, default)
            set(value) = prefs.edit().putInt(key, value).apply()
    }

    inner class LongPreference : Preference<L> {
        constructor(key: String, default: L) : super(key, default)
        constructor(resId: Int, default: L) : super(resId, default)
        constructor(key: String, default: L, onChange: Callback<L>) : super(key, default, onChange)
        constructor(resId: Int, default: L, onChange: Callback<L>) : super(resId, default, onChange)

        override var prefValue: L
            get() = prefs.getLong(key, default)
            set(value) = prefs.edit().putLong(key, value).apply()
    }

    inner class StringPreference : Preference<S> {
        constructor(key: String, default: S) : super(key, default)
        constructor(resId: Int, default: S) : super(resId, default)
        constructor(key: String, default: S, onChange: Callback<S>) : super(key, default, onChange)
        constructor(resId: Int, default: S, onChange: Callback<S>) : super(resId, default, onChange)

        override var prefValue: S
            get() = prefs.getString(key, default)
            set(value) = prefs.edit().putString(key, value).apply()
    }

    inner class StringSetPreference : Preference<SS> {
        constructor(key: String, default: SS) : super(key, default)
        constructor(resId: Int, default: SS) : super(resId, default)
        constructor(key: String, default: SS, onChange: Callback<SS>) : super(key, default, onChange)
        constructor(resId: Int, default: SS, onChange: Callback<SS>) : super(resId, default, onChange)

        override var prefValue: SS
            get() = prefs.getStringSet(key, default)
            set(value) = prefs.edit().putStringSet(key, value).apply()
    }

    inner class StringOrNullPreference : Preference<S?> {
        constructor(key: String, default: S? = null) : super(key, default)
        constructor(resId: Int, default: S? = null) : super(resId, default)
        constructor(key: String, default: S? = null, onChange: Callback<S?>) : super(key, default, onChange)
        constructor(resId: Int, default: S? = null, onChange: Callback<S?>) : super(resId, default, onChange)

        override var prefValue: S?
            get() = prefs.getString(key, default)
            set(value) = prefs.edit().putString(key, value).apply()
    }

    inner class StringSetOrNullPreference : Preference<SS?> {
        constructor(key: String, default: SS? = null) : super(key, default)
        constructor(resId: Int, default: SS? = null) : super(resId, default)
        constructor(key: String, default: SS? = null, onChange: Callback<SS?>) : super(key, default, onChange)
        constructor(resId: Int, default: SS? = null, onChange: Callback<SS?>) : super(resId, default, onChange)

        override var prefValue: SS?
            get() = prefs.getStringSet(key, default)
            set(value) = prefs.edit().putStringSet(key, value).apply()
    }
}
