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
import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable

class CreateDataType : AppCompatActivity() {
    private lateinit var variableAdapter: VariableAdapter
    private lateinit var option: Spinner
    var result : String = "str"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_data_type)
        val id: String? = intent.getStringExtra("id")
        supportActionBar?.hide()

        variableAdapter = VariableAdapter(mutableListOf())
        val rvVariables = findViewById<RecyclerView>(R.id.rvVariableList)



        rvVariables.adapter = variableAdapter
        rvVariables.layoutManager = LinearLayoutManager(this)

        option = findViewById(R.id.spinner)

        val btnAdd = findViewById<Button>(R.id.okAdd)
        val btnReset = findViewById<Button>(R.id.reset)
        val btnCreate = findViewById<FloatingActionButton>(R.id.create)
        val btnCancel = findViewById<FloatingActionButton>(R.id.cancel)
        btnCancel.bringToFront()
        btnCreate.bringToFront()
        val nameEt = findViewById<EditText>(R.id.editName)
        val title = findViewById<EditText>(R.id.etDatatypeName)

        val options = arrayOf("str", "int", "bool", "location", "datetime")
        option.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,
            options)

        btnAdd.setOnClickListener{
            val variable = Variable(nameEt.text.toString(), result)
            variableAdapter.addVariable(variable)
        }

        btnReset.setOnClickListener{
            variableAdapter.deleteAll()
        }

        btnCreate.setOnClickListener{
            // Toast.makeText(this, variableAdapter.getVariables(), Toast.LENGTH_SHORT).show()
            createDataType(Data().getToken(), id.toString(),
                title.text.toString(), variableAdapter.getVariables())
        }

        btnCancel.setOnClickListener {
            //Toast.makeText(this, "$id", Toast.LENGTH_SHORT).show()
            if (id != null) {
                refresh(id)
            }
        }

        option.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                result = options[position]
                if (parent != null) {
                    // Toast.makeText(parent.context, "$result is seleceted", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }


    }
    private fun createDataType(token: String, commId: String, title: String, fields: String) {
        val params: MutableMap<String, String> = HashMap()

        //Change with your post params
        params["@usertoken"] = token
        params["communityId"] = commId
        params["datatypename"] = title
        params["datatypefields"] = fields


        val url = Data().getUrl("createdatatype")


        // Post parameters
        // Form fields and values


        var error: JSONObject? = null

        val stringRequest: StringRequest = object : StringRequest( Method.POST, url,
            Response.Listener { response ->
                try {
                    //Parse your api responce here
                    val jsonObject = JSONObject(response)
                    error = jsonObject
                    val success = jsonObject["@success"]
                    Toast.makeText(this, "Creation of datatype is $success", Toast.LENGTH_SHORT).show()
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