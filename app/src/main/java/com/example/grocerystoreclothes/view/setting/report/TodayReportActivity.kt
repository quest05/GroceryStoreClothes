package com.example.grocerystoreclothes.view.setting.report

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grocerystoreclothes.databinding.ActivityTodayReportBinding
import com.example.grocerystoreclothes.model.entity.StoreProduct
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar


@AndroidEntryPoint
class TodayReportActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTodayReportBinding
    private val viewModel: TodayReportViewModel by viewModels()
    private var prtList : ArrayList<StoreProduct> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()

        binding = ActivityTodayReportBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel.getFilterDateList()
        viewModel.storeAllBillList.observe(this) { list ->

            val todayBills = list?.filter {
                isToday(it.billDateTime)
            }

            Log.e("TAG", "onCreate:todayBills "+ (todayBills?.size ?: -3))

            var totalReportPrice = 0
            if (todayBills != null) {
                for(data in todayBills) {
                    Log.e("TAG", "onCreate: data $data"  )
                    for(inside in data.billItemList) {
                        prtList.add(inside)
//                        totalReportPrice += inside.price!!
                        if((inside.cartCount ?: 0) < 0){
                            totalReportPrice -= inside.price!!.times(inside.cartCount!!)
                        }else{
                            totalReportPrice += inside.price!!.times(inside.cartCount!!)

                        }
                    }
                }
            }

            binding.txtRvTodayReport.layoutManager = LinearLayoutManager(this)
            binding.txtRvTodayReport.adapter = TodayReportAdapter(prtList)
            binding.txtTotalReportPrice.text = "Total Amount: ₹ $totalReportPrice"
        }
    }


    private fun isToday(billDateTime: Long): Boolean {
        // Create a Calendar instance for today's start and end times
        val calendar = Calendar.getInstance()

        // Set calendar to start of the day (midnight)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startOfDay = calendar.timeInMillis

        // Set calendar to end of the day (11:59:59 PM)
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        val endOfDay = calendar.timeInMillis

        // Check if the bill date falls within today's range
        return billDateTime in startOfDay..endOfDay
    }
}