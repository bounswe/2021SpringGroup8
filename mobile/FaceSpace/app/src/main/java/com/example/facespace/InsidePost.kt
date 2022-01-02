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
    private lateinit var postAdapter: PostAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inside_post)
        supportActionBar?.hide()

        postAdapter = PostAdapter(mutableListOf())

        val intent1 = intent
        val post_data = JSONObject(intent1.getStringExtra("result").toString())
        val comm_data = JSONObject(intent1.getStringExtra("comm_obj").toString())
        val post_creator = JSONObject(post_data["postedBy"].toString())["username"].toString()
        val comm_creator = JSONObject(comm_data["createdBy"].toString())["username"].toString()
        val postId = post_data["id"].toString()
        val commId = comm_data["id"].toString()
        Toast.makeText(this,"BURADAYIM BU DA COMMID: $commId", Toast.LENGTH_SHORT).show()

        val btnAdd = findViewById<FloatingActionButton>(R.id.btnAddP)
        val btnRefresh = findViewById<FloatingActionButton>(R.id.btnRefreshP)
        val btnGoHome = findViewById<FloatingActionButton>(R.id.btnGoHomeP)
        val btnLogout = findViewById<FloatingActionButton>(R.id.btnLogoutP)
        val btnMaps = findViewById<Button>(R.id.btnSeeLoc)
        val btnDeletePost = findViewById<Button>(R.id.btnDeletePost)

        btnRefresh.bringToFront()
        btnAdd.bringToFront()
        btnGoHome.bringToFront()
        btnLogout.bringToFront()
        btnMaps.bringToFront()

        if (Data().getUsername() == post_creator || Data().getUsername() == comm_creator) {
            btnDeletePost.visibility = View.VISIBLE
            btnDeletePost.bringToFront()
        }
        else {
            btnDeletePost.visibility = View.INVISIBLE
        }

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

        btnDeletePost.setOnClickListener {
            deletePost(postId, post_data, comm_data.toString())
            val comm_data_new = JSONObject()

            try {
                val temp: Iterator<String> = comm_data.keys()
                while (temp.hasNext()) {
                    val key = temp.next()
                    val value: Any = comm_data.get(key)
                    if (key != "posts") {
                        comm_data_new.put(key, value)
                    }
                    else {
                        val comm_json = comm_data["posts"] as JSONArray
                        val comm_json_new = JSONArray()
                        for (i in 0 until comm_json.length()) {
                            val currPost = comm_json.getJSONObject(i)
                            if (currPost["id"].toString() != postId) {
                                comm_json_new.put(currPost)
                            }
                        }
                        comm_data_new.put(key, comm_json_new)
                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            val intent: Intent = Intent(this, CommunityPageActivity::class.java)
            intent.putExtra("result", comm_data_new.toString())
            startActivity(intent)
        }

        val intent = intent
        val res = intent.getStringExtra("result").toString()
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

    private fun deletePost(post_id: String, post_data: JSONObject, comm_data:String) {
        val url = Data().getUrl("deletepost")

        var error: JSONObject? = null

        val params1: MutableMap<String, String> = HashMap()
        params1["postId"] = post_id
        params1["@usertoken"] = Data().getToken()

        val stringRequest: StringRequest = object : StringRequest( Method.POST, url,
            Response.Listener { response ->
                try {
                    val jsonObject = JSONObject(response)
                    error = jsonObject
                    val success = jsonObject["success"].toString()
                    Toast.makeText(baseContext, "Deletion of the post is $success", Toast.LENGTH_LONG).show()
                } catch (e: JSONException) {
                    Toast.makeText(this, (error?.get("@error")) as String, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
            }) {
            override fun getParams(): Map<String, String> {
                return params1
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
        val title = post_data["postTitle"].toString()
        val by = JSONObject(post_data["postedBy"].toString())["username"].toString()
        val date = JSONObject(post_data["creationTime"].toString())["_isoformat"].toString().substring(0,10)
        val post = Post(title, by, post_id, date, comm_data)
        postAdapter.deletePost(post)
    }
}