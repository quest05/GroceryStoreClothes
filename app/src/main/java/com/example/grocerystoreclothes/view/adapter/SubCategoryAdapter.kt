package com.example.grocerystoreclothes.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerystoreclothes.R
import com.example.grocerystoreclothes.databinding.ItemSubCategoryLayoutBinding
import com.example.grocerystoreclothes.model.entity.StoreSubCategory
import com.example.grocerystoreclothes.view.MainViewModel

class SubCategoryAdapter(dataList: List<StoreSubCategory>, mainViewModel: MainViewModel) :
    RecyclerView.Adapter<SubCategoryAdapter.ViewHolder>() {
    private var storeSubCategoryList: List<StoreSubCategory> = dataList
    private var viewModel = mainViewModel
    private var selectedPosition: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemSubCategoryLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(storeSubCategoryList[position])
    }

    override fun getItemCount(): Int = storeSubCategoryList.size

    inner class ViewHolder(private val binding: ItemSubCategoryLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(country: StoreSubCategory) {
            binding.apply {
                country.also {
                    txtSubCategory.text = it.name

                    txtSubCategory.setOnClickListener {
                        ContextCompat.getDrawable(itemView.context, R.drawable.rounded_border)
                    }

                    if (adapterPosition == selectedPosition) {
                        txtSubCategory.background = ContextCompat.getDrawable(
                            itemView.context, R.drawable.rounded_filled_border
                        )
                        txtSubCategory.setTextColor(
                            ContextCompat.getColor(itemView.context, R.color.white)
                        )
                    } else {
                        txtSubCategory.background =
                            ContextCompat.getDrawable(itemView.context, R.drawable.rounded_border)
                        txtSubCategory.setTextColor(
                            ContextCompat.getColor(itemView.context, R.color.black)
                        )
                    }

                    txtSubCategory.setOnClickListener {
                        txtSubCategory.background = ContextCompat.getDrawable(
                            itemView.context, R.drawable.rounded_filled_border
                        )
                        txtSubCategory.setTextColor(
                            ContextCompat.getColor(itemView.context, R.color.white)
                        )
                        notifyItemChanged(selectedPosition)
                        selectedPosition = adapterPosition

                        viewModel.getSelectedProductSubCat(storeSubCategoryList[selectedPosition].products)
                        Log.e("TAG", " bind : " + selectedPosition + "  " + storeSubCategoryList[selectedPosition])
                    }
                }
            }
        }
    }
}