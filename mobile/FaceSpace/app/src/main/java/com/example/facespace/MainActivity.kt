package com.example.facespace

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val btnSignIn = findViewById<Button>(R.id.btnIn)
        val btnGoSignUp = findViewById<Button>(R.id.btnUp)
        btnSignIn.setOnClickListener {

            // val deneme = "deneme" // to send parameters to another activity

            val intent = Intent(this, LoginPageActivity::class.java)
            // intent.putExtra("key", deneme) // to send parameter to another activity
            startActivity(intent)

        }

        btnGoSignUp.setOnClickListener {
            val intent = Intent(this, SignUpPageActivity::class.java)
            startActivity(intent)


        }

    }
}