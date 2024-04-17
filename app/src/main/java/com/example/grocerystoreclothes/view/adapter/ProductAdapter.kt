package com.example.grocerystoreclothes.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerystoreclothes.databinding.ItemProductLayoutBinding
import com.example.grocerystoreclothes.model.entity.StoreProduct

class ProductAdapter(storeProduct: List<StoreProduct>, private val clickAddProduct: OnProductClickListener) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    private var productList: List<StoreProduct> = storeProduct

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemProductLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(productList[position])

    override fun getItemCount(): Int = productList.size

    inner class ViewHolder(private val binding: ItemProductLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: StoreProduct) {
            binding.apply {
                data.also {data ->
                    txtProductName.text = data.name
                    txtPrice.text = data.price.toString()
                    txtProductid.text = data.productId
                    btnAddItem.setOnClickListener {
                        clickAddProduct.onClickAddProduct(adapterPosition, data)
                        Toast.makeText(binding.root.context, "${data.name} added ", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    interface OnProductClickListener {
        fun onClickAddProduct(position: Int, products: StoreProduct)
    }
}