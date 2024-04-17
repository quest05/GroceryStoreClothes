package com.example.grocerystoreclothes.view.setting

import androidx.lifecycle.ViewModel
import com.example.grocerystoreclothes.preferences.MyPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewmodel @Inject constructor(
    private val myPreference: MyPreference
) : ViewModel() {

    fun isAdminLogIn(): Boolean {
        return myPreference.isAdminLogin()
    }

    fun adminLogOut(isLogout : Boolean) {
        myPreference.setAdminLogin(isLogout)
    }
}