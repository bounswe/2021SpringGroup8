package com.example.signin_signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val btnSignIn = findViewById<Button>(R.id.btnSignIn)
        val btnGoSignUp = findViewById<Button>(R.id.btnGoSignUpPage)
        btnSignIn.setOnClickListener {
            var text = findViewById<EditText>(R.id.inputEmail).text.toString()
            var text2 = findViewById<EditText>(R.id.inputPassword).text.toString()
            text = text.plus(" aka $text2 has entered")
            Toast.makeText(this@MainActivity, text, Toast.LENGTH_SHORT).show()
        }

        btnGoSignUp.setOnClickListener {
            setContentView(R.layout.sign_up_page)
        }

    }
}