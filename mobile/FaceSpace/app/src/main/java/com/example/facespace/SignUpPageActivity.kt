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




class SignUpPageActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_page)



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
                val mMonth2 = mMonth + 1
                dateTV.text = "$mYear-$mMonth2-$mDay"
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

            // Toast.makeText(this, username, Toast.LENGTH_SHORT).show()



            val params: MutableMap<String, String> = HashMap()

            val url = "http://3.144.184.237:8080/signup"


            // Post parameters
            // Form fields and values


            if (checkBox.isChecked && password!="" && email!="" && username!="" && name!="" &&
                surname!="" && pplink!="" && date!="Date" && city!="City") {


                params["username"] = username
                params["password"] = password
                params["email"] = email
                params["name"] = name
                params["surname"] = surname
                params["birthdate"] = date
                params["pplink"] = pplink
                params["city"] = city


                var error: JSONObject? = null
                val stringRequest: StringRequest = object : StringRequest(Method.POST, url,
                    Response.Listener { response ->

                        try {
                            //Parse your api responce here
                            val jsonObject = JSONObject(response)
                            error = jsonObject
                            val id = ((jsonObject["@return"]) as JSONObject)["id"]
                            Toast.makeText(this, "Signup Successful. Your id is : $id", Toast.LENGTH_LONG).show()
                            val intent = Intent(this, CommunitiesPageActivity::class.java)
                            startActivity(intent)


                        } catch (e: JSONException) {
                            e.printStackTrace()
                            Toast.makeText(this, (error?.get("@error")) as String, Toast.LENGTH_SHORT).show()
                        }
                    },
                    Response.ErrorListener { error ->
                        Toast.makeText(this, params.toString()+error.toString(), Toast.LENGTH_SHORT).show()
                    }) {
                    override fun getParams(): Map<String, String> {
                        return params
                    }
                }
                val requestQueue = Volley.newRequestQueue(this)
                requestQueue.add(stringRequest)

            } else {
                Toast.makeText(this, "Please fill all inputs and read&accept terms. $username" +
                        "$password $email $name $surname $date $city", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            var city:String = "ali"
            city = data?.getStringExtra("city").toString()
            Toast.makeText(this, city,Toast.LENGTH_SHORT).show()
            val cityTV = findViewById<TextView>(R.id.City)
            cityTV.text = city
        }
    }
}


