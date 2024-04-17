package com.example.grocerystoreclothes.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CreatedAt(
    @SerializedName("date")
    var date: String? = null
): Serializable