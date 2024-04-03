package com.example.grocerystoreclothes.view.signup

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.grocerystoreclothes.view.home.MainActivity
import com.example.grocerystoreclothes.R
import com.example.grocerystoreclothes.databinding.ActivitySignInBinding
import com.example.grocerystoreclothes.model.entity.StoreCategory
import com.example.grocerystoreclothes.model.entity.StoreProduct
import com.example.grocerystoreclothes.model.entity.StoreSubCategory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern

@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private val viewModel: SignInViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.signup)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

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

        viewModel.insertDataBase(storeCategories, storeSubCategories, storeProducts)

        binding.buttonSignIn.setOnClickListener {
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()

            if (isValidEmail(email) && isValidPassword(password)) {
                // Perform sign-in logic here
                if (binding.radioAdmin.isChecked) {
                    if (email == "admin@gmail.com" && password == "admin@123") {
                        viewModel.setAdminPreference(true)
                        it.context.startActivity(
                            Intent(
                                it.context,
                                MainActivity::class.java
                            ).apply {})
                        finish()
                    } else {
                        Toast.makeText(this, "Email or Password invalid", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val isMatched = viewModel.checkCredentials(
                        this,
                        binding.editTextEmail.text.toString(),
                        binding.editTextPassword.text.toString()
                    )
                    if (isMatched) {
                        it.context.startActivity(Intent(
                            it.context, MainActivity::class.java
                        ).apply {})
                        finish()
                    } else {
                        Toast.makeText(this, "Email or Password invalid", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                if (!isValidEmail(email))
                    binding.editTextEmail.error = "Enter valid email"
                else
                    binding.editTextPassword.error = "Enter valid password"
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        // Password should be at least 6 characters long
        val pattern = Pattern.compile("^.{6,}$")
        return pattern.matcher(password).matches()
    }
}