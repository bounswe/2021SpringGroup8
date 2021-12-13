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
import com.example.facespaceextenstion.Data
import org.json.JSONException
import org.json.JSONObject



class LoginPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_login_page)


        /*

        // To take parameters from another view

        val extras = intent.extras
        var value = extras?.get("key")
        val faceText = findViewById<TextView>(R.id.face)
        faceText.text = (value?:"FaceSpace") as String?
         */




        Data().resetAll()

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


            val url = Data().getUrl("login")


            // Post parameters
            // Form fields and values


            var error: JSONObject? = null

            val stringRequest: StringRequest = object : StringRequest( Method.POST, url,
                Response.Listener { response ->
                    userEdit.text.clear()
                    passEdit.text.clear()
                    try {
                        //Parse your api responce here
                        val jsonObject = JSONObject(response)
                        error = jsonObject
                        val token = jsonObject["@usertoken"]
                        val returns = JSONObject(jsonObject["@return"].toString())
                        val dob = (JSONObject(returns["birthdate"].toString()))["_isoformat"]
                        val loc = JSONObject(returns["loc"].toString())
                        Toast.makeText(this, "$url login Successful. Your Token is : $token", Toast.LENGTH_SHORT).show()
                        Data().setToken(token as String)
                        Data().setAll(returns["username"].toString(), returns["email"].toString(),
                            returns["name"].toString(), returns["surname"].toString(),
                            dob.toString().substring(0,10), loc["locname"].toString(), returns["pplink"].toString(),
                            loc["longitude"] as Double, loc["latitude"] as Double, returns["createdCommunities"].toString()
                        )
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







            // Toast.makeText(this@MainActivity, text, Toast.LENGTH_SHORT).show()

        }

        btnGoSignUp.setOnClickListener {
            val intent = Intent(this, SignUpPageActivity::class.java)
            startActivity(intent)


        }

        btnForgot.setOnClickListener {

            val toast = Toast.makeText(this, "Sorry to hear that. This feature is still under implementation", Toast.LENGTH_SHORT)
            toast.show()


        }
    }
}