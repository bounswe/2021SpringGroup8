package com.example.facespace

import android.R.attr
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.facespaceextenstion.Data
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import android.R.attr.data




class InsidePost : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inside_post)
        supportActionBar?.hide()

        val btnAdd = findViewById<FloatingActionButton>(R.id.btnAddP)
        val btnRefresh = findViewById<FloatingActionButton>(R.id.btnRefreshP)
        val btnGoHome = findViewById<FloatingActionButton>(R.id.btnGoHomeP)
        val btnLogout = findViewById<FloatingActionButton>(R.id.btnLogoutP)
        val btnMaps = findViewById<Button>(R.id.btnSeeLoc)

        btnRefresh.bringToFront()
        btnAdd.bringToFront()
        btnGoHome.bringToFront()
        btnLogout.bringToFront()
        btnMaps.bringToFront()

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

        btnMaps.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }

        // val infos = intent.getSerializableExtra("keys") as HashMap<String, String>
        // var value: Serializable = extras?.
        val intent = intent
        val res = intent.getStringExtra("result")
        val resJson = JSONObject(res)

        val titleTV = findViewById<TextView>(R.id.titlePostP)
        val byTV = findViewById<TextView>(R.id.byPostP)
        val dateTV = findViewById<TextView>(R.id.dateFieldP)
        val descTV = findViewById<TextView>(R.id.descPostP)

        titleTV.text = resJson["postTitle"].toString()
        byTV.text = JSONObject(resJson["postedBy"].toString())["username"].toString()
        dateTV.text = JSONObject(resJson["creationTime"].toString())["_isoformat"].toString().substring(0,10)
        descTV.text = ""

        val fields = JSONObject(resJson["fieldValues"].toString())

        try {
            val temp: Iterator<String> = fields.keys()
            while (temp.hasNext()) {
                val key = temp.next()
                val value: Any = fields.get(key)
                val temp = descTV.text as String + key.toString() + " : " + value.toString() + "," + "\n"
                descTV.text = temp
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}