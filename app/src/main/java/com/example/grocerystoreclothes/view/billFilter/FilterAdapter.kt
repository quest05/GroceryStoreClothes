package com.example.grocerystoreclothes.view.billFilter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerystoreclothes.databinding.ItemFilterBillBinding
import com.example.grocerystoreclothes.model.entity.AllSaveBillProduct
import java.text.SimpleDateFormat
import java.util.Date

class FilterAdapter(
    storeProduct: List<AllSaveBillProduct>,
) :
    RecyclerView.Adapter<FilterAdapter.ViewHolder>() {
    private var productList: List<AllSaveBillProduct> = storeProduct

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemFilterBillBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(productList[position])

    override fun getItemCount(): Int = productList.size

    inner class ViewHolder(private val binding: ItemFilterBillBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(data: AllSaveBillProduct) {
            binding.apply {
                data.also { data ->
                    txtBillNumber.text =  "Bill Number: "+data.billNumber
                    txtTotalAmount.text = "Total BIll Amount: "+data.billTotalAmount
                    txtBillDate.text = "Bill Date: " + formatDate(data.billDateTime)
                    val billStrBuilder = StringBuilder()

                    data.billItemList.forEachIndexed { index, product ->
                        product.name?.let { productName ->
                            billStrBuilder.append( index.plus(1).toString() + ". "+ productName)
                            billStrBuilder.append(" (Price: "+product.price+" * "+ product.cartCount + " = "+ (product.price?.mod(product.cartCount!!) ?: "") + ")")

                            billStrBuilder.append("\n") // Add a new line after each product
                        }
                    }

                    txtBillData.text = billStrBuilder
                }
            }
        }
    }

    fun formatDate(timestamp: Long): String {
        val date = Date(timestamp)
        val dateFormat = SimpleDateFormat("dd-MM-yyyy (hh:mm a)")
        return dateFormat.format(date)
    }
}