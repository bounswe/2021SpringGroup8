package com.example.facespace


import android.R.attr
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.HashMap
import android.R.attr.data

import android.app.Activity
import com.example.facespaceextenstion.Data


class SignUpPageActivity : AppCompatActivity() {

    var lon: Double = 0.0
    var lat: Double = 0.0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_page)
        supportActionBar?.hide()


        val btnGoSignIn = findViewById<Button>(R.id.btnGoSigninPage)
        val btnSignUp = findViewById<Button>(R.id.btnSignUp)
        val btnEnterDate = findViewById<Button>(R.id.btnEnterDate)


        val emailView = findViewById<EditText>(R.id.inputEmail)
        val usernameView = findViewById<EditText>(R.id.inputUsername)
        val passView = findViewById<EditText>(R.id.inputPassword)
        val nameView = findViewById<EditText>(R.id.inputName)
        val surnameView = findViewById<EditText>(R.id.inputSurname)
        val ppLinkView = findViewById<EditText>(R.id.inputPPLink)


        val checkBox = findViewById<CheckBox>(R.id.checkbox)


        val dateTV = findViewById<TextView>(R.id.Date)
        val cityTV = findViewById<TextView>(R.id.City)
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        var month:Int = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        btnEnterDate.setOnClickListener {
            val dpd = DatePickerDialog(this, { view, mYear, mMonth, mDay  ->
                var mMonth2 = mMonth + 1
                var mMonth3 = mMonth2.toString()
                var day2 = mDay.toString()
                if(mMonth2<10) {
                    mMonth3= "0$mMonth3"
                }
                if(mDay<10) {
                    day2= "0$day2"
                }
                dateTV.text = "$mYear-$mMonth3-$day2"
            }, year, month, day)
            dpd.show()
        }

        btnGoSignIn.setOnClickListener {
            val intent = Intent(this, LoginPageActivity::class.java)
            startActivity(intent)
        }

        val btnMaps = findViewById<Button>(R.id.btnSelectCity)


        btnMaps.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivityForResult(intent, 1)
        }


        btnSignUp.setOnClickListener {


            val password = passView.text.toString()
            val email = emailView.text.toString()
            val username = usernameView.text.toString()
            val name = nameView.text.toString()
            val surname = surnameView.text.toString()
            val pplink = ppLinkView.text.toString()
            val date = dateTV.text.toString()
            val city = cityTV.text.toString()

            val params: MutableMap<String, String> = HashMap()

            val url = Data().getUrl("signup")


            if (checkBox.isChecked && password!="" && email!="" && username!="" && name!="" &&
                surname!="" && pplink!="" && date!="Date" && city!="City") {


                params["username"] = username
                params["password"] = password
                params["email"] = email
                params["name"] = name
                params["surname"] = surname
                params["birthdate"] = date
                params["pplink"] = pplink
                var loc = JSONObject()
                loc.put("locname", city)
                loc.put("longitude", lon)
                loc.put("latitude", lat)


                params["loc"] = loc.toString()


                var error: JSONObject? = null
                val stringRequest: StringRequest = object : StringRequest(Method.POST, url,
                    Response.Listener { response ->

                        try {
                            //Parse your api responce here
                            val jsonObject = JSONObject(response)
                            error = jsonObject
                            val returns = JSONObject(jsonObject["@return"].toString())
                            val dob = (JSONObject(returns["birthdate"].toString()))["_isoformat"]
                            Data().setUsername(username)
                            Data().setToken((jsonObject["@usertoken"]).toString())
                            Data().setAll(returns["username"].toString(), returns["email"].toString(),
                                returns["name"].toString(), returns["surname"].toString(),
                                dob.toString().substring(0,10), loc["locname"].toString(), returns["pplink"].toString(),
                                loc["longitude"] as Double, loc["latitude"] as Double, returns["createdCommunities"].toString()
                            )
                            Toast.makeText(this, "Signup successful.", Toast.LENGTH_LONG).show()
                            val intent = Intent(this, HomePageActivity::class.java)
                            intent.putExtra("username", username)
                            startActivity(intent)


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

            } else {
                Toast.makeText(this, "Please fill all inputs and read & accept terms.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val city: String = data?.getStringExtra("city").toString()
            lon = data?.getDoubleExtra("lon", 0.0)!!
            lat = data.getDoubleExtra("lat", 0.0)
            // Toast.makeText(this, city,Toast.LENGTH_SHORT).show()
            val cityTV = findViewById<TextView>(R.id.City)
            cityTV.text = city
        }
    }
}


