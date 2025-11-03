package com.devsphere.interfaceimpl

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.devsphere.interfaceimpl.databinding.ActivityLoginBinding
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val db by lazy { FirebaseFirestore.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // LOGIN: just navigate (no auth, no checks)
        binding.btnLogin.setOnClickListener {
            goToMain()
        }

        // REGISTER: add {name, email} to Firestore (auto doc id), then navigate
        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()

            if (name.isEmpty() || email.isEmpty()) {
                toast("Name and email required")
                return@setOnClickListener
            }

            setLoading(true)
            val user = User(name = name, email = email)
            db.collection("users")
                .add(user)
                .addOnSuccessListener {
                    setLoading(false)
                    toast("User saved")
                    goToMain()
                }
                .addOnFailureListener { e ->
                    setLoading(false)
                    toast("Failed to save: ${e.localizedMessage}")
                }
        }
    }

    private fun goToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun setLoading(loading: Boolean) {
        binding.progress.visibility = if (loading) View.VISIBLE else View.INVISIBLE
        binding.btnLogin.isEnabled = !loading
        binding.btnRegister.isEnabled = !loading
    }

    private fun toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}
