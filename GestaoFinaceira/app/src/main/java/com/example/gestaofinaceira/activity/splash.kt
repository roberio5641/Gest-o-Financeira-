package com.example.gestaofinaceira.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.gestaofinaceira.R

class splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val handler = Handler(Looper.myLooper()!!)

        handler.postDelayed({
            val it = Intent(this, Login::class.java)
            startActivity(it)
            finish()
        }, 1500)


    }
}