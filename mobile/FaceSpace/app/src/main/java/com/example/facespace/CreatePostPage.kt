package com.example.facespace

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.facespaceextenstion.Data
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_create_post_page.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable

class CreatePostPage : AppCompatActivity() {
    private lateinit var dataTypeFieldAdapter: DataTypeFieldAdapter
    private lateinit var option: Spinner
    var result : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post_page)
        val commId: String? = intent.getStringExtra("commId")
        val typeName: String? = intent.getStringExtra("typeName")
        val dataTypeFields = JSONObject(intent.getStringExtra("dataTypeFields")!!)
        supportActionBar?.hide()

        dataTypeFieldAdapter = DataTypeFieldAdapter(mutableListOf())
        val rvDataTypeFields = findViewById<RecyclerView>(R.id.rvDataTypeFields)

        rvDataTypeFields.adapter = dataTypeFieldAdapter
        rvDataTypeFields.layoutManager = LinearLayoutManager(this)

        option = findViewById(R.id.spinner2)
        val title = findViewById<EditText>(R.id.postTitle)
        val btnAdd = findViewById<Button>(R.id.okAdd2)
        val btnReset = findViewById<Button>(R.id.reset2)
        val btnCreate = findViewById<FloatingActionButton>(R.id.create2)
        val btnCancel = findViewById<FloatingActionButton>(R.id.cancel2)
        btnCancel.bringToFront()
        btnCreate.bringToFront()
        val fieldValue = findViewById<EditText>(R.id.editValue)

        val options : MutableList<String> = mutableListOf()
        val options_map = mutableMapOf<String,String>()
        val options_vals = mutableMapOf<String,String>()

        try {
            val temp: Iterator<String> = dataTypeFields.keys()
            while (temp.hasNext()) {
                val key = temp.next()
                val value: Any = dataTypeFields.get(key)
                val temp = "$key ($value)"
                options.add(temp)
                options_map[temp] = key
                options_vals[temp] = value as String
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        var options_var = options.toMutableList()

        result = options_var[0]

        option.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, options_var)

        btnAdd.setOnClickListener{
            if (options_var.isEmpty()) {
                Toast.makeText(this, "You already entered the values for all fields! If you want to change, you must click Reset.", Toast.LENGTH_SHORT).show()
            }
            else if (fieldValue.text.isEmpty()) {
                Toast.makeText(this, "Field value cannot be empty!", Toast.LENGTH_SHORT).show()
            }
            else {
                val dataTypeField = DataTypeField(options_map[result]!!, options_vals[result]!!, fieldValue.text.toString())
                dataTypeFieldAdapter.addDataTypeField(dataTypeField)
                options_var.remove(result)
                option.adapter =
                    ArrayAdapter(this, android.R.layout.simple_list_item_1, options_var)
            }
        }

        btnReset.setOnClickListener{
            dataTypeFieldAdapter.deleteAll()
            options_var = options.toMutableList()
            option.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, options_var)
        }

        btnCreate.setOnClickListener{
            // Toast.makeText(this, variableAdapter.getVariables(), Toast.LENGTH_SHORT).show()
            if (options_var.isEmpty()) {
                createPost(
                    Data().getToken(),
                    commId.toString(),
                    title.text.toString(),
                    typeName.toString(),
                    dataTypeFieldAdapter.getDataTypeFields()
                )
            }
            else {
                Toast.makeText(this, "You have to fill all fields before creating a post!", Toast.LENGTH_SHORT).show()
            }
        }

        btnCancel.setOnClickListener {
            //Toast.makeText(this, "$id", Toast.LENGTH_SHORT).show()
            if (commId != null) {
                refresh(commId)
            }
        }

        option.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                result = options_var[position]
                if (parent != null) {
                    // Toast.makeText(parent.context, "$result is seleceted", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }


    }
    private fun createPost(token: String, commId: String, title: String, typeName: String, values: String) {
        val params: MutableMap<String, String> = HashMap()

        //Change with your post params
        params["@usertoken"] = token
        params["communityId"] = commId
        params["title"] = title
        params["datatypename"] = typeName
        params["datatypevalues"] = values

        val url = Data().getUrl("submitpost")

        var error: JSONObject? = null

        val stringRequest: StringRequest = object : StringRequest( Method.POST, url,
            Response.Listener { response ->
                try {

                    //Parse your api response here
                    val jsonObject = JSONObject(response)
                    error = jsonObject
                    val success = jsonObject["success"]
                    Toast.makeText(this, "Creation of post is $success", Toast.LENGTH_SHORT).show()
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

    private fun refresh(id:String) {

        val url = Data().getUrl("getcommunity")

        var error: JSONObject? = null

        val params: MutableMap<String, String> = HashMap()
        params["communityId"] = id

        val stringRequest: StringRequest = object : StringRequest( Method.POST, url,
            Response.Listener { response ->
                try {
                    //Parse your api response here
                    val jsonObject = JSONObject(response)
                    error = jsonObject
                    val results: JSONObject = jsonObject["@return"] as JSONObject
                    helper(results, id)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
            }) {
            override fun getParams(): Map<String, String> {
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)

    }
    private fun helper(res: JSONObject, newId: String) {
        val infos: MutableMap<String,String> = HashMap()

        val tempDate: JSONObject = res["creationTime"] as JSONObject
        val tempBy: JSONObject = res["createdBy"] as JSONObject

        infos["title"]  = res["communityTitle"].toString()
        infos["desc"]  = res["description"].toString()
        infos["date"]  = tempDate["_isoformat"].toString().substring(0,10)
        infos["by"]  = tempBy["username"].toString()
        infos["id"] = newId
        infos["subscribers"] = res["subscribers"].toString()

        val intent: Intent = Intent(this, CommunityPageActivity::class.java)
        intent.putExtra("keys", infos as Serializable)
        intent.putExtra("result", res.toString())
        startActivity(intent)

    }

}