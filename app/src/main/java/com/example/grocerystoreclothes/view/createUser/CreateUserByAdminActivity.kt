package com.example.grocerystoreclothes.view.createUser

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.grocerystoreclothes.view.home.MainActivity
import com.example.grocerystoreclothes.R
import com.example.grocerystoreclothes.databinding.ActivityCreateUserByAdmminBinding
import com.example.grocerystoreclothes.model.userDetail.UserModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern

@AndroidEntryPoint
class CreateUserByAdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateUserByAdmminBinding
    private val viewModel: CreateUserAdminViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCreateUserByAdmminBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        binding.btnAddUser.setOnClickListener {
            if (checkValidation()) {
                // add user list into shared preference

                val newAddUser = UserModel(
                    id = 1,
                    name = binding.editTextName.text.toString(),
                    email = binding.editTextEmail.text.toString(),
                    password = binding.editTextPassword.text.toString()
                )
                if(viewModel.addUsersToPreferenceList(this, newAddUser)){
                    it.context.startActivity(Intent(it.context, MainActivity::class.java).apply {
                    })
                    finish()
                }

                Log.e("TAG", "onCreate: valid ")
            }
        }
    }


    private fun checkValidation(): Boolean {

        if (!Patterns.EMAIL_ADDRESS.matcher(binding.editTextEmail.text.toString()).matches()) {
            binding.editTextEmail.error = "Enter valid email"
            return false
        } else if (!isValidPassword(binding.editTextPassword.text.toString())) {
            binding.editTextPassword.error = "Password should be at least 6 characters"
            return false
        } else if (binding.editTextPassword.text.toString() != binding.editTextConfirmPassword.text.toString()) {
            binding.editTextConfirmPassword.error = "Password and confirm password is not matched"
            return false
        }
        return true
    }

    private fun isValidPassword(password: String): Boolean {
        val pattern = Pattern.compile("^.{6,}$")
        return pattern.matcher(password).matches()
    }
}