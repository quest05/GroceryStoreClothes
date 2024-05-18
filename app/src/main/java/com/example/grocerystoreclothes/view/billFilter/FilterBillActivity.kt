package com.example.grocerystoreclothes.view.billFilter

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grocerystoreclothes.R
import com.example.grocerystoreclothes.databinding.ActivityFilterBillBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class FilterBillActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilterBillBinding
    private val viewModel: FilterBillViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityFilterBillBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.recyclerAllBill.layoutManager = LinearLayoutManager(this)

        viewModel.storeAllBillList.observe(this) { list ->
            binding.recyclerAllBill.adapter = FilterAdapter(list)
        }

        binding.txtDateRangePicker.setOnClickListener {
            showRangeDatePicker()
        }

    }

    private fun showRangeDatePicker() {
        val builder = MaterialDatePicker.Builder.dateRangePicker()
        val constraintsBuilder = CalendarConstraints.Builder()
        builder.setCalendarConstraints(constraintsBuilder.build())

        val picker = builder.build()

        picker.addOnPositiveButtonClickListener { selection ->
            // Handle the date range selection
            val startDate = selection.first
            val endDate = selection.second
            val sdf = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
            val formattedStartDate = sdf.format(startDate)
            val formattedEndDate = sdf.format(endDate)


            // Do something with the selected dates
            val datePair = Pair(startDate, endDate)
            Log.e("TAG", "showRangeDatePicker: $startDate end $endDate")
            binding.txtDateRange.text = "$formattedStartDate - $formattedEndDate"
            viewModel.getFilterDateList(startDate, endDate)
            picker.dismiss()
        }

        picker.show(this.supportFragmentManager, picker.toString())
    }

}