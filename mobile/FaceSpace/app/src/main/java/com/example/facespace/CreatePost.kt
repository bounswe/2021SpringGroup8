package com.example.facespace

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.facespaceextenstion.Data
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_create_post.view.*
import kotlinx.android.synthetic.main.fragment_create_community.*
import kotlinx.android.synthetic.main.fragment_create_community.view.*
import org.json.JSONException
import org.json.JSONObject

class CreatePost : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView: View = inflater.inflate(R.layout.activity_create_post, container, false)
        rootView.CancelButton.setOnClickListener{
            dismiss()
        }

        rootView.CreateButton.setOnClickListener{
            val title = rootView.TitleContent.text.toString()
            val desc = rootView.DescContent.text.toString()

            sendRequest(title, desc)

        }
        return rootView
    }

    private fun sendRequest(tit:String, des:String) {
        val url = Data().getUrl("submitpost")

        val params: MutableMap<String, String> = HashMap()
        var dtvalues = JSONObject()

        //Change with your post params
        params["title"] = tit
        params["datatypename"] = des
        params["@usertoken"] = Data().getToken()
        params["datatypevalues"] = dtvalues.toString()
        params["communityId"] = Data().getCurrentCommunityId()

        var error: JSONObject? = null
        val stringRequest: StringRequest = object : StringRequest( Method.POST, url,
            Response.Listener { response ->
                try {
                    val jsonObject = JSONObject(response)
                    error = jsonObject
                    val id = JSONObject(jsonObject["@return"].toString())["id"]
                    Toast.makeText(context, "Post with id: $id crated succesfully", Toast.LENGTH_SHORT).show()
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(context, "catch oldu mu"+(error?.get("@error")) as String, Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(context, "listener error $error", Toast.LENGTH_SHORT).show()
            }) {
            override fun getParams(): MutableMap<String, String> {
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(stringRequest)
    }
}