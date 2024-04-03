package com.example.grocerystoreclothes.view.createUser

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import com.example.grocerystoreclothes.model.userDetail.UserModel
import com.example.grocerystoreclothes.preferences.MyPreference
import com.example.grocerystoreclothes.roomdb.MyDefaultProductDb
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateUserAdminViewModel  @Inject constructor(
    private val db: MyDefaultProductDb,
    private val myPreference: MyPreference
) : ViewModel() {

    fun addUsersToPreferenceList(context: Context, newUsers: UserModel): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val gson = Gson()

        // Retrieve the existing list of users from SharedPreferences
        val existingUserListJson = sharedPreferences.getString("user_list", null)
        val existingUserListType = object : TypeToken<List<UserModel>>() {}.type
        var existingUserList = gson.fromJson<List<UserModel>>(existingUserListJson, existingUserListType)

        // If the existing list is null, create a new list
        if (existingUserList == null) {
            existingUserList = ArrayList()
        }

        existingUserList = existingUserList + newUsers

        Log.e("TAG", "addUsersToPreferenceList: existingUserList $existingUserList " )
        val updatedUserListJson = gson.toJson(existingUserList)

        // Save the updated list back to SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putString("user_list", updatedUserListJson)
        editor.apply()

        return true

    }




}