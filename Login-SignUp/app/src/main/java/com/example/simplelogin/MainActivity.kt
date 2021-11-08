package com.example.simplelogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnSign = findViewById<Button>(R.id.button)
        val btnForget = findViewById<Button>(R.id.buttonForgot)
        val btnSignUP = findViewById<Button>(R.id.buttonSignUp)
        btnSign.setOnClickListener {
            val temp = findViewById<EditText>(R.id.inputmail)
            var text = temp.text.toString()
            text = text.plus(" has entered")
            Toast.makeText(this@MainActivity, text, Toast.LENGTH_SHORT).show();
        }

        btnForget.setOnClickListener {


            Toast.makeText(this@MainActivity, "salaksın ki", Toast.LENGTH_SHORT).show();
        }

        btnSignUP.setOnClickListener {
            setContentView(R.layout.signup)
        }


    /*


        val btnSignUP2 = findViewById<Button>(R.id.button_signup)
        val goLoginPage = findViewById<Button>(R.id.buttonLogin)

        btnSignUP2.setOnClickListener {
            var textmail = findViewById<EditText>(R.id.inputmail2).text.toString()
            val textUsername = findViewById<EditText>(R.id.inputusername).text.toString()
            textmail = textmail.plus(" aka $textUsername has entered")
            Toast.makeText(this@MainActivity, textmail, Toast.LENGTH_SHORT).show();
        }

        goLoginPage.setOnClickListener {


            setContentView(R.layout.activity_main)
        }


     */


    }
}