package com.example.facespace

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.facespaceextenstion.Data
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_create_community.*
import kotlinx.android.synthetic.main.fragment_create_community.view.*
import org.json.JSONException
import org.json.JSONObject


/**
 * A simple [Fragment] subclass.
 * Use the [CreateCommunity.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateCommunity : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView: View = inflater.inflate(R.layout.fragment_create_community, container, false)
        rootView.cancelDialog.setOnClickListener{
            dismiss()
        }

        rootView.addOk.setOnClickListener{
            val title = rootView.Title.text.toString()
            val desc = rootView.Desc.text.toString()

            sendRequest(title, desc)

        }
        return rootView
    }

    private fun sendRequest(tit:String, des:String) {
        val url = Data().getUrl("createcommunity")


        val params: MutableMap<String, String> = HashMap()

        //Change with your post params
        params["communityTitle"] = tit
        params["description"] = des
        params["@usertoken"] = Data().getToken()


        var error: JSONObject? = null


        val stringRequest: StringRequest = object : StringRequest( Method.POST, url,
            Response.Listener { response ->
                try {
                    //Parse your api responce here
                    val jsonObject = JSONObject(response)
                    error = jsonObject
                    val id = JSONObject((jsonObject["@return"]).toString())["id"]
                    Toast.makeText(context, "Community with id: $id crated succesfully", Toast.LENGTH_SHORT).show()
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(context, "catch oldu"+(error?.get("@error")) as String, Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(context, "listener error $error", Toast.LENGTH_SHORT).show()
            }) {
            override fun getParams(): Map<String, String> {
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(stringRequest)
    }
}