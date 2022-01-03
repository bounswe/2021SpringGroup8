package com.example.facespace

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
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
    var result : String = ""
    private lateinit var postAdapter: PostAdapter
    private lateinit var commAdapter: CommunityAdapter
    private val username = Data().getUsername()
    private val userId = Data().getToken()
    private var CommunitySubs: JSONArray? = null
    private var isSubscribed = isInComm(username)
    private var comm_obj = ""
    var isShowed = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_page)
        supportActionBar?.hide()
        val intent = intent
        comm_obj = intent.getStringExtra("result").toString()
        val res = JSONObject(comm_obj)
        val comm_creator = JSONObject(res["createdBy"].toString())["username"].toString()
        val commId = res["id"].toString()
        CommunitySubs = JSONArray(res["subscribers"].toString())

        postAdapter = PostAdapter(mutableListOf())
        commAdapter = CommunityAdapter(mutableListOf())

        val btnCreatePost = findViewById<FloatingActionButton>(R.id.createPost)
        val btnRefresh = findViewById<FloatingActionButton>(R.id.btnRefresh)
        val btnGoHome = findViewById<FloatingActionButton>(R.id.btnGoHome)
        val btnLogout = findViewById<FloatingActionButton>(R.id.btnLogout)
        val btnSubs = findViewById<Button>(R.id.btnSubscribe)


        val upperLay = findViewById<ConstraintLayout>(R.id.upperTopPanel)

        isSubscribed = isInComm(username)
        if (isSubscribed) {
            btnSubs.text = getText(R.string.unsubscribe)
        }
        else btnSubs.text = getText(R.string.subscribe)

        val rvPosts = findViewById<RecyclerView>(R.id.rvPostItems)

        rvPosts.adapter = postAdapter
        rvPosts.layoutManager = LinearLayoutManager(this)

        btnRefresh.bringToFront()
        btnCreatePost.bringToFront()
        btnGoHome.bringToFront()
        btnLogout.bringToFront()
        btnSubs.bringToFront()

        btnRefresh.setOnClickListener {
            val intent = Intent(this, CommunityPageActivity::class.java)
            intent.putExtra("result", res.toString())
            startActivity(intent)
        }

        btnGoHome.setOnClickListener {
            val intent = Intent(this, HomePageActivity::class.java)
            intent.putExtra("username", Data().getUsername())
            startActivity(intent)
        }

        btnLogout.setOnClickListener {
            val intent = Intent(this, LoginPageActivity::class.java)
            startActivity(intent)
        }

        btnSubs.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                getSubscribers(commId)
                //Toast.makeText(baseContext, "suan text: " + btnSubs.text + "ve isInComm: " + isSubscribed, Toast.LENGTH_LONG).show()
                if (btnSubs.text == getText(R.string.unsubscribe)) {
                    if (isSubscribed) {
                        unsubscribe(userId, commId)
                    }
                    btnSubs.text = getText(R.string.subscribe)
                } else {
                    if (!isSubscribed) {
                        subscribe(userId, commId)
                    }
                    btnSubs.text = getText(R.string.unsubscribe)
                }
            }
        })
        val btnShow = findViewById<Button>(R.id.showFields)
        val btnDeleteComm = findViewById<Button>(R.id.btnDeleteComm)
        val params = btnShow.layoutParams as ConstraintLayout.LayoutParams
        if(btnDeleteComm.parent != null) {
            (btnDeleteComm.parent as ViewGroup).removeView(btnDeleteComm)
            params.topToBottom = btnSubscribe.id
        }
        if (Data().getUsername() == comm_creator) {

            upperLay.addView(btnDeleteComm)
            params.topToBottom = btnDeleteComm.id
            btnDeleteComm.setOnClickListener {
                deleteComm(commId, res)
                val intent = Intent(this, HomePageActivity::class.java)
                intent.putExtra("username", username)
                startActivity(intent)
            }
            // btnDeleteComm.visibility = View.VISIBLE
            // btnDeleteComm.bringToFront()


        }
        val dataTypes = JSONArray(res["dataTypes"].toString()) // res is Community object
        val tvFields = findViewById<TextView>(R.id.tvFields)
        upperLay.removeView(tvFields)
        val fieldsJson = getFieldJson(dataTypes)

        btnShow.setOnClickListener{
            if(isShowed) {
                btnShow.text = "Show Fields"
                upperLay.removeView(tvFields)

            } else {
                btnShow.text = "Hide Fields"
                upperLay.addView(tvFields)
                tvFields.text = fieldsJson[result].toString()


            }
            isShowed=!isShowed
        }



        val btnSearch = findViewById<FloatingActionButton>(R.id.btnSearch)
        btnSearch.bringToFront()
        btnSearch.setOnClickListener {
            val intent = Intent(this, SearchPosts::class.java)
            intent.putExtra("result", res.toString())
            startActivity(intent)
        }

        val titleTV = findViewById<TextView>(R.id.communityTitle)
        val descTV = findViewById<TextView>(R.id.communityDescription)
        val byTV = findViewById<TextView>(R.id.communityCreator)
        val dateTV = findViewById<TextView>(R.id.communityDate)

        titleTV.text = res["communityTitle"].toString()
        descTV.text = res["description"].toString()
        byTV.text = JSONObject(res["createdBy"].toString())["username"].toString()
        dateTV.text = JSONObject(res["creationTime"].toString())["_isoformat"].toString().substring(0,10)

        val posts = JSONArray(res["posts"].toString())
        for (i in 0 until posts.length()) {
            val post = posts.getJSONObject(i)
            getPost(post)
        }


        //Toast.makeText(this, dataTypes.toString(), Toast.LENGTH_LONG).show()
        val typeNames = types(dataTypes)
        //Toast.makeText(this, typeNames.toString(), Toast.LENGTH_SHORT).show()
        val spin = findViewById<Spinner>(R.id.spinnerPosts)
        spin.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, typeNames)
        spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                result = typeNames[position]
                if(isShowed) {
                    tvFields.text = fieldsJson[result].toString()

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        val btnDataType = findViewById<Button>(R.id.dtButton)
        val createdBy = JSONObject(res["createdBy"].toString())["username"].toString()
        if(Data().getUsername().contains(createdBy)) {
            btnDataType.visibility = View.VISIBLE
        } else {
            btnDataType.visibility = View.INVISIBLE
        }

        btnCreatePost.setOnClickListener{
            var dataTypeFields: JSONObject? = null
            for (i in 0 until dataTypes.length()) {
                val currDataType = dataTypes.getJSONObject(i)
                val currDataTypeName = currDataType["name"]
                if(currDataTypeName == result) {
                    dataTypeFields = JSONObject(currDataType["fields"].toString())
                    break
                }
            }
            if(dataTypeFields == null) {
                Toast.makeText(this, "There is no data type for this community! " +
                        "You must create a data type before you can create a post.",
                        Toast.LENGTH_LONG).show()
            }
            else {
                val inte = Intent(this,CreatePostPage::class.java)
                inte.putExtra("commId", commId)
                inte.putExtra("typeName", result)
                inte.putExtra("dataTypeFields", dataTypeFields.toString())
                startActivity(inte)
            }
        }


        btnDataType.setOnClickListener{
            val int = Intent(this, CreateDataType::class.java)
            int.putExtra("id", commId)
            startActivity(int)
        }

        // Toast.makeText(this, "magam be", Toast.LENGTH_LONG).show()
    }

    private fun types(list: JSONArray):MutableList<String> {
        val liste : MutableList<String> = mutableListOf()
        for (i in 0 until list.length()) {
            val currType = list.getJSONObject(i)
            liste.add(currType["name"] as String)

        }
        return liste
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

    private fun subscribe(userId: String, commId: String) {
        val url = Data().getUrl("subscribetocommunity")
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
                        isSubscribed = true
                        Toast.makeText(this, "User with id $userId subscribed successfully!", Toast.LENGTH_SHORT).show()
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

    private fun unsubscribe(userId: String, commId: String) {
        val url = Data().getUrl("unsubscribecommunity")
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
                        isSubscribed = false
                        Toast.makeText(this, "User with id $userId unsubscribed successfully!", Toast.LENGTH_SHORT).show()
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
        val post = Post(title as String, by.toString(), id.toString(), date, comm_obj)
        postAdapter.addPost(post)
    }

    private fun deleteComm(comm_id: String, comm_data: JSONObject) {
        val url = Data().getUrl("deletecommunity")

        var error: JSONObject? = null

        val params1: MutableMap<String, String> = HashMap()
        params1["communityId"] = comm_id
        params1["@usertoken"] = Data().getToken()

        val stringRequest: StringRequest = object : StringRequest( Method.POST, url,
            Response.Listener { response ->
                try {
                    val jsonObject = JSONObject(response)
                    error = jsonObject
                    val success = jsonObject["@success"].toString()
                    Toast.makeText(baseContext, "Deletion of the community is $success", Toast.LENGTH_LONG).show()
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
        val title = comm_data["communityTitle"].toString()
        val date = JSONObject(comm_data["creationTime"].toString())["_isoformat"].toString().substring(0,10)
        val desc = comm_data["description"].toString()
        val by = JSONObject(comm_data["createdBy"].toString())["username"].toString()
        val comm = Community(title, by, desc, comm_data["subscribers"] as JSONArray,
            comm_data["posts"] as JSONArray, comm_data["dataTypes"] as JSONArray, date, comm_id)
        commAdapter.deleteComm(comm)
    }

    private fun getFieldJson(dataTypes:JSONArray):JSONObject { // field of a community object
        val fields = JSONObject()
        for (i in 0 until dataTypes.length()) {
            val currDT = dataTypes.getJSONObject(i)
            val nameOfDT:String = currDT["name"].toString()
            val fieldsJson:JSONObject = currDT["fields"] as JSONObject
            val fieldNames:String =  fieldsJson.keys().asSequence().toList().toString()
            fields.put(nameOfDT, fieldNames)
        }

        return fields
    }
}
