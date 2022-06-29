package com.karya.livedataviewmodel.NotesApp.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.karya.livedataviewmodel.NotesApp.activities.MainActivity
import com.karya.livedataviewmodel.R
import com.karya.livedataviewmodel.databinding.ActivitySplashScreenBinding

class SplashScreenAct : AppCompatActivity(R.layout.activity_splash_screen) {
    lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivSplash.alpha = 0f
        binding.ivSplash.animate().setDuration(2500).alpha(1f).withEndAction{
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }
    }
}