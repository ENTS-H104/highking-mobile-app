package com.entsh104.highking.data.source.local

import android.content.Context

class SharedPreferencesManager(context: Context) {

    private val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        prefs.edit().putString("token", token).apply()
    }

    fun getToken(): String? {
        return prefs.getString("token", null)
    }

    fun clear() {
        prefs.edit().clear().apply()
    }
}
