package com.example.facespace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.allViews
import org.json.JSONObject

class SearchPosts : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_posts)
        var isVisible:Boolean  = true
        val intent = intent
        val res = intent.getStringExtra("result")
        val capt = findViewById<TextView>(R.id.textCaption)
        capt.text = res
        val resJson = JSONObject(res) // it is an Community Object
        val lay = findViewById<ConstraintLayout>(R.id.layout1)

        val btn = findViewById<Button>(R.id.button)
        btn.setOnClickListener {
            if(isVisible) {
                lay.removeAllViews()
                isVisible = false
            } else {
                lay.display
                isVisible = true
            }

        }
    }
}