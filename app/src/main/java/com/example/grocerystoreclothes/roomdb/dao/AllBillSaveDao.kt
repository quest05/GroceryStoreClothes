package com.example.grocerystoreclothes.roomdb.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.grocerystoreclothes.model.entity.AllSaveBillProduct

@Dao
interface AllBillSaveDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllSaveBill(storeCategories: List<AllSaveBillProduct>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOneSaveBill(saveBillProduct: AllSaveBillProduct)

    @Update
    fun updateAllSaveBill(allBillProduct: AllSaveBillProduct)

    @Delete
    fun deleteAllSaveBill(allBillProduct: AllSaveBillProduct)

    @Query("SELECT * FROM save_bill_table")
    fun getAllSaveBill(): List<AllSaveBillProduct>

    @Query("SELECT * FROM save_bill_table WHERE billNumber = :billNumber")
    suspend fun getBillByNumber(billNumber: String): AllSaveBillProduct?

}