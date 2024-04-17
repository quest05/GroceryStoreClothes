package com.example.grocerystoreclothes.view.addcart

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.grocerystoreclothes.R
import com.example.grocerystoreclothes.databinding.ActivityAddCartBinding
import com.example.grocerystoreclothes.model.entity.StoreProduct
import com.example.grocerystoreclothes.view.createBill.CreateBillActivity
import com.example.grocerystoreclothes.view.home.MainViewModel.Companion.addCartProduct
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCartBinding
    private val viewModel: AddCartViewModel by viewModels()
    private var totalPrice = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAddCartBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        val addedProduct = intent.getSerializableExtra("addedProduct") as? List<StoreProduct>
        if (addCartProduct.value != null) {
//            viewModel.addedAllProductCart(addedProduct)
            binding.rvShowCart.layoutManager = GridLayoutManager(this, 3)

            addCartProduct.value?.forEach { product ->
                totalPrice += product.price?.times(product.cartCount!!) ?: 0
            }

            binding.txtTotalAmount.text = "Total Amount: $totalPrice"
                addCartProduct.observe(this) {

                binding.rvShowCart.adapter =
                    CartAdapter(it, object : CartAdapter.OnProductClickListener {
                        override fun onAddProduct(position: Int, products: StoreProduct) {
                            viewModel.addNewProduct(products)
                            totalPrice += products.price ?: 0
                            binding.txtTotalAmount.text = "Total Amount: $totalPrice"
                        }

                        override fun onRemoveProduct(position: Int, products: StoreProduct) {
                            viewModel.removeProduct(products)
                            totalPrice -= products.price ?: 0
                            binding.txtTotalAmount.text = "Total Amount: $totalPrice"
                        }
                    })
            }

            binding.txtEmptyCart.visibility = View.GONE
        } else {
            // empty cart
            binding.txtEmptyCart.visibility = View.VISIBLE
        }

        binding.btnCreateBill.setOnClickListener {
            // TODO set dynamic bill number and time as well

            showConfirmationDialog(
                "Are you sure you want to proceed?",
                onConfirm = {
                    it.context.startActivity(Intent(it.context, CreateBillActivity::class.java).apply {
                    })
                }
            )
        }
    }

    private fun showConfirmationDialog(message: String, onConfirm: () -> Unit) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmation!")
            .setMessage(message)
            .setPositiveButton("Yes") { dialogInterface: DialogInterface, _: Int ->
                onConfirm.invoke()
                dialogInterface.dismiss()
            }
            .setNegativeButton("No") { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }
            .create()
            .show()
    }
}