package com.example.grocerystoreclothes.view.setting

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.grocerystoreclothes.R
import com.example.grocerystoreclothes.databinding.ActivitySettingBinding
import com.example.grocerystoreclothes.view.createUser.CreateUserByAdminActivity
import com.example.grocerystoreclothes.view.signup.SignInActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private val viewmodel: SettingViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySettingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (viewmodel.isAdminLogIn()) {
            binding.btnAddUser.visibility = View.VISIBLE
            binding.btnAddUser.setOnClickListener {
                it.context.startActivity(
                    Intent(
                        it.context,
                        CreateUserByAdminActivity::class.java
                    ).apply {
                        // putExtra("keyIdentifier", value)
                    })
            }
        } else {
            binding.btnAddUser.visibility = View.GONE
        }

        binding.btnLogout.setOnClickListener {
            if (viewmodel.isAdminLogIn()) {
                viewmodel.adminLogOut(false)
            } else {
                // logout normal user

            }

            it.context.startActivity(Intent(it.context, SignInActivity::class.java).apply {
                // putExtra("keyIdentifier", value)
            })
            finish()
        }


    }
}