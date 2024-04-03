package com.example.grocerystoreclothes.view.signup

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import com.example.grocerystoreclothes.model.entity.StoreCategory
import com.example.grocerystoreclothes.model.entity.StoreProduct
import com.example.grocerystoreclothes.model.entity.StoreSubCategory
import com.example.grocerystoreclothes.model.userDetail.UserModel
import com.example.grocerystoreclothes.preferences.MyPreference
import com.example.grocerystoreclothes.roomdb.MyDefaultProductDb
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignInViewmodel @Inject constructor(
    private val db: MyDefaultProductDb,
    private val myPreference: MyPreference

) : ViewModel() {

    init {
        Log.e("TAG", ": admin login  " + myPreference.isAdminLogin())
    }

    fun insertDataBase(
        storeCategories: List<StoreCategory>,
        storeSubCategories: List<StoreSubCategory>,
        storeProducts: List<StoreProduct>
    ) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.storeCategoryDao().insertStoreCategory(storeCategories)
                db.storeSubCategoryDao().insertStoreSubCategory(storeSubCategories)
                db.storeProductDao().insertStoreProduct(storeProducts)
            }
        }
    }

    fun setAdminPreference(isLogin: Boolean) {
        myPreference.setAdminLogin(isLogin)
        Log.e("TAG", ": admin login  $isLogin")
    }

    fun isAdminLogin(): Boolean {
        return myPreference.isAdminLogin()
    }

    fun checkCredentials(context: Context, email: String, password: String): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val gson = Gson()

        // Retrieve the list of users from SharedPreferences
        val userListJson = sharedPreferences.getString("user_list", null)
        val userListType = object : TypeToken<List<UserModel>>() {}.type
        val userList = gson.fromJson<List<UserModel>>(userListJson, userListType)

        Log.e("TAG", "checkCredentials : userList $userList")
        // Iterate through the user list to find a matching user
        userList?.let { users ->
            for (user in users) {
                if (user.email == email && user.password == password) {
                    return true // Username and password match
                }
            }
        }

        return false // No matching user found
    }
}