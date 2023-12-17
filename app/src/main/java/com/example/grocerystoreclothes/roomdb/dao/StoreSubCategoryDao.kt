package com.example.grocerystoreclothes.roomdb.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.grocerystoreclothes.model.entity.StoreSubCategory

@Dao
interface StoreSubCategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStoreSubCategory(storeSubCategories: List<StoreSubCategory>)

    @Update
    fun updateStoreSubCategory(storeSubCategory: StoreSubCategory)

    @Delete
    fun deleteStoreSubCategory(storeSubCategory: StoreSubCategory)

    @Query("SELECT * FROM store_sub_category_table")
    fun getAllStoreSubCategories(): List<StoreSubCategory>
}