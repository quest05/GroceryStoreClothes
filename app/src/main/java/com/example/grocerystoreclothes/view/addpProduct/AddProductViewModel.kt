package com.example.grocerystoreclothes.view.addpProduct

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grocerystoreclothes.model.CreatedAt
import com.example.grocerystoreclothes.model.Id
import com.example.grocerystoreclothes.model.Products
import com.example.grocerystoreclothes.model.SubCategory
import com.example.grocerystoreclothes.model.UpdatedAt
import com.example.grocerystoreclothes.model.entity.StoreCategory
import com.example.grocerystoreclothes.model.entity.StoreProduct
import com.example.grocerystoreclothes.model.entity.StoreSubCategory
import com.example.grocerystoreclothes.roomdb.MyDefaultProductDb
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val db: MyDefaultProductDb
) : ViewModel() {

    val storeCategoryList = MutableLiveData<List<StoreCategory>>()
    val storeSubCategoryList = MutableLiveData<List<StoreSubCategory>>()

    val selectedSubCatList = MutableLiveData<List<StoreSubCategory>>()

    val successMsg = MutableLiveData<String>()

    init {
        getDbAllCategory()
        Log.e("TAG", ": init in vm ")
    }

    private fun getDbAllCategory() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Log.e("TAG", ": post data of category in vm " + db.storeCategoryDao().getAllStoreCategories())

                storeCategoryList.postValue(db.storeCategoryDao().getAllStoreCategories())
                storeSubCategoryList.postValue(db.storeSubCategoryDao().getAllStoreSubCategories())
            }
        }
    }


    fun getSelectedStoreSubCategories(subCategoryIds: List<SubCategory>) {
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

    }

    fun addDBNewProduct(productPrice : String, subCatPosition: Int, txtProduct: String) {

        val newProductId = generateUniqueId()
        val productId = Id(oid = newProductId)
        val created = CreatedAt(date = getCurrentDateTime())
        val updated = UpdatedAt(date = getCurrentDateTime())
        val data = StoreProduct(Id = productId, productId = "Test-01", name = txtProduct, price = productPrice.toInt(), image = null,
            rate = "", remark = "", createdAt = created, updatedAt = updated)

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val obj = db.storeSubCategoryDao().getObjectById(selectedSubCatList.value?.get(subCatPosition)?.Id!!)
                val productList = obj?.products?.toMutableList()
                productList?.add(Products(newProductId))

                if (productList != null) {
                    try {
                        db.storeProductDao().insertProduct(data)
                        db.storeSubCategoryDao().updateProducts(productList, selectedSubCatList.value?.get(subCatPosition)?.Id!!)
                        successMsg.postValue("Product added successfully...!")
                    }catch (e : Exception){
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private fun generateUniqueId(): String {
        val uuid = UUID.randomUUID()
        return uuid.toString()
    }
    private fun getCurrentDateTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val currentDate = Date()
        return dateFormat.format(currentDate)
    }
}