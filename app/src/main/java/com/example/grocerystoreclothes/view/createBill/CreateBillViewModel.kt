package com.example.grocerystoreclothes.view.createBill

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grocerystoreclothes.model.entity.AllSaveBillProduct
import com.example.grocerystoreclothes.preferences.MyPreference
import com.example.grocerystoreclothes.roomdb.MyDefaultProductDb
import com.example.grocerystoreclothes.view.home.MainViewModel.Companion.addCartProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CreateBillViewModel @Inject constructor(
    private val db: MyDefaultProductDb,
    private val myPreference: MyPreference
) : ViewModel() {

    fun saveBillToDatabase(totalPrice: String, isReturnProduct: Boolean) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val currentBillNumber =
                    if (isReturnProduct) myPreference.getReturnBillNo() else myPreference.getBillNo()
                val nextBillNumber = currentBillNumber + 1
                if (isReturnProduct) {
                    myPreference.setReturnBillNo(nextBillNumber)

                } else {
                    myPreference.setBillNo(nextBillNumber)
                }

                val billNumber = if (isReturnProduct) String.format(
                    "RE-%04d",
                    nextBillNumber
                ) else String.format("%04d", nextBillNumber)

                val saveBill = AllSaveBillProduct(
                    billNumber = billNumber,
                    billTotalAmount = totalPrice,
                    billDateTime = System.currentTimeMillis(),
                    billItemList = addCartProduct.value!!
                )


                db.storeAllBillDao().insertOneSaveBill(saveBill)

                Log.e(
                    "TAG",
                    "saveBillToDatabase: get all bill " + db.storeAllBillDao().getAllSaveBill()
                )
            }
        }
    }

}