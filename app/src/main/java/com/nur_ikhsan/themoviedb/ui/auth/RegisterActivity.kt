package com.nur_ikhsan.themoviedb.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.nur_ikhsan.themoviedb.databinding.ActivityRegisterBinding
import com.nur_ikhsan.themoviedb.ui.activity.MainActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarMovie.setNavigationOnClickListener {
            onBackPressed()
        }

        auth = FirebaseAuth.getInstance()

        binding.apply {

            btnRegister.setOnClickListener {

                val name = etUsername.text.toString().trim()
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString().trim()

                if (name.isEmpty()){
                    etUsername.error = "Username tidak boleh kosong"
                    etUsername.requestFocus()
                    return@setOnClickListener
                }

                if (email.isEmpty()){
                    etEmail.error = "Email tidak boleh kosong"
                    etEmail.requestFocus()
                    return@setOnClickListener
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    etEmail.error = "Email tidak boleh kosong"
                    etEmail.requestFocus()
                    return@setOnClickListener
                }

                if (password.isEmpty() || password.length < 6){
                    etPassword.error = "Password harus lebih dari 6 karakter"
                    etPassword.requestFocus()
                    return@setOnClickListener
                }

                registerUser(email, password, name)
            }
        }
    }

    private fun registerUser(email: String, password: String, name : String) {
        binding.progressBar.isVisible = true
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful && it.result.user != null){
                    val firebaseUser = it.result.user
                    if (firebaseUser != null){
                        val request = UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build()
                        firebaseUser.updateProfile(request)
                    }

                    Intent(this, MainActivity::class.java).also { intent ->
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        Toast.makeText(this, "Registrasi berhasil, selamat datang $name", Toast.LENGTH_SHORT).show()
                        finish()
                        binding.progressBar.isVisible = false
                    }
                }else{
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                    binding.progressBar.isVisible = false
                }
            }
    }
    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null){
            Intent(this, MainActivity::class.java).also { intent ->
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }
    }
}