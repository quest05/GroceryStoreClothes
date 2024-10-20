package com.example.grocerystoreclothes.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.grocerystoreclothes.model.CreatedAt
import com.example.grocerystoreclothes.model.Id
import com.example.grocerystoreclothes.model.UpdatedAt
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "store_product_table")
data class StoreProduct(
    @SerializedName("_id")
    @PrimaryKey var Id: Id,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("productId")
    var productId: String? = null,

    @SerializedName("price")
    var price: Int? = null,

    @SerializedName("image")
    var image: String? = null,

    @SerializedName("rate")
    var rate: String? = null,

    @SerializedName("remark")
    var remark: String? = null,

    @SerializedName("createdAt")
    var createdAt: CreatedAt,

    @SerializedName("updatedAt")
    var updatedAt: UpdatedAt,

    @SerializedName("__v")
    var _v: Int? = null,

    @SerializedName("count")
    var cartCount: Int? = null,
    @ColumnInfo(name = "isReturn")
    val isReturn: Boolean
) : Serializable