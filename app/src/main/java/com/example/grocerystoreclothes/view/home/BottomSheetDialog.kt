package com.example.grocerystoreclothes.view.home

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerystoreclothes.Constants.formatter
import com.example.grocerystoreclothes.R
import com.example.grocerystoreclothes.model.entity.StoreProduct
import com.example.grocerystoreclothes.view.addcart.AddCartActivity
import com.example.grocerystoreclothes.view.addcart.CartAdapter
import com.example.grocerystoreclothes.view.home.MainViewModel.Companion.addCartProduct
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.Serializable
import javax.annotation.Nullable

class BottomSheetDialog(private val isReturnProduct: Boolean) : BottomSheetDialogFragment() {
    var totalPrice = 0.0
    private var totalQty = 0
    private lateinit var txtTotalAmount: TextView
    private lateinit var txtTotalQty: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {
        val v: View = inflater.inflate(
            R.layout.bottom_sheet_layout,
            container, false
        )
        val rvBottomProduct = v.findViewById<RecyclerView>(R.id.rvBottomProduct)
        val txtReturnRedLine = v.findViewById<TextView>(R.id.txtReturnRedLine)
        txtTotalAmount = v.findViewById(R.id.txtTotalAmountBottom)
        txtTotalQty = v.findViewById(R.id.txtTotalQty)
        val btnViewCart = v.findViewById<Button>(R.id.btnViewCart)


        if (isReturnProduct) {
            /*val formatterPrice = formatter.format(totalPrice)
            txtTotalAmount.text = "₹$formatterPrice"
            txtTotalQty.text = totalQty.toString()*/
            txtReturnRedLine.visibility = View.VISIBLE
        } else {
            /*val formatterPrice = formatter.format(totalPrice)
            txtTotalAmount.text = "₹$formatterPrice"
            txtTotalQty.text = totalQty.toString()*/
        }

        addCartProduct.observe(this) {

            totalPrice = 0.0
            totalQty = 0
            it.forEach { product ->
                totalPrice += product.price?.times(product.cartCount!!) ?: 0
                totalQty += product.cartCount?: 0
            }
            val formatterPrice = formatter.format(totalPrice)
            txtTotalAmount.text = "₹$formatterPrice"
            txtTotalQty.text = totalQty.toString()


            Log.e("TAG", "onCreateView: rvBottomProduct.adapter " +rvBottomProduct.adapter )
            rvBottomProduct.adapter =
                CartAdapter(it, isReturnProduct, object : CartAdapter.OnProductClickListener {
                    override fun onAddProduct(position: Int, products: StoreProduct) {
                        addNewProduct(products)
                    }

                    override fun onRemoveProduct(position: Int, products: StoreProduct) {
                        removeProduct(products)
                        if (totalPrice.equals(0.0)) {
                            dismiss()
                        }
                    }

                    override fun onAddMoreProduct(position: Int, products: StoreProduct) {
                        showDialog(products)
                    }
                })
        }

        btnViewCart.setOnClickListener {
            val addCartProductValue = addCartProduct.value
            it.context.startActivity(Intent(it.context, AddCartActivity::class.java).apply {
                putExtra("addedProduct", addCartProductValue as Serializable)
                putExtra("isReturn", isReturnProduct)
            })

            dismiss()
        }
        return v
    }

    private fun removeProduct(product: StoreProduct) {
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

    private fun addNewProduct(product: StoreProduct, productCount: Int =0) {

       /* val currentList = addCartProduct.value ?: emptyList()
        val existingProduct = currentList.find { it.Id.oid == product.Id.oid }

        if (existingProduct != null) {
            val updatedList = currentList.map {
                if (productCount >= 1) {
                    it.copy(cartCount = productCount)
                } else {
                    it.copy(cartCount = it.cartCount?.plus(1))
                }
            }
            addCartProduct.value = updatedList
        } else {
            val newList = currentList.toMutableList()
            newList.add(product.copy(cartCount = productCount))
            addCartProduct.value = newList
        }*/

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

    private fun returnNewProduct(product: StoreProduct, productCount: Int) {

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


    private fun showDialog(products: StoreProduct) {
        val dialog = Dialog(requireContext())
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
                    returnNewProduct(products, moreCount)
                } else {
                    addNewProduct(products, moreCount)
                }
            }
            dialog.dismiss()
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}
