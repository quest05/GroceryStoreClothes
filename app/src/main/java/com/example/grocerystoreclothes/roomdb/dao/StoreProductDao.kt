package com.example.grocerystoreclothes.roomdb.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.grocerystoreclothes.model.entity.StoreProduct

@Dao
interface StoreProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStoreProduct(storeProducts: List<StoreProduct>)

    @Update
    fun updateStoreProduct(storeProduct: StoreProduct)

    @Delete
    fun deleteStoreProduct(storeProduct: StoreProduct)

    @Query("SELECT * FROM store_product_table")
    fun getAllStoreProducts(): List<StoreProduct>
}