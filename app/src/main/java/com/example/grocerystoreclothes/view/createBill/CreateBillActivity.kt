package com.example.grocerystoreclothes.view.createBill

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.grocerystoreclothes.R
import com.example.grocerystoreclothes.databinding.ActivityCreateBillBinding
import com.example.grocerystoreclothes.model.entity.StoreProduct
import com.example.grocerystoreclothes.view.home.MainViewModel.Companion.addCartProduct
import dagger.hilt.android.AndroidEntryPoint
import java.io.OutputStream
import java.util.UUID

@AndroidEntryPoint
class CreateBillActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateBillBinding
    private val viewModel : CreateBillViewModel by viewModels()
    private var totalPrice = 0.0

    private var bluetoothAdapter: BluetoothAdapter? = null
    private var connectedDevice: BluetoothDevice? = null
    private var printOutputStream: OutputStream? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCreateBillBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        addCartProduct.value?.forEach { product ->
            totalPrice += product.price?.times(product.cartCount!!) ?: 0
        }

        viewModel.saveBillToDatabase(totalPrice.toString())

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        binding.btnPrint.setOnClickListener {

            val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                val pairedDevices = bluetoothAdapter?.bondedDevices ?: emptyList()
                val devices = pairedDevices.map { it.name to it }.toMap()
                // Now you can safely access paired devices and display them to the user

                connectButtonOnClick()
            } else {
                // Request Bluetooth permission if not granted yet
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    requestPermissions(arrayOf(Manifest.permission.BLUETOOTH_CONNECT), REQUEST_BLUETOOTH_PERMISSION)
                }
            }

        }
    }



    // Handle Bluetooth permission request result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_BLUETOOTH_PERMISSION) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission granted, proceed with Bluetooth operations
//            } else {
//                // Handle permission denied scenario (e.g., display a message)
//            }
        }
    }

    // Connect to the target Bluetooth device
    private fun connectToPrinter(device: BluetoothDevice) {
        connectedDevice = device
        try {
            val socket = device.createInsecureRfcommSocketToServiceRecord(UUID.fromString(BLUETOOTH_PRINTER_SPP_UUID)) // Replace with appropriate UUID if needed
            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            socket.connect()
            printOutputStream = socket.outputStream
            printButtonOnClick()
        } catch (e: Exception) {
            Log.e("PrinterApp", "Error connecting to printer:", e)
        }
    }

    // Format receipt content in ESC/POS commands (adapt based on your printer's requirements)
    private fun formatReceipt(items: List<StoreProduct>?): String {
        val stringBuilder = StringBuilder()
        stringBuilder.appendLine("**Store Name**")
        stringBuilder.appendLine("-----------------------")
        for (item in items!!) {
            stringBuilder.appendLine(item.name)
            stringBuilder.appendLine("  $${item.price}")
        }
        stringBuilder.appendLine("-----------------------")
        stringBuilder.appendLine("Total: $$  ")

        return stringBuilder.toString()
    }

    // Send the formatted receipt data to the printer
    private fun printReceipt(data: String) {
        try {
            printOutputStream?.write(data.toByteArray())
            printOutputStream?.flush()
        } catch (e: Exception) {
            Log.e("PrinterApp", "Error printing receipt:", e)
        }
    }

    // Implement button or functionality to trigger printing (replace with your UI interaction)
    private fun printButtonOnClick() {

        val formattedReceipt = formatReceipt(addCartProduct.value)
        printReceipt(formattedReceipt)
    }

    fun connectButtonOnClick() {
        showDeviceSelectionDialog()
    }
    private fun showDeviceSelectionDialog() {
        val pairedDevices = bluetoothAdapter?.bondedDevices ?: emptyList()

        val devices = pairedDevices.map { if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
            it.name to it }.toMap()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, devices.keys.toList())

        val dialog = AlertDialog.Builder(this)
            .setTitle("Select Printer")
            .setAdapter(adapter) { dialog, which ->
                val selectedDevice = devices.values.toList()[which]
                connectToPrinter(selectedDevice)
                dialog.dismiss()
            }
            .create()
        dialog.show()
    }


    private val REQUEST_BLUETOOTH_PERMISSION = 101
    private val BLUETOOTH_PRINTER_SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB" // Replace with UUID specific to your


}