package com.devsphere.interfaceimpl

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.devsphere.interfaceimpl.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel   // 👈 Declare variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ Initialize ViewModel manually (old-school way)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        // Observe LiveData
        viewModel.isLoading.observe(this) { isLoading ->
            binding.progress.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
            binding.btnLogin.isEnabled = !isLoading
            binding.btnRegister.isEnabled = !isLoading
        }

        viewModel.isSuccess.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "User registered successfully!", Toast.LENGTH_SHORT).show()
                goToMain()
            } else {
                Toast.makeText(this, "Registration failed!", Toast.LENGTH_SHORT).show()
            }
        }

        // Click listeners
        binding.btnLogin.setOnClickListener { goToMain() }

        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            viewModel.registerUser(name, email)
        }
    }

    private fun goToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
