package com.example.facespace

import android.app.DatePickerDialog
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
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*
import kotlin.collections.HashMap

class CreatePostPage : AppCompatActivity() {
    private lateinit var dataTypeFieldAdapter: DataTypeFieldAdapter
    private lateinit var option: Spinner
    var result : String = ""
    var dataTypeFields: JSONObject? = null
    var lon: Double = 0.0
    var lat: Double = 0.0
    var loc = JSONObject()
    var date_field = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post_page)
        val commId: String? = intent.getStringExtra("commId")
        val typeName: String? = intent.getStringExtra("typeName")
        dataTypeFields = JSONObject(intent.getStringExtra("dataTypeFields")!!)
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
        val btnMap = findViewById<Button>(R.id.btnSelectCity2)
        val btnDate = findViewById<Button>(R.id.btnEnterDate2)
        btnCancel.bringToFront()
        btnCreate.bringToFront()
        val fieldValue = findViewById<EditText>(R.id.editValue)

        val options : MutableList<String> = mutableListOf()
        val options_map = mutableMapOf<String,String>()
        val options_vals = mutableMapOf<String,String>()

        try {
            val temp: Iterator<String> = dataTypeFields!!.keys()
            while (temp.hasNext()) {
                val key = temp.next()
                val value: Any = dataTypeFields!!.get(key)
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
            else if (fieldValue.visibility == View.INVISIBLE) {
                if (options_vals[result]!!.toString() == "location") {
                    val dataTypeField = DataTypeField(options_map[result]!!, options_vals[result]!!, loc.toString())
                    if (loc.length() == 0) {
                        Toast.makeText(this, "You must choose a location on the map!", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(this, "Location is: " + loc.toString(), Toast.LENGTH_SHORT).show()
                        dataTypeFieldAdapter.addDataTypeField(dataTypeField)
                        options_var.remove(result)
                        option.adapter =
                            ArrayAdapter(this, android.R.layout.simple_list_item_1, options_var)
                    }
                }
                else {
                    val dataTypeField = DataTypeField(options_map[result]!!, options_vals[result]!!, date_field)
                    if (date_field.isEmpty()) {
                        Toast.makeText(this, "You must choose a date on the calendar!", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        dataTypeFieldAdapter.addDataTypeField(dataTypeField)
                        options_var.remove(result)
                        option.adapter =
                            ArrayAdapter(this, android.R.layout.simple_list_item_1, options_var)
                    }
                }
            }
            else if (fieldValue.text.isEmpty()) {
                Toast.makeText(this, "Field value cannot be empty!", Toast.LENGTH_SHORT).show()
            }
            else {
                val out_val = fieldValue.text.toString()
                val dataTypeField = DataTypeField(options_map[result]!!, options_vals[result]!!, out_val)
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

        btnMap.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivityForResult(intent, 4)
        }

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month:Int = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        btnDate.setOnClickListener {
            val dpd = DatePickerDialog(this, { view, mYear, mMonth, mDay  ->
                val mMonth2 = mMonth + 1
                var mMonth3 = mMonth2.toString()
                var day2 = mDay.toString()
                if(mMonth2<10) {
                    mMonth3= "0$mMonth3"
                }
                if(mDay<10) {
                    day2= "0$day2"
                }
                btnDate.text = "$mYear-$mMonth3-$day2"
                date_field = "$mYear-$mMonth3-$day2"
            }, year, month, day)
            dpd.show()
        }

        option.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                result = options_var[position]
                if (options_vals[result] == "location") {
                    fieldValue.visibility = View.INVISIBLE
                    btnDate.visibility = View.INVISIBLE
                    btnMap.visibility = View.VISIBLE
                }
                else if (options_vals[result] == "datetime") {
                    fieldValue.visibility = View.INVISIBLE
                    btnDate.visibility = View.VISIBLE
                    btnMap.visibility = View.INVISIBLE
                }
                else {
                    fieldValue.visibility = View.VISIBLE
                    btnDate.visibility = View.INVISIBLE
                    btnMap.visibility = View.INVISIBLE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }
    private fun createPost(token: String, commId: String, title: String, typeName: String, values: String) {
        val params: MutableMap<String, String> = HashMap()
        val values_json = JSONObject(values)
        val values_edited = JSONObject(values)
        try {
            val temp: Iterator<String> = values_json.keys()
            while (temp.hasNext()) {
                val key = temp.next()
                val value: Any = values_json.get(key)
                //Toast.makeText(this, dataTypeFields!![key].toString(), Toast.LENGTH_SHORT).show()
                if(dataTypeFields!![key].toString() == "bool") {
                    values_edited.remove(key)
                    values_edited.put(key, (value as String).toBoolean())
                }
                if(dataTypeFields!![key].toString() == "location") {
                    values_edited.remove(key)
                    val loc_obj = JSONObject(value as String)
                    values_edited.put(key, loc_obj)
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val new_values = values_edited.toString()
        //Change with your post params
        params["@usertoken"] = token
        params["communityId"] = commId
        params["title"] = title
        params["datatypename"] = typeName
        params["datatypevalues"] = new_values
        //Toast.makeText(this, "new values: " + new_values, Toast.LENGTH_SHORT).show()
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
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 4 && resultCode == RESULT_OK) {
            val city: String = data?.getStringExtra("city").toString()
            lon = data?.getDoubleExtra("lon", 0.0)!!
            lat = data.getDoubleExtra("lat", 0.0)
            //Toast.makeText(this, city,Toast.LENGTH_SHORT).show()
            val mapbtn = findViewById<Button>(R.id.btnSelectCity2)
            mapbtn.text = city
            val lati = BigDecimal(lon).setScale(3, RoundingMode.HALF_EVEN).toDouble()
            val longi = BigDecimal(lat).setScale(3, RoundingMode.HALF_EVEN).toDouble()
            loc.put("locname", city)
            loc.put("longitude", lati)
            loc.put("latitude", longi)
        }
    }
}