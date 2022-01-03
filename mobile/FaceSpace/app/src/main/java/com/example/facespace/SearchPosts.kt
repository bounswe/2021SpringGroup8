package com.example.facespace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.allViews
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.facespaceextenstion.Data
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_community_page.*
import kotlinx.android.synthetic.main.activity_search_posts.*
import kotlinx.android.synthetic.main.activity_search_posts.view.*
import kotlinx.android.synthetic.main.filter_item.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class SearchPosts : AppCompatActivity() {
    private lateinit var postAdapter: PostAdapter
    private lateinit var filterAdapter: FilterAdapter
    var fieldNames: MutableList<String> = mutableListOf()
    var dataType:String = ""
    var field:String = ""
    var fieldType:String = ""
    var filterType:String = ""
    var currentFilterTypes = arrayOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_posts)
        supportActionBar?.hide()
        var isVisible:Boolean  = true
        val intent = intent
        val res = intent.getStringExtra("result").toString()
        val resJson = JSONObject(res) // it is an Community Object
        // val lay = findViewById<ConstraintLayout>(R.id.layout1)
        postAdapter = PostAdapter(mutableListOf())
        filterAdapter = FilterAdapter(mutableListOf())
        val rvResults = findViewById<RecyclerView>(R.id.results)
        val rvFilters = findViewById<RecyclerView>(R.id.rvFilters)
        rvResults.adapter = postAdapter
        rvResults.layoutManager = LinearLayoutManager(this)
        rvFilters.adapter = filterAdapter
        rvFilters.layoutManager = LinearLayoutManager(this)
        val posts = JSONArray(resJson["posts"].toString())
        for (i in 0 until posts.length()) {
            val post = posts.getJSONObject(i)
            getPost(post, res)
        }
        val searchBar = findViewById<EditText>(R.id.searchbar)
        val btn = findViewById<Button>(R.id.button)
        val parentLay = findViewById<ConstraintLayout>(R.id.parentLayout)

        val datatypes = JSONArray(resJson["dataTypes"].toString())
        val typeNames = types(datatypes)
        val filterTypesInt = arrayOf("greater", "less", "greater or equal",
            "less or equal", "equal")
        val filterTypesText = arrayOf("search text")
        val filterTypesBool = arrayOf( "checked", "unchecked")
        val spin = findViewById<Spinner>(R.id.spinnerDataTypes)
        val spinFields = findViewById<Spinner>(R.id.spinnerFields)
        val spinFilters = findViewById<Spinner>(R.id.spinnerFilters)
        spin.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, typeNames)
        spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                dataType = typeNames[position]
                fieldNames = fields(datatypes, dataType)
                if(fieldNames.isEmpty()) {
                    field=""
                    currentFilterTypes = arrayOf()
                    spinFilters.adapter = ArrayAdapter(applicationContext,
                        android.R.layout.simple_list_item_1, arrayOf<String>())
                    filterType=""
                }
                spinFields.adapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, fieldNames)

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        spinFields.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                fieldType=""
                if(!fieldNames.isEmpty()) {
                    field = (fieldNames[position]).split("-")[0]
                    fieldType = (fieldNames[position]).split("-")[1]
                }

                if(fieldType=="bool") {
                    currentFilterTypes = filterTypesBool
                    spinFilters.adapter = ArrayAdapter(applicationContext,
                        android.R.layout.simple_list_item_1, filterTypesBool)
                } else if(fieldType=="str") {
                    currentFilterTypes = filterTypesText
                    spinFilters.adapter = ArrayAdapter(applicationContext,
                        android.R.layout.simple_list_item_1, filterTypesText)
                } else if (fieldType=="int"){
                    currentFilterTypes = filterTypesInt
                    spinFilters.adapter = ArrayAdapter(applicationContext,
                        android.R.layout.simple_list_item_1, filterTypesInt)
                } else {
                    currentFilterTypes = arrayOf()
                    spinFilters.adapter = ArrayAdapter(applicationContext,
                        android.R.layout.simple_list_item_1, arrayOf<String>())
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

        spinFilters.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(!currentFilterTypes.isEmpty()) {
                    filterType = currentFilterTypes[position]
                } else {
                    filterType = ""
                }


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }



        val params = rvResults.layoutParams as ConstraintLayout.LayoutParams
        val layoutTitle = findViewById<ConstraintLayout>(R.id.layoutTitle)
        btn.setOnClickListener {
            if(isVisible) {
                parentLay.removeView(layout1)
                params.topToBottom = layoutTitle.id
                btn.text = "Open Search"
                postAdapter.dummy()
            } else {
                parentLay.addView(layout1)
                params.topToBottom = layout1.id
                btn.text = "Hide Search"
            }
            isVisible = !isVisible
            // Toast.makeText(this@SearchPosts,"$dataType and $field and $filterType", Toast.LENGTH_SHORT).show()
        }

        val addBtn = findViewById<Button>(R.id.btnAdd)
        addBtn.setOnClickListener{
            if(dataType=="" || field=="" || filterType=="") {
                 Toast.makeText(this, "Not a valid filter. Try again.", Toast.LENGTH_SHORT).show()
            } else {
                val value = searchBar.text.toString()
                val filter= Filter(dataType,field,filterType,value)
                filterAdapter.addFilter(filter)
            }

        }

        val resetBtn = findViewById<Button>(R.id.btnReset)
        resetBtn.setOnClickListener{
            filterAdapter.deleteAll()
            postAdapter.deleteAll()
            for (i in 0 until posts.length()) {
                val post = posts.getJSONObject(i)
                getPost(post, res)
            }

        }
        val searchBtn = findViewById<FloatingActionButton>(R.id.btnSearch)
        searchBtn.bringToFront()
        searchBtn.setOnClickListener{
            if(filterAdapter.itemCount==0) {
                Toast.makeText(this, "There are no filters.", Toast.LENGTH_SHORT).show()
            } else {
                val param:JSONObject = filterAdapter.getFilters()
                param.put("communityId", resJson["id"].toString())
                // Toast.makeText(this,param.toString().replace('\\', ' '), Toast.LENGTH_SHORT).show()
                sendRequest(param, res)
            }

        }

    }

    private fun getPost(post: JSONObject, res: String){
        val url = Data().getUrl("viewpost")

        val params: MutableMap<String, String> = HashMap()
        params["postId"] = post["id"].toString()

        var error: JSONObject? = null

        val stringRequest: StringRequest = object : StringRequest( Method.POST, url,
            Response.Listener { response ->
                try {
                    val jsonObject = JSONObject(response)
                    error = jsonObject
                    helper(post, res)
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

    fun helper(elm: JSONObject, res: String) {
        val commJson = JSONObject(elm.toString())
        val title = commJson["postTitle"]
        val id = commJson["id"]
        val date = JSONObject(commJson["creationTime"].toString())["_isoformat"].toString().substring(0,10)
        val by = JSONObject(commJson["postedBy"].toString())["username"]
        val post = Post(title as String, by.toString(), id.toString(), date, res)
        postAdapter.addPost(post)
    }

    private fun types(list: JSONArray):MutableList<String>{
        val liste : MutableList<String> = mutableListOf()
        for (i in 0 until list.length()) {
            val currType = list.getJSONObject(i)
            liste.add(currType["name"] as String)

        }
        return liste
    }

    private fun fields(list: JSONArray, name:String):MutableList<String>{
        var liste : MutableList<String> = mutableListOf()
        for (i in 0 until list.length()) {
            val currType:JSONObject = list.getJSONObject(i)
            if(currType["name"] as String == name) {
                val fieldJson:JSONObject = currType["fields"] as JSONObject
                val iter = fieldJson.keys()
                while (iter.hasNext()) {
                    val key = iter.next()
                    val value = fieldJson[key]
                    if(value=="str" || value=="int" || value=="bool" ) {
                        liste.add("$key-$value")
                    }
                }
            }


        }
        return liste
    }

    private fun sendRequest(jsonObj: JSONObject, res: String) {
        val url = Data().getUrl("searchpost")
        val params: MutableMap<String, String> = HashMap()
        val iter = jsonObj.keys()
        while(iter.hasNext()) {
            val key = iter.next()
            if(key=="filters") {
                params[key] = jsonObj[key].toString().replace('\\', ' ')
                // Toast.makeText(this, jsonObj[key].toString().replace('\\', ' '), Toast.LENGTH_SHORT).show()
            } else {
                params[key] = jsonObj[key].toString()
            }

        }

        // Toast.makeText(this, params.toString(), Toast.LENGTH_SHORT).show()

        var error: JSONObject? = null

        val stringRequest: StringRequest = object : StringRequest( Method.POST, url,
            Response.Listener { response ->
                try {
                    val jsonObject = JSONObject(response)
                    error = jsonObject

                    val posts = JSONArray(jsonObject["@return"].toString())
                    // Toast.makeText(this, "${jsonObject.toString()} isteği atmış", Toast.LENGTH_SHORT).show()
                    postAdapter.deleteAll()
                    for (i in 0 until posts.length()) {
                        val post = posts.getJSONObject(i)
                        getPost(post, res)
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

}