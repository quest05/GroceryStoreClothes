package com.example.grocerystoreclothes.roomdb.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.grocerystoreclothes.model.entity.StoreCategory

@Dao
interface StoreCategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStoreCategory(storeCategories: List<StoreCategory>)

    @Update
    fun updateStoreCategory(storeCategory: StoreCategory)

    @Delete
    fun deleteStoreCategory(storeCategory: StoreCategory)

    @Query("SELECT * FROM store_category_table")
    fun getAllStoreCategories(): List<StoreCategory>
}