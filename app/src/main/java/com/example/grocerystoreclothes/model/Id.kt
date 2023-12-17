package com.example.grocerystoreclothes.model

import com.google.gson.annotations.SerializedName

data class Id(
    @SerializedName("oid")
    var oid: String? = null
)