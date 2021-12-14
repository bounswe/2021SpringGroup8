package com.example.facespace

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.facespaceextenstion.Data
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_create_post_page.*

class CreatePostPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post_page)
        supportActionBar?.hide()
        val commName: String? = intent.getStringExtra("commName")
        val commId: String? = intent.getStringExtra("commId")
        val typeName: String? = intent.getStringExtra("typeName")

        tv1.text = "Create Post for $commName"
        tv2.text = "Post Type is \n $typeName"

        val btnAdd = findViewById<FloatingActionButton>(R.id.btnAddC)
        val btnRefresh = findViewById<FloatingActionButton>(R.id.btnRefreshC)
        val btnGoHome = findViewById<FloatingActionButton>(R.id.btnGoHomeC)
        val btnLogout = findViewById<FloatingActionButton>(R.id.btnLogoutC)

        btnRefresh.bringToFront()
        btnAdd.bringToFront()
        btnGoHome.bringToFront()
        btnLogout.bringToFront()

        btnRefresh.setOnClickListener {
            Toast.makeText(baseContext, "This feature is under construction!", Toast.LENGTH_LONG).show()
        }

        btnGoHome.setOnClickListener {
            val intent = Intent(this, HomePageActivity::class.java)
            intent.putExtra("username", Data().getUsername())
            startActivity(intent)
        }

        btnAdd.setOnClickListener {
            Toast.makeText(baseContext, "This feature is under construction!", Toast.LENGTH_LONG).show()
        }

        btnLogout.setOnClickListener {
            val intent = Intent(this, LoginPageActivity::class.java)
            startActivity(intent)
        }
    }
}