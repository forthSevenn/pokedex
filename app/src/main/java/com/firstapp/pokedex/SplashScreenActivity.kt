package com.firstapp.pokedex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        this.supportActionBar?.hide()

        val splashBtn = findViewById<Button>(R.id.splashBtn)

        splashBtn.setOnClickListener {
            val splashIntent = Intent(this, MainActivity::class.java)
            startActivity(splashIntent)
        }
    }
}