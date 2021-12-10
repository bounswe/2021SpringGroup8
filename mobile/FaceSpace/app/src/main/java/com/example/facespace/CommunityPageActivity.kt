package com.example.facespace

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_community_page.*
import kotlinx.android.synthetic.main.community_item.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable

class CommunityPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_page)
        supportActionBar?.hide()
        // val infos = intent.getSerializableExtra("keys") as HashMap<String, String>
        // var value: Serializable = extras?.
        val intent = intent
        val infos = intent.getSerializableExtra("keys") as HashMap<*, *>?
        infos!!["key"]?.let { Log.v("HashMapTest", it as String) }

        val titleTV = findViewById<TextView>(R.id.communityTitle)
        val descTV = findViewById<TextView>(R.id.communityDescription)
        val byTV = findViewById<TextView>(R.id.communityCreator)
        val dateTV = findViewById<TextView>(R.id.communityDate)

        // val infos: MutableMap<String, String> = Data().getCommInfo()

        titleTV.text = infos["title"].toString()
        descTV.text = infos["desc"].toString()
        byTV.text = infos["by"].toString()
        dateTV.text = infos["date"].toString()

        // Toast.makeText(this, "magam be", Toast.LENGTH_LONG).show()
    }


}