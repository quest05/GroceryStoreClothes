package com.example.grocerystoreclothes.view.setting.report

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerystoreclothes.R
import com.example.grocerystoreclothes.databinding.ItemTodayReportLayoutBinding
import com.example.grocerystoreclothes.model.entity.StoreProduct

class TodayReportAdapter(
    storeProduct: List<StoreProduct>,
) :
    RecyclerView.Adapter<TodayReportAdapter.ViewHolder>() {
    private var productList: List<StoreProduct> = storeProduct

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemTodayReportLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(productList[position])

    override fun getItemCount(): Int = productList.size

    inner class ViewHolder(private val binding: ItemTodayReportLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(productData: StoreProduct) {
            binding.apply {
                txtCount.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
                txtProductId.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
                txtProductName.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
                txtProductQty.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
                txtPrice.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))

                itemView.background = ContextCompat.getDrawable(itemView.context, R.color.white)

                productData.also { product ->
                        product.name?.let { productName ->
                            txtProductName.text = productName
                        }
                        txtProductId.text = product.productId
                        txtProductQty.text = product.cartCount.toString()
                        txtPrice.text = (product.price?.times(product.cartCount!!).toString())
                }
            }
        }
    } 
}