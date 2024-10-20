package com.example.grocerystoreclothes.view.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerystoreclothes.R
import com.example.grocerystoreclothes.databinding.ItemCategoryLayoutBinding
import com.example.grocerystoreclothes.model.entity.StoreCategory
import com.example.grocerystoreclothes.view.home.MainViewModel

class CategoryAdapter(storeCategories: List<StoreCategory>, mainViewModel: MainViewModel) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    private var storeCategoryList: List<StoreCategory> = storeCategories
    private var selectedPosition: Int = 0
    private var viewmodel = mainViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemCategoryLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(storeCategoryList[position])
    }

    override fun getItemCount(): Int = storeCategoryList.size

    inner class ViewHolder(private val binding: ItemCategoryLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(country: StoreCategory) {
            binding.apply {
                country.also {
                    txtCategory.text = it.name

                    if (adapterPosition == selectedPosition) {
                        txtCategory.background = ContextCompat.getDrawable(
                            itemView.context, R.drawable.bottom_line_border
                        )
                        txtCategory.setTextColor(
                            ContextCompat.getColor(itemView.context, R.color.primary_text)
                        )
                        txtCategory.setTypeface(null, Typeface.BOLD)

                    } else {
                        txtCategory.background = null
                        txtCategory.setTextColor(
                            ContextCompat.getColor(itemView.context, R.color.secondary_text)
                        )
                        txtCategory.setTypeface(null, Typeface.NORMAL)
                    }

                    txtCategory.setOnClickListener {
                        txtCategory.background = ContextCompat.getDrawable(
                            itemView.context, R.drawable.bottom_line_border
                        )
                        txtCategory.setTextColor(
                            ContextCompat.getColor(itemView.context, R.color.primary_text)
                        )
                        txtCategory.setTypeface(null, Typeface.BOLD)
                        notifyItemChanged(selectedPosition)
                        selectedPosition = adapterPosition

                        viewmodel.getSelectedStoreSubCategories(storeCategoryList[adapterPosition].subCategory)
                    }
                }
            }
        }
    }
}

