package com.example.grocerystoreclothes.view.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grocerystoreclothes.databinding.ActivityMainBinding
import com.example.grocerystoreclothes.model.entity.StoreProduct
import com.example.grocerystoreclothes.view.adapter.CategoryAdapter
import com.example.grocerystoreclothes.view.adapter.ProductAdapter
import com.example.grocerystoreclothes.view.adapter.SubCategoryAdapter
import com.example.grocerystoreclothes.view.addcart.AddCartActivity
import com.example.grocerystoreclothes.view.addpProduct.AddProductActivity
import com.example.grocerystoreclothes.view.billFilter.FilterBillActivity
import com.example.grocerystoreclothes.view.home.MainViewModel.Companion.addCartProduct
import com.example.grocerystoreclothes.view.setting.SettingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.Serializable

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

        binding.recyclerCategory.layoutManager = LinearLayoutManager(this)
        binding.recyclerSubCategory.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerProduct.layoutManager = GridLayoutManager(this, 2)
        mainViewModel.getDbAllCategory()

        mainViewModel.storeCategoryList.observe(this) {
            binding.recyclerCategory.adapter = CategoryAdapter(it, mainViewModel)
            lifecycleScope.launch {
                delay(2000)
                mainViewModel.getSelectedStoreSubCategories(it[0].subCategory)
            }
        }

        mainViewModel.selectedSubCatList.observe(this) {
            binding.progressbar.visibility = View.GONE
            mainViewModel.getSelectedProductSubCat(it[0].products)
            binding.recyclerSubCategory.adapter = SubCategoryAdapter(it, mainViewModel)
        }

        mainViewModel.selectedProductList.observe(this) {
            binding.recyclerProduct.adapter =
                ProductAdapter(it, object : ProductAdapter.OnProductClickListener {
                    override fun onClickAddProduct(position: Int, products: StoreProduct) {
                        mainViewModel.addToCartProduct(products)
                        // Handle the click action here
                    }
                })
        }


        binding.btnAddProduct.setOnClickListener {
            it.context.startActivity(Intent(it.context, AddProductActivity::class.java).apply {
                // putExtra("keyIdentifier", value)
            })
           /* if(mainViewModel.addCartProduct.value?.isEmpty() == true){

            } else {
                showConfirmationDialog(
                    "There is Product in cart... \nAre you sure you want to empty cart?",
                    onConfirm = {
                        it.context.startActivity(Intent(it.context, AddProductActivity::class.java).apply {
                            // putExtra("keyIdentifier", value)
                        })
                    }
                )
            }*/
        }

        binding.btnSetting.setOnClickListener {
            it.context.startActivity(Intent(it.context, SettingActivity::class.java).apply {
            })
            /*if(mainViewModel.addCartProduct.value?.isEmpty() == true){

            }else{
                showConfirmationDialog(
                    "There is Product in cart... \nAre you sure you want to empty cart?",
                    onConfirm = {
                        it.context.startActivity(Intent(it.context, SettingActivity::class.java).apply {
                        })
                    }
                )
            }*/
        }


        binding.btnFilterBill.setOnClickListener {
            it.context.startActivity(Intent(it.context, FilterBillActivity::class.java).apply {
            })
        }

        binding.btnCart.setOnClickListener {
            val addCartProductValue = addCartProduct.value
            it.context.startActivity(Intent(it.context, AddCartActivity::class.java).apply {
                putExtra("addedProduct", addCartProductValue as Serializable)
            })
        }
    }
}