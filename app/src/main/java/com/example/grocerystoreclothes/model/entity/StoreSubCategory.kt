package com.example.grocerystoreclothes.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.grocerystoreclothes.model.CreatedAt
import com.example.grocerystoreclothes.model.Id
import com.example.grocerystoreclothes.model.Products
import com.example.grocerystoreclothes.model.UpdatedAt
import com.google.gson.annotations.SerializedName

@Entity(tableName = "store_sub_category_table")
data class StoreSubCategory(

    @SerializedName("_id")
    @PrimaryKey var Id: Id,

    @SerializedName("name")
    var name: String,

    @SerializedName("products")
    var products: List<Products> = arrayListOf(),

    @SerializedName("createdAt")
    var createdAt: CreatedAt,

    @SerializedName("updatedAt")
    var updatedAt: UpdatedAt,

    @SerializedName("__v")
    var _v: Int


)