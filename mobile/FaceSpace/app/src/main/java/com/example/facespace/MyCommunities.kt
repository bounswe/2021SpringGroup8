package com.example.facespace

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.facespaceextenstion.Data
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_my_communities.*
import kotlinx.android.synthetic.main.activity_my_communities.view.*
import kotlinx.android.synthetic.main.activity_search_posts.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MyCommunities : AppCompatActivity() {
    private lateinit var commAdapter: CommunityAdapter
    var onlyCreated = false
    var isOpen:Boolean = false
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_communities)
        supportActionBar?.hide()

        val intent1 = intent
        onlyCreated = intent1.getBooleanExtra("onlyCreated", false)

        commAdapter = CommunityAdapter(mutableListOf())

        val btnAdd = findViewById<FloatingActionButton>(R.id.btnAddM)
        val btnRefresh = findViewById<FloatingActionButton>(R.id.btnRefreshM)
        val btnGoHome = findViewById<FloatingActionButton>(R.id.btnGoHomeM)
        val btnLogout = findViewById<FloatingActionButton>(R.id.btnLogoutM)
        val crtdComms = findViewById<Button>(R.id.crtdComms)
        val currentView = findViewById<TextView>(R.id.currentViewText)
        val parentLay = findViewById<ConstraintLayout>(R.id.parentLayout)
        val searchPanel = findViewById<ConstraintLayout>(R.id.searchPanel)
        val btnSearch = findViewById<FloatingActionButton>(R.id.btnSearch)
        btnSearch.bringToFront()
        btnSearch.setOnClickListener {
            if(isOpen) {
                parentLay.removeView(searchPanel)
            } else {
                parentLay.addView(searchPanel)
            }
            isOpen = !isOpen
            // Toast.makeText(this@SearchPosts,"$dataType and $field and $filterType", Toast.LENGTH_SHORT).show()
        }

        val btnEnterSearch = findViewById<Button>(R.id.enterSearch)
        val etQuery = findViewById<EditText>(R.id.etQuery)
        btnEnterSearch.setOnClickListener{
            val query:String = etQuery.text.toString()
            getMyCommunities(query)
        }

        if(onlyCreated) {
            crtdComms.text = "Show Subscribed"
            currentView.text = "Communities I Created"
        }
        else {
            crtdComms.text = "Show Created"
            currentView.text = "Communities I Subscribed"
        }

        val rvComms = findViewById<RecyclerView>(R.id.rvMyCommItems)

        rvComms.adapter = commAdapter
        rvComms.layoutManager = LinearLayoutManager(this)

        getMyCommunities()
        btnRefresh.bringToFront()
        btnAdd.bringToFront()
        btnGoHome.bringToFront()
        btnLogout.bringToFront()


        btnRefresh.setOnClickListener {
            getMyCommunities()
        }

        btnGoHome.setOnClickListener {
            val intent = Intent(this, HomePageActivity::class.java)
            startActivity(intent)
        }

        btnAdd.setOnClickListener {
            val dialog = CreateCommunity()
            dialog.show(supportFragmentManager, "Create New Community")
        }

        btnLogout.setOnClickListener {
            val intent = Intent(this, LoginPageActivity::class.java)
            startActivity(intent)
        }
3
        crtdComms.setOnClickListener {
            val intent = Intent(this, MyCommunities::class.java)
            intent.putExtra("onlyCreated", !onlyCreated)
            startActivity(intent)
        }
        parentLay.removeView(searchPanel)

    }

    private fun getMyCommunities(query:String=""){
        val params: MutableMap<String, String> = HashMap()
        params["@usertoken"] = Data().getToken()
        val token = params["@usertoken"]

        val url = Data().getUrl("getmyprofile")
        var error: JSONObject? = null
        val stringRequest: StringRequest = @RequiresApi(Build.VERSION_CODES.O)
        object : StringRequest( Method.POST, url,
            Response.Listener { response ->
                try {
                    val jsonObject = JSONObject(response)
                    error = jsonObject
                    if(onlyCreated) {
                        val subs: JSONArray = JSONObject((jsonObject["@return"]).toString())["createdCommunities"] as JSONArray
                        if (subs.length() != 0)  helper(subs, query)
                    }
                    else {
                        val subs: JSONArray = JSONObject((jsonObject["@return"]).toString())["subscribes"] as JSONArray
                        if (subs.length() != 0)  helper(subs, query)
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(this, (error?.get("@error")) as String, Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
            }) {
            override fun getParams(): Map<String, String> {
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun helper(list: JSONArray, query:String="") {
        commAdapter.deleteAll()
        for (i in 0 until list.length()) {
            val commJson = list.getJSONObject(i)
            val title = commJson["CommunityTitle"]
            val id = commJson["id"]
            val desc = commJson["description"]
            val by = JSONObject(commJson["createdBy"].toString())["username"]
            val since = JSONObject(commJson["creationTime"].toString())["_isoformat"]
            val time:String = since.toString()
            val comm = Community(title.toString(), by.toString(), desc.toString(),null,
                null,null, time.substring(0,10), id.toString())
            if(query=="") {
                commAdapter.addComm(comm)
            } else if(title.toString().contains(query, ignoreCase = true) &&
                desc.toString().contains(query, ignoreCase = true)) {
                commAdapter.addComm(comm)
            }

        }
    }
}