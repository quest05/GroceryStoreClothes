package com.example.grocerystoreclothes.model

import com.google.gson.annotations.SerializedName

data class UpdatedAt(
    @SerializedName("date")
    var date: String? = null
)