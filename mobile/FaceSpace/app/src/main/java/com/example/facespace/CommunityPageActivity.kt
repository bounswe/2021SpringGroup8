package com.example.facespace

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.facespaceextenstion.Data
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_community_page.*
import kotlinx.android.synthetic.main.community_item.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable

class CommunityPageActivity : AppCompatActivity() {
    private lateinit var postAdapter: PostAdapter
    private val username = Data().getUsername()
    private val userId = Data().getToken()
    private val commId = Data().getCurrentCommunityId()
    private var CommunitySubs: JSONArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_page)
        supportActionBar?.hide()
        Toast.makeText(this, "HELLOOOOOO", Toast.LENGTH_SHORT).show()

        postAdapter = PostAdapter(mutableListOf())

        val btnAdd = findViewById<FloatingActionButton>(R.id.btnAdd)
        val btnRefresh = findViewById<FloatingActionButton>(R.id.btnRefresh)
        val btnGoHome = findViewById<FloatingActionButton>(R.id.btnGoHome)
        val btnLogout = findViewById<FloatingActionButton>(R.id.btnLogout)
        val btnSubs = findViewById<Button>(R.id.btnSubscribe)
        val btnUnsubs = findViewById<Button>(R.id.btnUnsubscribe)

        val rvPosts = findViewById<RecyclerView>(R.id.rvPostItems)

        rvPosts.adapter = postAdapter
        rvPosts.layoutManager = LinearLayoutManager(this)

        btnRefresh.bringToFront()
        btnAdd.bringToFront()
        btnGoHome.bringToFront()
        btnLogout.bringToFront()

        btnRefresh.setOnClickListener {
            postAdapter.deleteAll()
            //getPost()
        }

        btnGoHome.setOnClickListener {
            val intent = Intent(this, HomePageActivity::class.java)
            intent.putExtra("username", Data().getUsername())
            startActivity(intent)
        }

        btnAdd.setOnClickListener {
            val dialog = CreatePost()
            dialog.show(supportFragmentManager, "Create New Post")
        }

        btnLogout.setOnClickListener {
            val intent = Intent(this, LoginPageActivity::class.java)
            startActivity(intent)
        }

        if (true) {
            btnSubs.isEnabled = false
            btnUnsubs.isEnabled = true
        }

        btnSubs.setOnClickListener {
            getSubscribers(commId)
            if (subscribe(username, commId)) {
                btnSubs.isEnabled = false
                btnUnsubs.isEnabled = true
            }
        }

        btnUnsubs.setOnClickListener {
            getSubscribers(commId)
            if (unsubscribe(username, commId)) {
                btnSubs.isEnabled = true
                btnUnsubs.isEnabled = false
            }
        }


        // val infos = intent.getSerializableExtra("keys") as HashMap<String, String>
        // var value: Serializable = extras?.
        val intent = intent
        val infos = intent.getSerializableExtra("keys") as HashMap<*, *>?
        val res = intent.getStringExtra("result").toString()
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

        val resJson = JSONObject(res)
        val posts = JSONArray(resJson["posts"].toString())
        for (i in 0 until posts.length()) {
            val post = posts.getJSONObject(i)
            getPost(post)
        }

        Toast.makeText(this, posts.toString(), Toast.LENGTH_LONG).show()

        // From here you continue from here to list posts previews


        // Toast.makeText(this, "magam be", Toast.LENGTH_LONG).show()
    }

    private fun isInComm(username: String): Boolean {
        var res: Boolean = false
        if (CommunitySubs != null) {
            for (i in 0 until CommunitySubs!!.length()) {
                val name = JSONObject(CommunitySubs!!.getJSONObject(i).toString())["username"].toString()
                if (name == username) {
                    res = true
                    break
                }
            }
        }
        return res
    }

    private fun getSubscribers(commId: String) {
        val url = Data().getUrl("getcommunity")

        var error: JSONObject? = null

        val params1: MutableMap<String, String> = HashMap()
        params1["communityId"] = commId

        val stringRequest: StringRequest = object : StringRequest( Method.POST, url,
            Response.Listener { response ->
                try {
                    //Parse your api responce here
                    val jsonObject = JSONObject(response)
                    error = jsonObject
                    val results = jsonObject["@return"] as JSONObject
                    CommunitySubs = JSONArray(results["subscribers"].toString())
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
            }) {
            override fun getParams(): Map<String, String> {
                return params1
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
        //Toast.makeText(this, "uyeler: " + CommunitySubs.toString(), Toast.LENGTH_LONG).show()
    }

    private fun subscribe(userId: String, commId: String): Boolean{
        val url = Data().getUrl("subscribetocommunity")
        var success: Boolean = false
        val params: MutableMap<String, String> = HashMap()
        params["@usertoken"] = userId
        params["communityId"] = commId

        var error: JSONObject? = null

        val stringRequest: StringRequest = object : StringRequest( Method.POST, url,
            Response.Listener { response ->
                try {
                    val jsonObject = JSONObject(response)
                    error = jsonObject
                    if (jsonObject["@success"] == "True") {
                        success = true
                        Toast.makeText(this, "user with id $userId subscribed successfully!", Toast.LENGTH_SHORT).show()
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
        return success
    }

    private fun unsubscribe(userId: String, commId: String): Boolean{
        val url = Data().getUrl("unsubscribecommunity")
        var success: Boolean = false
        val params: MutableMap<String, String> = HashMap()
        params["@usertoken"] = userId
        params["communityId"] = commId

        var error: JSONObject? = null

        val stringRequest: StringRequest = object : StringRequest( Method.POST, url,
            Response.Listener { response ->
                try {
                    val jsonObject = JSONObject(response)
                    error = jsonObject
                    if (jsonObject["@success"] == "True") {
                        success = true
                        Toast.makeText(this, "user with id $userId unsubscribed successfully!", Toast.LENGTH_SHORT).show()
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
        return success
    }

    private fun getPost(post: JSONObject){
        val url = Data().getUrl("viewpost")

        val params: MutableMap<String, String> = HashMap()
        params["postId"] = post["id"].toString()

        var error: JSONObject? = null

        val stringRequest: StringRequest = object : StringRequest( Method.POST, url,
            Response.Listener { response ->
                try {
                    val jsonObject = JSONObject(response)
                    error = jsonObject
                    helper(post)
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

    fun helper(elm: JSONObject) {
        val commJson = JSONObject(elm.toString())
        val title = commJson["postTitle"]
        val id = commJson["id"]
        val date = JSONObject(commJson["creationTime"].toString())["_isoformat"].toString().substring(0,10)
        //val desc = commJson["description"]
        val by = JSONObject(commJson["postedBy"].toString())["username"]
        //val commID = JSONObject(commJson["postedAt"].toString())["id"].toString()
        // val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        // val formatted = since.format(formatter) as String
        val post = Post(title as String, by.toString(), "", id.toString(), date)
        postAdapter.addPost(post)
    }


}