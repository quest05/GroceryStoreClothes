package com.example.grocerystoreclothes.view.addcart

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.grocerystoreclothes.Constants.formatter
import com.example.grocerystoreclothes.R
import com.example.grocerystoreclothes.databinding.ActivityAddCartBinding
import com.example.grocerystoreclothes.model.entity.StoreProduct
import com.example.grocerystoreclothes.view.createBill.CreateBillActivity
import com.example.grocerystoreclothes.view.home.MainViewModel.Companion.addCartProduct
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class AddCartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCartBinding
    private val viewModel: AddCartViewModel by viewModels()
    private var totalPrice = 0.0
    private var totalQty = 0
    private var isReturnProduct = false

    @SuppressLint("SetTextI18n")
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
        isReturnProduct = intent.getBooleanExtra("isReturn", false)

        if (isReturnProduct) {
            binding.txtTitle.text = "Return Cart Item"
            binding.btnCreateBill.text = "Create Return Bill"
        }

        viewModel.getSrNumber(isReturnProduct)
        viewModel.billSrNumber.observe(this) {
            binding.txtSrNo.text = "Sr.No: $it"
        }

//        val addedProduct = intent.getSerializableExtra("addedProduct") as? List<StoreProduct>
        if (addCartProduct.value != null) {
//            viewModel.addedAllProductCart(addedProduct)
            /*if (Utilities.isTablet(this)) {
                binding.rvShowCart.layoutManager = GridLayoutManager(this, 6)
            } else {
                binding.rvShowCart.layoutManager = GridLayoutManager(this, 3)
            }*/
            val dateFormat = SimpleDateFormat("dd-MM-yyyy (hh:mm a)", Locale.getDefault())
            val formattedDate = dateFormat.format(Calendar.getInstance().time)

            binding.txtDate.text = "Date: $formattedDate"

            addCartProduct.observe(this) {
                totalPrice = 0.0
                totalQty = 0
                it.forEach { product ->
                    totalPrice += product.price?.times(product.cartCount!!) ?: 0
                    totalQty += product.cartCount?: 0
                }
                val formatterPrice = formatter.format(totalPrice)
                binding.txtTotalAmount.text = "₹$formatterPrice"
                binding.txtTotalQty.text = totalQty.toString()


                binding.rvShowCart.adapter =
                    CartAdapter(it, isReturnProduct, object : CartAdapter.OnProductClickListener {
                        override fun onAddProduct(position: Int, products: StoreProduct) {
                            viewModel.addNewProduct(products)
                        }

                        override fun onRemoveProduct(position: Int, products: StoreProduct) {
                            viewModel.removeProduct(products)
                        }

                        override fun onAddMoreProduct(position: Int, products: StoreProduct) {
                            showDialog(products)
                        }
                    })
            }

            binding.txtEmptyCart.visibility = View.GONE
        } else {
            // empty cart
            binding.txtEmptyCart.visibility = View.VISIBLE
        }

        binding.btnCreateBill.setOnClickListener {
            if (addCartProduct.value?.isEmpty() == true) {
                Toast.makeText(
                    this, "Need to add one item atleast to create a bill...", Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            showConfirmationDialog(
                onConfirm = {
                    it.context.startActivity(
                        Intent(it.context, CreateBillActivity::class.java).apply {
                            putExtra("isReturn", isReturnProduct)
                        })

                    finish()
                }
            )
        }
    }

    private fun showDialog(products: StoreProduct) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_new_add_product)

        val edtAddMoreProduct: EditText = dialog.findViewById(R.id.edtAddMoreProduct)
        val btnAddMore: Button = dialog.findViewById(R.id.btnAddMore)
        val btnCancel: Button = dialog.findViewById(R.id.btnCancel)

        btnAddMore.setOnClickListener {
            if (edtAddMoreProduct.text.isNotEmpty()) {
                val moreCount = edtAddMoreProduct.text.toString().toInt()
                if (isReturnProduct) {
                    viewModel.returnNewProduct(products, moreCount)
                } else {
                    viewModel.addNewProduct(products, moreCount)
                }

            }
            dialog.dismiss()
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun showConfirmationDialog(onConfirm: () -> Unit) {
        val builder = AlertDialog.Builder(this, R.style.AlertDialogTheme)
        builder.setTitle("Confirmation!")
            .setMessage("Are you sure you want to proceed?")
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