package com.example.grocerystoreclothes.view

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun insertDataBase(
        storeCategories: List<StoreCategory>,
        storeSubCategories: List<StoreSubCategory>,
        storeProducts: List<StoreProduct>
    ) {
        viewModelScope.launch {

            withContext(Dispatchers.IO) {
                db.storeCategoryDao().insertStoreCategory(storeCategories)
                db.storeSubCategoryDao().insertStoreSubCategory(storeSubCategories)
//                db.storeProductDao().insertStoreProduct(storeProducts)
                Log.e("TAG", "see all data $storeProducts")
                Log.e("TAG", "see storeSubCategoryDao: ${db.storeSubCategoryDao().getAllStoreSubCategories()} ")
//                Log.e("TAG", "see storeProductDao: ${db.storeProductDao().getAllStoreProducts()} ")
            }
        }
    }
}