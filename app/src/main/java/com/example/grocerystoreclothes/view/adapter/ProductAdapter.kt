package com.example.grocerystoreclothes.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerystoreclothes.Constants.formatter
import com.example.grocerystoreclothes.databinding.ItemProductLayoutBinding
import com.example.grocerystoreclothes.model.entity.StoreProduct

class ProductAdapter(
    storeProduct: List<StoreProduct>,
    private val isReturnProduct: Boolean,
    private val clickAddProduct: OnProductClickListener
) :
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
                data.also { data ->
                    txtProductName.text = data.name
                    val formattedNumber = formatter.format(data.price)
                    txtPrice.text = formattedNumber

                    txtProductid.text = data.productId
                    if (isReturnProduct) {
                        btnAddItem.visibility = View.GONE
                        btnReturn.visibility = View.VISIBLE
                    } else {
                        btnAddItem.visibility = View.VISIBLE
                        btnReturn.visibility = View.GONE
                    }
                    btnAddItem.setOnClickListener {
                        clickAddProduct.onClickAddProduct(adapterPosition, data)
//                        Toast.makeText(binding.root.context, "${data.name} added ", Toast.LENGTH_SHORT).show()
                    }
                    btnReturn.setOnClickListener {
                        clickAddProduct.onClickReturnProduct(adapterPosition, data)
//                        Toast.makeText(binding.root.context, "${data.name} added ", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    interface OnProductClickListener {
        fun onClickAddProduct(position: Int, products: StoreProduct)
        fun onClickReturnProduct(position: Int, products: StoreProduct)
    }
}