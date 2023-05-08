package com.example.sensoresapp.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.sensoresapp.databinding.ActivityMainBinding
import com.example.sensoresapp.databinding.ActivitySplashBinding
import com.example.sensoresapp.ui.MainActivity

class SplashActivity : AppCompatActivity() {
    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showNextActivity()
    }

    private fun showNextActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            Intent(this, MainActivity::class.java).apply{
                startActivity(this)
                finish()
            }
        }, 3000)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}