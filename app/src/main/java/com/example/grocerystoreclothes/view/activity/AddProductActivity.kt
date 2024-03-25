package com.example.grocerystoreclothes.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.grocerystoreclothes.MainActivity
import com.example.grocerystoreclothes.R
import com.example.grocerystoreclothes.databinding.ActivityAddProductBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddProductBinding
    private val viewModel: AddProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel.storeCategoryList.observe(this) { it ->

            val namesList = it.map { it.name }
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, namesList
            )

            binding.spinnerCategory.adapter = adapter

            binding.spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                    viewModel.getSelectedStoreSubCategories(it[position].subCategory)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Handle case when nothing is selected
                }
            }
        }


        viewModel.storeSubCategoryList.observe(this) {

            viewModel.getSelectedStoreSubCategories(viewModel.storeCategoryList.value?.get(0)?.subCategory
                ?: emptyList()
            )

        }

        viewModel.selectedSubCatList.observe(this) { subList ->
            val namesList = subList.map { it.name }

            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, namesList)
            binding.spinnerSubCategory.adapter = adapter
        }

        binding.btnAddProduct.setOnClickListener {
            Log.e("TAG", "onCreate: "  + binding.etProduct.text?.trim().toString().length )
            if(binding.etProduct.text?.trim()?.length!! > 0 && binding.etPrice.text?.trim()?.length!! > 0){

                viewModel.addDBNewProduct(
                    binding.etPrice.text.toString(),
                    binding.spinnerSubCategory.selectedItemPosition,
                    binding.etProduct.text.toString()
                )
            }else{
                Toast.makeText(this, "Please enter product or price name ", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.successMsg.observe(this){
           startActivity(Intent(this, MainActivity::class.java).apply {
            })
            finish()
        }

    }
}