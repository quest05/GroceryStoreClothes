package com.example.grocerystoreclothes.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grocerystoreclothes.model.Products
import com.example.grocerystoreclothes.model.entity.StoreCategory
import com.example.grocerystoreclothes.model.entity.StoreProduct
import com.example.grocerystoreclothes.model.entity.StoreSubCategory
import com.example.grocerystoreclothes.roomdb.MyDefaultProductDb
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val db: MyDefaultProductDb
) : ViewModel() {

    val storeCategoryList = MutableLiveData<List<StoreCategory>>()
    val storeSubCategoryList = MutableLiveData<List<StoreSubCategory>>()
    val productsList = MutableLiveData<List<StoreProduct>>()

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
//                Log.e("TAG", "see storeSubCategoryDao: ${db.storeSubCategoryDao().getAllStoreSubCategories()} ")
                Log.e("TAG", "see storeProductDao: ${db.storeProductDao().getAllStoreProducts()} ")
            }
        }
    }

    fun getAllCategory() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                storeCategoryList.postValue(db.storeCategoryDao().getAllStoreCategories())
            }
        }
    }
    fun getAllSubCategory() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                storeSubCategoryList.postValue(db.storeSubCategoryDao().getAllStoreSubCategories())
            }
        }
    }
    fun getAllProducts() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                productsList.postValue(db.storeProductDao().getAllStoreProducts())
            }
        }
    }
}