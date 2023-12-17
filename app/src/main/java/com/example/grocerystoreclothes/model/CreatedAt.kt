package com.example.grocerystoreclothes.model

import com.google.gson.annotations.SerializedName

data class CreatedAt(
    @SerializedName("date")
    var date: String? = null
)