package com.example.grocerystoreclothes.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.grocerystoreclothes.model.CreatedAt
import com.example.grocerystoreclothes.model.Id
import com.example.grocerystoreclothes.model.SubCategory
import com.example.grocerystoreclothes.model.UpdatedAt
import com.google.gson.annotations.SerializedName

@Entity(tableName = "store_category_table")
data class StoreCategory(
    @SerializedName("_id")
    @PrimaryKey var Id: Id,

    @SerializedName("name")
    var name: String,

    @SerializedName("subCategory")
    var subCategory: List<SubCategory> = arrayListOf(),

    @SerializedName("createdAt")
    var createdAt: CreatedAt,

    @SerializedName("updatedAt")
    var updatedAt: UpdatedAt,

    @SerializedName("__v")
    var _v: Int
)
