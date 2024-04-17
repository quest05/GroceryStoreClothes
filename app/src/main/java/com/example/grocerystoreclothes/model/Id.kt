package com.example.grocerystoreclothes.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Id(
    @SerializedName("oid")
    var oid: String? = null
): Serializable