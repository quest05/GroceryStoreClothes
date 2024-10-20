package com.example.grocerystoreclothes.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.grocerystoreclothes.view.home.MainActivity
import com.example.grocerystoreclothes.R
import com.example.grocerystoreclothes.view.signup.SignInActivity
import com.example.grocerystoreclothes.view.signup.SignInViewmodel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel: SignInViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            if (viewModel.isAdminLogin()) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java).apply {
                })
                finish()
            } else {
                startActivity(Intent(this@SplashActivity, SignInActivity::class.java).apply {
                })
                finish()
            }
        }
    }
}