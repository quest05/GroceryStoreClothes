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
        return prefs.getBoolean("PREF_ADMIN_LOGIN", false)
    }
    fun setAdminLogin(query: Boolean) {
        prefs.edit().putBoolean("PREF_ADMIN_LOGIN", query).apply()
    }
    fun setBillNo(query: Int) {
        prefs.edit().putInt("BILL_NUMBER", query).apply()
    }
    fun getBillNo(): Int {
        return prefs.getInt("BILL_NUMBER", 0)
    }
}
