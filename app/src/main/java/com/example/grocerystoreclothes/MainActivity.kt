package com.example.grocerystoreclothes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.grocerystoreclothes.databinding.ActivityMainBinding
import com.example.grocerystoreclothes.view.MainViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.activity.viewModels
import com.example.grocerystoreclothes.model.entity.StoreCategory
import com.example.grocerystoreclothes.model.entity.StoreProduct
import com.example.grocerystoreclothes.model.entity.StoreSubCategory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val assetManager = resources.assets
        val inputStreamStoreCat = assetManager.open("store_categories.json")
        val inputStreamStoreSubCat = assetManager.open("store_sub_categories.json")
        val inputStreamStoreProduct = assetManager.open("store_products.json")
        val jsonStrStoreCat = inputStreamStoreCat.bufferedReader().readText()

        val storeCategoryListType = object : TypeToken<List<StoreCategory>>() {}.type
        val storeCategories: List<StoreCategory> = Gson().fromJson(jsonStrStoreCat, storeCategoryListType)

        val storeSubCatListType = object : TypeToken<List<StoreSubCategory>>() {}.type
        val storeSubCategories: List<StoreSubCategory> = Gson().fromJson(inputStreamStoreSubCat.bufferedReader().readText(), storeSubCatListType)

        val storeProductListType = object : TypeToken<List<StoreProduct>>() {}.type
        val storeProducts: List<StoreProduct> = Gson().fromJson(inputStreamStoreProduct.bufferedReader().readText(), storeProductListType)

        Log.e("TAG", "onCreate: "+ storeProducts)
        mainViewModel.insertDataBase(storeCategories, storeSubCategories, storeProducts)

        binding.recyclerCategory.adapter
    }
}