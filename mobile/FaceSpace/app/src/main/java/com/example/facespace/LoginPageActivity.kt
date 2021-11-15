package com.example.facespace

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class LoginPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)


        /*

        // To take parameters from another view

        val extras = intent.extras
        var value = extras?.get("key")
        val faceText = findViewById<TextView>(R.id.face)
        faceText.text = (value?:"FaceSpace") as String?
         */






        val btnSignIn = findViewById<Button>(R.id.btnSignIn)
        val btnGoSignUp = findViewById<Button>(R.id.btnGoSignUpPage)
        val btnForgot =  findViewById<Button>(R.id.btnForgot)
        btnSignIn.setOnClickListener {
            val userEdit = findViewById<EditText>(R.id.inputUsername)
            val passEdit = findViewById<EditText>(R.id.inputPassword)
            val username = userEdit.text.toString()
            val password = passEdit.text.toString()


            val params: MutableMap<String, String> = HashMap()
            //Change with your post params
            params["username"] = username
            params["password"] = password


            val url = "http://10.0.2.2:8080/login"


            // Post parameters
            // Form fields and values



            val stringRequest: StringRequest = object : StringRequest( Method.POST, url,
                Response.Listener { response ->
                    userEdit.text.clear()
                    passEdit.text.clear()
                    try {
                        //Parse your api responce here
                        val jsonObject = JSONObject(response)
                        val token = jsonObject["@usertoken"]
                        Toast.makeText(this, "login Successful. Your Token is : $token", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, CommunitiesPageActivity::class.java)
                        startActivity(intent)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
                }) {
                override fun getParams(): Map<String, String> {
                    return params
                }
            }
            val requestQueue = Volley.newRequestQueue(this)
            requestQueue.add(stringRequest)







            // Toast.makeText(this@MainActivity, text, Toast.LENGTH_SHORT).show()

        }

        btnGoSignUp.setOnClickListener {
            val intent = Intent(this, SignUpPageActivity::class.java)
            startActivity(intent)


        }

        btnForgot.setOnClickListener {

            val toast = Toast.makeText(this, "Sorry to hear that. This feature is still under implementation", Toast.LENGTH_LONG)
            toast.show()


        }
    }
}