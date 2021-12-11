package com.example.facespace

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_communities_page.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CommunitiesPageActivity : AppCompatActivity() {
    private lateinit var commAdapter: CommunityAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_communities_page)
        supportActionBar?.hide()

        commAdapter = CommunityAdapter(mutableListOf())

        val btnAdd = findViewById<FloatingActionButton>(R.id.btnAdd)
        val btnRefresh = findViewById<FloatingActionButton>(R.id.btnRefresh)
        val btnGoHome = findViewById<FloatingActionButton>(R.id.btnGoHome)
        val btnLogout = findViewById<FloatingActionButton>(R.id.btnLogout)

        val rvComms = findViewById<RecyclerView>(R.id.rvCommunityItems)

        rvComms.adapter = commAdapter
        rvComms.layoutManager = LinearLayoutManager(this)

        val editTitle = findViewById<EditText>(R.id.Title)
        val editDesc = findViewById<EditText>(R.id.Desc)
        getCommunities()
        btnRefresh.bringToFront()
        btnAdd.bringToFront()
        btnGoHome.bringToFront()
        btnLogout.bringToFront()
        btnRefresh.setOnClickListener {
            commAdapter.deleteAll()
            getCommunities()
        }

        btnGoHome.setOnClickListener {
            val intent = Intent(this, HomePageActivity::class.java)
            intent.putExtra("username", Data().getUsername())
            startActivity(intent)
        }

        btnAdd.setOnClickListener {

            var dialog = CreateCommunity()
            dialog.show(supportFragmentManager, "Create New Community")

        }

        btnLogout.setOnClickListener {
            val intent = Intent(this, LoginPageActivity::class.java)
            startActivity(intent)
        }

    }

    fun getCommunities(){

        // val allComm: MutableList<Community>

        val url = "http://3.144.184.237:8080/getallcommunities"
        var error: JSONObject? = null

        val stringRequest: StringRequest = @RequiresApi(Build.VERSION_CODES.O)
        object : StringRequest( Method.POST, url,
            Response.Listener { response ->
                try {
                    //Parse your api responce here


                    val jsonObject = JSONObject(response)

                    error = jsonObject
                    val comms:JSONArray = jsonObject["@return"] as JSONArray

                    helper(comms)
                    // Data().setUsername(token as String)

                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(this, (error?.get("@error")) as String, Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
            }) {
            // get Pramas would be override here
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun helper(list: JSONArray) {
        print(list.toString())
        for (i in 0 until list.length()) {
            val current = list.getJSONObject(i)
            val commJson = JSONObject(current.toString())
            val title = commJson["CommunityTitle"]
            val id = commJson["id"]
            val desc = "yalan"
            val by = JSONObject(commJson["createdBy"].toString())["username"]
            var since = JSONObject(commJson["creationTime"].toString())["_isoformat"]
            val time:String = since.toString()
            // val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            // val formatted = since.format(formatter) as String
            val comm = Community(title as String, by.toString(), desc,false, time.substring(0,10), id.toString())
            commAdapter.addComm(comm)

        }
    }


}