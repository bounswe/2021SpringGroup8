package com.example.facespace

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class HomePageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        supportActionBar?.hide()

        val extras = intent.extras
        var username = extras?.get("username")

        val greetingTV = findViewById<TextView>(R.id.greeting)
        greetingTV.text = "Hello, $username!"

        val goProfile = findViewById<ImageView>(R.id.imageProfile)
        val goAllComm = findViewById<ImageView>(R.id.imageAllComm)
        val goMyComm = findViewById<ImageView>(R.id.imageMyComm)
        val logout = findViewById<ImageView>(R.id.imageLogOut)

        goProfile.setOnClickListener {
            Toast.makeText(this, "${username.toString()} wants to go his profile.", Toast.LENGTH_LONG).show()
        }

        goAllComm.setOnClickListener {
            val intent = Intent(this, CommunitiesPageActivity::class.java)
            startActivity(intent)
        }

        logout.setOnClickListener {
            val intent = Intent(this, LoginPageActivity::class.java)
            greetingTV.text = "Hello, sadece Cavus!"
            startActivity(intent)
        }
    }
}