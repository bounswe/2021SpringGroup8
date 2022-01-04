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
import kotlinx.android.synthetic.main.activity_profile_page.*
import kotlinx.android.synthetic.main.activity_profile_page.view.*
import androidx.constraintlayout.widget.ConstraintSet

import android.widget.ImageView

import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_inside_post.*


class InsidePost : AppCompatActivity() {
    private lateinit var postAdapter: PostAdapter
    private lateinit var fieldObjectAdapter: FieldObjectAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inside_post)
        supportActionBar?.hide()

        postAdapter = PostAdapter(mutableListOf())
        fieldObjectAdapter = FieldObjectAdapter(mutableListOf())

        val rvFieldObjects = findViewById<RecyclerView>(R.id.rvFieldObjects)
        rvFieldObjects.adapter = fieldObjectAdapter
        rvFieldObjects.layoutManager = LinearLayoutManager(this)

        val intent1 = intent
        val post_data = JSONObject(intent1.getStringExtra("result").toString())
        val dt_name = post_data["dataTypeName"].toString()
        val comm_data = JSONObject(intent1.getStringExtra("comm_obj").toString())
        val comm_data_types = comm_data["dataTypes"] as JSONArray
        val post_creator = JSONObject(post_data["postedBy"].toString())["username"].toString()
        val comm_creator = JSONObject(comm_data["createdBy"].toString())["username"].toString()
        val postId = post_data["id"].toString()


        val btnDeletePost = findViewById<Button>(R.id.btnDeletePost)
        val valLoc = findViewById<Button>(R.id.valLoc)

        val titleTV = findViewById<TextView>(R.id.titlePostP)
        val byTV = findViewById<TextView>(R.id.byPostP)
        val dateTV = findViewById<TextView>(R.id.dateFieldP)

        titleTV.text = post_data["postTitle"].toString()
        byTV.text = JSONObject(post_data["postedBy"].toString())["username"].toString()
        dateTV.text = JSONObject(post_data["creationTime"].toString())["_isoformat"].toString().substring(0,10)

        val fields = JSONObject(post_data["fieldValues"].toString())
        var dt_fields = JSONObject()

        for (i in 0 until comm_data_types.length()) {
            val data_type = comm_data_types.getJSONObject(i)
            val data_type_name = data_type["name"].toString()
            if (data_type_name == dt_name) {
                dt_fields = JSONObject(data_type["fields"].toString())
            }
        }

        try {
            val temp: Iterator<String> = fields.keys()
            while (temp.hasNext()) {
                val key = temp.next()
                val value: Any = fields.get(key)
                val f_obj = FieldObject(key, dt_fields[key].toString(), value.toString(), this)
                fieldObjectAdapter.addFieldObject(f_obj)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }



        if (Data().getUsername() == post_creator || Data().getUsername() == comm_creator) {
            btnDeletePost.visibility = View.VISIBLE
            btnDeletePost.bringToFront()
        }
        else {
            btnDeletePost.visibility = View.INVISIBLE
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