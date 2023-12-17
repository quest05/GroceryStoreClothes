package com.example.grocerystoreclothes.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.grocerystoreclothes.model.entity.StoreCategory
import com.example.grocerystoreclothes.model.entity.StoreProduct
import com.example.grocerystoreclothes.model.entity.StoreSubCategory
import com.example.grocerystoreclothes.roomdb.dao.StoreCategoryDao
import com.example.grocerystoreclothes.roomdb.dao.StoreProductDao
import com.example.grocerystoreclothes.roomdb.dao.StoreSubCategoryDao

@Database(entities = [StoreCategory::class, StoreSubCategory::class, StoreProduct::class],
    version = 11)
@TypeConverters(Converters::class)
abstract class MyDefaultProductDb : RoomDatabase() {
    abstract fun storeCategoryDao(): StoreCategoryDao
    abstract fun storeSubCategoryDao(): StoreSubCategoryDao
    abstract fun storeProductDao(): StoreProductDao
}