package com.example.grocerystoreclothes.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerystoreclothes.databinding.ItemBottomCartLayoutBinding
import com.example.grocerystoreclothes.model.entity.StoreProduct

class BottomSheetAdapter(
    storeProduct: List<StoreProduct>,
    private val clickAddProduct: OnProductClickListener
) :
    RecyclerView.Adapter<BottomSheetAdapter.ViewHolder>() {
    private var productList: List<StoreProduct> = storeProduct

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemBottomCartLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(productList[position])

    override fun getItemCount(): Int = productList.size

    inner class ViewHolder(private val binding: ItemBottomCartLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: StoreProduct) {
            binding.apply {
                data.also { data ->
                    txtProductName.text = data.name
                    txtPrice.text = data.price.toString()
                    txtProductId.text = data.productId
                    txtProductSize.text = data.cartCount.toString()
                }
                binding.btnPlusAdd.setOnClickListener {
                    clickAddProduct.onAddProduct(adapterPosition, data)
                }
                btnMinusRemove.setOnClickListener {
                    clickAddProduct.onRemoveProduct(adapterPosition, data)
                }
                txtProductSize.setOnClickListener {
                    clickAddProduct.onAddMoreProduct(adapterPosition, data)
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
