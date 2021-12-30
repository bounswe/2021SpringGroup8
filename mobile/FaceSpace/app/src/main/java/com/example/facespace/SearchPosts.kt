package com.example.facespace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.allViews
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.facespaceextenstion.Data
import kotlinx.android.synthetic.main.activity_search_posts.*
import kotlinx.android.synthetic.main.activity_search_posts.view.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class SearchPosts : AppCompatActivity() {
    private lateinit var postAdapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_posts)
        supportActionBar?.hide()
        var isVisible:Boolean  = true
        val intent = intent
        val res = intent.getStringExtra("result")
        val resJson = JSONObject(res) // it is an Community Object
        val lay = findViewById<ConstraintLayout>(R.id.layout1)
        postAdapter = PostAdapter(mutableListOf())
        val rvResults = findViewById<RecyclerView>(R.id.results)
        rvResults.adapter = postAdapter
        rvResults.layoutManager = LinearLayoutManager(this)
        val posts = JSONArray(resJson["posts"].toString())
        for (i in 0 until posts.length()) {
            val post = posts.getJSONObject(i)
            getPost(post)
        }
        val searchBar = findViewById<SearchView>(R.id.searchbar)
        val btn = findViewById<Button>(R.id.button)
        val parentLay = findViewById<ConstraintLayout>(R.id.parentLayout)
        btn.setOnClickListener {
            if(isVisible) {
                parentLay.removeView(layout1)
            } else {
                parentLay.addView(layout1)
            }
            isVisible = !isVisible
            Toast.makeText(this@SearchPosts,isVisible.toString(), Toast.LENGTH_SHORT).show()
        }

        searchBar.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(this@SearchPosts,"$query submitted", Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Toast.makeText(this@SearchPosts, "$newText changed", Toast.LENGTH_SHORT).show()
                return false
            }

        })
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
        val by = JSONObject(commJson["postedBy"].toString())["username"]
        val post = Post(title as String, by.toString(), "", id.toString(), date)
        postAdapter.addPost(post)
    }
}