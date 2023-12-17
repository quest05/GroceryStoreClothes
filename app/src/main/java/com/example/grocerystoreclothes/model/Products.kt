package com.example.grocerystoreclothes.model

import com.google.gson.annotations.SerializedName

data class Products(
    @SerializedName("oid")
    var oid: String? = null
)