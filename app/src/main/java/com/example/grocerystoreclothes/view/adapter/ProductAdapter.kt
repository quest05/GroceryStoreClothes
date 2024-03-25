package com.example.grocerystoreclothes.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerystoreclothes.databinding.ItemProductLayoutBinding
import com.example.grocerystoreclothes.model.entity.StoreProduct

class ProductAdapter(storeProduct: List<StoreProduct>) :
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
                data.also {
                    txtProductName.text = it.name
                    txtPrice.text = it.price.toString()
                    txtProductid.text = it.productId
                    btnAddItem.setOnClickListener {
                        // perform adding item to create bill

                    }
                }
            }
        }
    }
}