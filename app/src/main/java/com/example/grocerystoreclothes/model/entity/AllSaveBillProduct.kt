package com.example.grocerystoreclothes.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "save_bill_table")
data class AllSaveBillProduct(
    @PrimaryKey var billNumber: String,
    var billDateTime: Long,
    var billTotalAmount: String,
    var billItemList: List<StoreProduct>
)