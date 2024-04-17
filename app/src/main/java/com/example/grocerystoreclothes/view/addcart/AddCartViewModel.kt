package com.example.grocerystoreclothes.view.addcart

import androidx.lifecycle.ViewModel
import com.example.grocerystoreclothes.model.entity.StoreProduct
import com.example.grocerystoreclothes.view.home.MainViewModel.Companion.addCartProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddCartViewModel @Inject constructor() : ViewModel() {

//    val addCartProduct = MutableLiveData<List<StoreProduct>>(emptyList())
   /* fun addedAllProductCart(product: List<StoreProduct>) {
       *//* val currentList = addCartProduct.value.orEmpty().toMutableList()
        currentList.addAll(product)*//*
        addCartProduct.value = product
    }*/
    fun addNewProduct(product: StoreProduct) {
        val currentList = addCartProduct.value ?: emptyList()
        val existingProduct = currentList.find { it.Id.oid == product.Id.oid }

        if (existingProduct != null) {
            val updatedList = currentList.map {
                if (it.Id.oid == product.Id.oid) {
                    it.copy(cartCount = it.cartCount?.plus(1))
                } else {
                    it
                }
            }
            addCartProduct.value = updatedList
        } else {
            val newList = currentList.toMutableList()
            newList.add(product.copy(cartCount = 1))
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