package com.example.grocerystoreclothes.view.addcart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grocerystoreclothes.model.entity.StoreProduct
import com.example.grocerystoreclothes.preferences.MyPreference
import com.example.grocerystoreclothes.view.home.MainViewModel.Companion.addCartProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AddCartViewModel @Inject constructor(
    private val myPreference: MyPreference
) : ViewModel() {

        val billSrNumber = MutableLiveData<String>()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    //    val addCartProduct = MutableLiveData<List<StoreProduct>>(emptyList())
    /* fun addedAllProductCart(product: List<StoreProduct>) {
        *//* val currentList = addCartProduct.value.orEmpty().toMutableList()
        currentList.addAll(product)*//*
        addCartProduct.value = product
    }*/


    fun getSrNumber(isReturnProduct: Boolean) {
        resetIfNewDay()

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val currentBillNumber =
                    if (isReturnProduct) myPreference.getReturnBillNo() else myPreference.getBillNo()
                val nextBillNumber = currentBillNumber + 1

                val billNumber = if (isReturnProduct) String.format(
                    "RE-%04d",
                    nextBillNumber
                ) else String.format("%04d", nextBillNumber)

                withContext(Dispatchers.Main) {
                    billSrNumber.value = billNumber
                }

            }
        }
    }

    private fun resetIfNewDay() {
        val lastDate = myPreference.getLastDate()
        val currentDate = dateFormat.format(Date())

        if (lastDate != currentDate) {
            // It's a new day, reset the count
            myPreference.setReturnBillNo(0)
            myPreference.setBillNo(0)
            myPreference.setLastDate(currentDate)
        }
    }

    fun addNewProduct(product: StoreProduct, productCount: Int = 0) {
        val currentList = addCartProduct.value ?: emptyList()
        val existingProduct = currentList.find { it.Id.oid == product.Id.oid }

        if (existingProduct != null) {
            val updatedList = currentList.map {
                if (it.Id.oid == product.Id.oid) {
                    if (productCount >= 1) {
                        it.copy(cartCount = productCount)
                    } else {
                        it.copy(cartCount = it.cartCount?.plus(1))
                    }
                } else {
                    it
                }
            }
            addCartProduct.value = updatedList
        } else {
            val newList = currentList.toMutableList()
            newList.add(product.copy(cartCount = productCount))
            addCartProduct.value = newList
        }
    }

    fun returnNewProduct(product: StoreProduct, productCount: Int = 0) {
        val currentList = addCartProduct.value ?: emptyList()
        val existingProduct = currentList.find { it.Id.oid == product.Id.oid }

        if (existingProduct != null) {
            val updatedList = currentList.map {
                if (it.Id.oid == product.Id.oid) {

                    if (productCount >= 1) {
                        it.copy(cartCount = 0.minus(productCount))
                    } else {
                        it.copy(cartCount = it.cartCount?.minus(1))
                    }
                } else {
                    it
                }
            }
            addCartProduct.value = updatedList
        } else {
            val newList = currentList.toMutableList()
            newList.add(product.copy(cartCount = productCount))
            addCartProduct.value = newList

        }
    }

    fun removeProduct(product: StoreProduct) {

        val currentList = addCartProduct.value ?: emptyList()
        val existingProduct = currentList.find { it.Id.oid == product.Id.oid }

        if (existingProduct != null) {
            val updatedList = currentList.mapNotNull {
                if (it.Id.oid == product.Id.oid) {
                    if ((it.cartCount ?: 0) > 1) {
                        it.copy(cartCount = it.cartCount?.minus(1))
                    } else {
                        null
                    }
                } else {
                    it
                }
            }
            addCartProduct.value = updatedList
        }
    }
}