package com.example.grocerystoreclothes.roomdb

import androidx.room.TypeConverter
import com.example.grocerystoreclothes.model.CreatedAt
import com.example.grocerystoreclothes.model.Id
import com.example.grocerystoreclothes.model.Products
import com.example.grocerystoreclothes.model.SubCategory
import com.example.grocerystoreclothes.model.UpdatedAt
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromString(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<String>): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromSubCategoryList(subCategoryList: List<SubCategory>): String {
        // Convert list to JSON string
        return Gson().toJson(subCategoryList)
    }

    @TypeConverter
    fun toSubCategoryList(subCategoryString: String): List<SubCategory> {
        // Convert JSON string to list
        return Gson().fromJson(subCategoryString, object : TypeToken<List<SubCategory>>() {}.type)
    }

    @TypeConverter
    fun fromProductList(value: List<Products>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toProductList(value: String): List<Products> {
        return Gson().fromJson(value, object : TypeToken<List<Products>>() {}.type)
    }


    @TypeConverter
    fun fromId(id: Id?): String {
        // Convert Id object to JSON string
        return Gson().toJson(id)
    }

    @TypeConverter
    fun toId(idString: String): Id {
        // Convert JSON string to Id object
        return Gson().fromJson(idString, Id::class.java)
    }

    @TypeConverter
    fun fromCreatedAt(value: CreatedAt?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toCreatedAt(value: String): CreatedAt {
        return Gson().fromJson(value, CreatedAt::class.java)
    }

    @TypeConverter
    fun fromUpdatedAt(value: UpdatedAt?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toUpdatedAt(value: String): UpdatedAt {
        return Gson().fromJson(value, UpdatedAt::class.java)
    }


}