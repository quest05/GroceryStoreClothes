package com.example.grocerystoreclothes.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerystoreclothes.databinding.ItemCategoryLayoutBinding
import com.example.grocerystoreclothes.model.entity.StoreCategory

class CategoryAdapter(storeCategories: List<StoreCategory>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    private var storeCategoryList: List<StoreCategory> = storeCategories

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemCategoryLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(storeCategoryList[position])

    override fun getItemCount(): Int = storeCategoryList.size

    inner class ViewHolder(private val binding: ItemCategoryLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(country: StoreCategory) {
            binding.apply {
                country.also {
                    txtCategory.text = it.name
                    /*Glide.with(binding.root.context)
                        .load("https://images.dog.ceo/breeds/mexicanhairless/n02113978_147.jpg")
                        .into(binding.imvCorner)*/
                }
            }
        }
    }
}