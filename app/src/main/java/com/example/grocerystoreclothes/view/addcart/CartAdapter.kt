package com.example.grocerystoreclothes.view.addcart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerystoreclothes.Constants.formatter
import com.example.grocerystoreclothes.databinding.ItemCartLayoutBinding
import com.example.grocerystoreclothes.model.entity.StoreProduct

class CartAdapter(
    storeProduct: List<StoreProduct>,
    private val isReturnProduct: Boolean,
    private val clickAddProduct: OnProductClickListener
) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    private var productList: List<StoreProduct> = storeProduct

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemCartLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(productList[position])

    override fun getItemCount(): Int = productList.size

    inner class ViewHolder(private val binding: ItemCartLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: StoreProduct) {
            binding.apply {
                data.also { data ->
                    txtProductName.text = data.name
                    val formatterPrice = data.price?.let { data.cartCount?.times(it) }
                    txtPrice.text = formatter.format(formatterPrice)
                    txtProductId.text = data.productId
                    if (isReturnProduct) {
                        txtProductSize.text = data.cartCount.toString()
                    } else {
                        txtProductSize.text = data.cartCount.toString()
                    }
                }
                btnPlusAdd.visibility = View.VISIBLE
                btnMinusRemove.visibility = View.VISIBLE
                btnPlusAdd.setOnClickListener {
                    clickAddProduct.onAddProduct(adapterPosition, data)
                }
                btnMinusRemove.setOnClickListener {
                    clickAddProduct.onRemoveProduct(adapterPosition, data)
                }
                txtProductSize.setOnClickListener {
                    clickAddProduct.onAddMoreProduct(adapterPosition, data)
                }
                if (isReturnProduct) {
                    btnPlusAdd.visibility = View.GONE
                    btnMinusRemove.visibility = View.GONE
                }
            }
        }
    }

    interface OnProductClickListener {
        fun onAddProduct(position: Int, products: StoreProduct)
        fun onRemoveProduct(position: Int, products: StoreProduct)
        fun onAddMoreProduct(position: Int, products: StoreProduct)
    }
}