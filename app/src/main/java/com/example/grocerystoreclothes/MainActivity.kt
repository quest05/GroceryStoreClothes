package com.example.grocerystoreclothes

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grocerystoreclothes.databinding.ActivityMainBinding
import com.example.grocerystoreclothes.model.entity.StoreCategory
import com.example.grocerystoreclothes.model.entity.StoreProduct
import com.example.grocerystoreclothes.model.entity.StoreSubCategory
import com.example.grocerystoreclothes.view.MainViewModel
import com.example.grocerystoreclothes.view.adapter.CategoryAdapter
import com.example.grocerystoreclothes.view.adapter.ProductAdapter
import com.example.grocerystoreclothes.view.adapter.SubCategoryAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val assetManager = resources.assets
        val inputStreamStoreCat = assetManager.open("store_categories.json")
        val inputStreamStoreSubCat = assetManager.open("store_sub_categories.json")
        val inputStreamStoreProduct = assetManager.open("store_products.json")
        val jsonStrStoreCat = inputStreamStoreCat.bufferedReader().readText()

        val storeCategoryListType = object : TypeToken<List<StoreCategory>>() {}.type
        val storeCategories: List<StoreCategory> =
            Gson().fromJson(jsonStrStoreCat, storeCategoryListType)

        val storeSubCatListType = object : TypeToken<List<StoreSubCategory>>() {}.type
        val storeSubCategories: List<StoreSubCategory> =
            Gson().fromJson(inputStreamStoreSubCat.bufferedReader().readText(), storeSubCatListType)

        val storeProductListType = object : TypeToken<List<StoreProduct>>() {}.type
        val storeProducts: List<StoreProduct> = Gson().fromJson(
            inputStreamStoreProduct.bufferedReader().readText(),
            storeProductListType
        )

        Log.e("TAG", "onCreate: " + storeProducts)
        mainViewModel.insertDataBase(storeCategories, storeSubCategories, storeProducts)

        binding.recyclerCategory.layoutManager = LinearLayoutManager(this)
        binding.recyclerSubCategory.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerProduct.layoutManager = GridLayoutManager(this, 2)
        mainViewModel.getAllCategory()
        mainViewModel.getAllSubCategory()
        mainViewModel.getAllProducts()
        mainViewModel.storeCategoryList.observe(this) {
            binding.recyclerCategory.adapter = CategoryAdapter(it)
        }
        mainViewModel.storeSubCategoryList.observe(this) {
            binding.recyclerSubCategory.adapter = SubCategoryAdapter(it)
        }
        mainViewModel.productsList.observe(this) {
            binding.recyclerProduct.adapter = ProductAdapter(it)
        }

    }
}