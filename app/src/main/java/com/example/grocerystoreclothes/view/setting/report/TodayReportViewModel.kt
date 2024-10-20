package com.example.grocerystoreclothes.view.setting.report

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grocerystoreclothes.model.entity.AllSaveBillProduct
import com.example.grocerystoreclothes.roomdb.MyDefaultProductDb
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TodayReportViewModel @Inject constructor(
    private val db: MyDefaultProductDb
) : ViewModel() {

    val storeAllBillList = MutableLiveData<List<AllSaveBillProduct>>()

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                storeAllBillList.postValue(db.storeAllBillDao().getAllSaveBill())
            }
        }
//        getFilterDateList()
    }


     fun getFilterDateList() {


         Log.e( "TAG", "getFilterDateList: size of all bill "+ (storeAllBillList.value?.size ?: -1))


         /*val filteredList = storeAllBillList.value?.filter { item ->
             val billDateTime = item.billDateTime
             billDateTime in startDate..endDate
         }*/

//        storeAllBillList.value = todayBills!!

    }




}