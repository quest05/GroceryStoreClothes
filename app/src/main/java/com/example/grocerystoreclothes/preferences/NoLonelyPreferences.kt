package com.example.grocerystoreclothes.preferences

import android.content.Context
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyPreference @Inject constructor(@ApplicationContext context : Context){
    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    fun isAdminLogin(): Boolean {
        return prefs.getBoolean(PREF_TAG, false)
    }
    fun setAdminLogin(query: Boolean) {
        prefs.edit().putBoolean(PREF_TAG, query).apply()
    }

    companion object {
        private const val PREF_TAG = "tag"
    }
}
