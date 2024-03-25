package com.example.grocerystoreclothes.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grocerystoreclothes.model.Products
import com.example.grocerystoreclothes.model.SubCategory
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
    private val storeSubCategoryList = MutableLiveData<List<StoreSubCategory>>()
    private val productsList = MutableLiveData<List<StoreProduct>>()

    val selectedSubCatList = MutableLiveData<List<StoreSubCategory>>()
    val selectedProductList = MutableLiveData<List<StoreProduct>>()

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

    fun getDbAllCategory() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                storeCategoryList.postValue(db.storeCategoryDao().getAllStoreCategories())
                storeSubCategoryList.postValue(db.storeSubCategoryDao().getAllStoreSubCategories())
                productsList.postValue(db.storeProductDao().getAllStoreProducts())
            }
        }
    }

    fun getSelectedStoreSubCategories(subCategoryIds: List<SubCategory>): List<StoreSubCategory> {
        val matchingSubCategories = mutableListOf<StoreSubCategory>()

        for (subCategoryId in subCategoryIds) {
            for (storeSubCategory in storeSubCategoryList.value.orEmpty()) {
                if (storeSubCategory.Id.oid == subCategoryId.oid) {
                    matchingSubCategories.add(storeSubCategory)
                    continue
                }
            }
        }
        selectedSubCatList.postValue(matchingSubCategories)

        return matchingSubCategories
    }

    fun getSelectedProductSubCat(productsId: List<Products>): List<StoreProduct> {
        val matchingList = mutableListOf<StoreProduct>()

        for (id in productsId) {
            for (productsList in productsList.value.orEmpty()) {
                if (productsList.Id.oid == id.oid) {
                    matchingList.add(productsList)
                    continue
                }
            }
        }
        selectedProductList.postValue(matchingList)

        return matchingList
    }
}