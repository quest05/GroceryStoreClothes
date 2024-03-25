package com.example.grocerystoreclothes.roomdb.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.grocerystoreclothes.model.Id
import com.example.grocerystoreclothes.model.Products
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

    @Query("SELECT * FROM store_sub_category_table")
    fun getAllStoreSubCategoriesLive(): LiveData<List<StoreSubCategory>>

    @Query("SELECT * FROM store_sub_category_table WHERE id = :id")
    suspend fun getObjectById(id: Id): StoreSubCategory?

    @Query("UPDATE store_sub_category_table SET products = :products WHERE id = :storeSubCatId")
    suspend fun updateProducts(products: List<Products>, storeSubCatId: Id)

}