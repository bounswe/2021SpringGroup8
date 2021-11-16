package com.example.facespace

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class SignUpPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_page)


        val btnGoSignIn = findViewById<Button>(R.id.btnGoSigninPage)
        val btnSignUp = findViewById<Button>(R.id.btnSignUp)


        val emailView = findViewById<EditText>(R.id.inputEmail)
        val usernameView = findViewById<EditText>(R.id.inputUsername)
        val passView = findViewById<EditText>(R.id.inputPassword)

        val checkBox = findViewById<CheckBox>(R.id.checkbox)


        btnGoSignIn.setOnClickListener {
            val intent = Intent(this, LoginPageActivity::class.java)
            startActivity(intent)
        }


        btnSignUp.setOnClickListener {


            val password = passView.text.toString()
            val email = emailView.text.toString()
            val username = usernameView.text.toString()


            val params: MutableMap<String, String> = HashMap()

            params["username"] = username
            params["password"] = password
            params["email"] = email


            val url = "http://52.22.100.255:8080/signup"


            // Post parameters
            // Form fields and values


            if (checkBox.isChecked) {
                var error: JSONObject? = null
                val stringRequest: StringRequest = object : StringRequest(Method.POST, url,
                    Response.Listener { response ->
                        emailView.text.clear()
                        usernameView.text.clear()
                        passView.text.clear()
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
                        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
                    }) {
                    override fun getParams(): Map<String, String> {
                        return params
                    }
                }
                val requestQueue = Volley.newRequestQueue(this)
                requestQueue.add(stringRequest)

            } else {
                Toast.makeText(this, "Please read and accept terms.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}