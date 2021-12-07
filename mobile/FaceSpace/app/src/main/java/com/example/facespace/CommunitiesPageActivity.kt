package com.example.facespace

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

        commAdapter = CommunityAdapter(mutableListOf())

        val btnCreate = findViewById<Button>(R.id.btnCreate)
        val btnRefresh = findViewById<Button>(R.id.btnRefresh)

        val rvComms = findViewById<RecyclerView>(R.id.rvCommunityItems)

        rvComms.adapter = commAdapter
        rvComms.layoutManager = LinearLayoutManager(this)

        val editTitle = findViewById<EditText>(R.id.Title)
        val editDesc = findViewById<EditText>(R.id.Desc)
        val editBy = findViewById<EditText>(R.id.By)

        btnRefresh.setOnClickListener {
            commAdapter.deleteAll()
            getCommunities()
        }

        btnCreate.setOnClickListener {

            val data = Data()
            Toast.makeText(this, data.getUsername(), Toast.LENGTH_SHORT).show()
            val title = editTitle.text.toString()
            val desc = editDesc.text.toString()
            val by = editBy.text.toString()

            val current = LocalDateTime.now()

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val formatted = current.format(formatter) as String



            if(title.isNotEmpty() && desc.isNotEmpty() && by.isNotEmpty()) {
                val comm = Community(title,by,desc,false, formatted)
                commAdapter.addComm(comm)
                editTitle.text.clear()
                editDesc.text.clear()
                editBy.text.clear()

            } else {
                Toast.makeText(this, "Fill all cells", Toast.LENGTH_SHORT).show()
            }
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
            Toast.makeText(this,"${list::class.qualifiedName}", Toast.LENGTH_SHORT).show()
            val commJson = JSONObject(current.toString())
            val title = commJson["CommunityTitle"]
            val desc = "yalan"
            val by = JSONObject(commJson["createdBy"].toString())["username"]
            val since = JSONObject(commJson["creationTime"].toString())["_isoformat"].toString()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val formatted = since.format(formatter) as String

            val comm = Community(title as String, by.toString(), desc,false, formatted)
            commAdapter.addComm(comm)

        }
    }
}