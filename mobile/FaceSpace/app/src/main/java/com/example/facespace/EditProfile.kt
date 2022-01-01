package com.example.facespace

import android.app.DatePickerDialog
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
import kotlinx.android.synthetic.main.fragment_create_community.*
import kotlinx.android.synthetic.main.fragment_create_community.view.*
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import kotlinx.android.synthetic.main.fragment_edit_profile.view.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.HashMap

// TODO: Rename parameter arguments, choose names that match


/**
 * A simple [Fragment] subclass.
 * Use the [EditProfile.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditProfile : DialogFragment() {
    val loc: Location = Data().getLoc()
    var lon: Double = loc.longitude
    var lat: Double = loc.latitude
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView: View = inflater.inflate(R.layout.fragment_edit_profile, container, false)




        val infos: MutableMap<String, String> = Data().getAll()
        rootView.newName.setText(infos["name"])
        rootView.newSurname.setText(infos["surname"])
        rootView.newEmail.setText(infos["email"])
        rootView.newCity.text = infos["city"]
        rootView.newDate.text = infos["birthdate"]
        rootView.newLink.setText(infos["pplink"])

        rootView.update.bringToFront()
        rootView.cancel.bringToFront()

        rootView.cancel.setOnClickListener{
            dismiss()
        }


        rootView.update.setOnClickListener{
            val token = Data().getToken()
            val name = rootView.newName.text.toString()
            val surname = rootView.newSurname.text.toString()
            val email = rootView.newEmail.text.toString()
            val city = rootView.newCity.text.toString()
            val date = rootView.newDate.text.toString()
            val pplink = rootView.newLink.text.toString()

            sendRequest(token, name, surname, email, city, lon, lat, date, pplink)

        }



        rootView.selectCity.setOnClickListener {
            val intent = Intent(activity, MapsActivity::class.java)
            intent.putExtra("lon", lon)
            intent.putExtra("lat", lat)
            startActivityForResult(intent, 3)
        }
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        var month:Int = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        rootView.selectDate.setOnClickListener {
            val dpd = this.context?.let { it1 ->
                DatePickerDialog(it1, { view, mYear, mMonth, mDay  ->
                    var mMonth2 = mMonth + 1
                    var mMonth3 = mMonth2.toString()
                    var day2 = mDay.toString()
                    if(mMonth2<10) {
                        mMonth3= "0$mMonth3"
                    }
                    if(mDay<10) {
                        day2= "0$day2"
                    }
                    newDate.text = "$mYear-$mMonth3-$day2"
                }, year, month, day)
            }
            dpd?.show()
        }


        return rootView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 3 && resultCode == AppCompatActivity.RESULT_OK) {
            val city: String = data?.getStringExtra("city").toString()
            lon = data?.getDoubleExtra("lon", 0.0)!!
            lat = data.getDoubleExtra("lat", 0.0)
            Toast.makeText(context, city,Toast.LENGTH_SHORT).show()
            newCity.text = city
        }
    }

    private fun sendRequest(token:String, name:String, surname:String, email:String,
                city:String, lon:Double, lat:Double, date:String, link:String) {
        val url = Data().getUrl("updateprofile")


        val params: MutableMap<String, String> = HashMap()

        //Change with your post params
        params["@usertoken"] = token
        params["name"] = name
        params["surname"] = surname
        params["email"] = email
        params["birthdate"] = date
        params["pplink"] = link
        var loc = JSONObject()
        loc.put("locname", city)
        loc.put("longitude", lon)
        loc.put("latitude", lat)


        params["loc"] = loc.toString()




        var error: JSONObject? = null


        val stringRequest: StringRequest = object : StringRequest( Method.POST, url,
            Response.Listener { response ->
                try {
                    //Parse your api responce here
                    val jsonObject = JSONObject(response)
                    error = jsonObject
                    Data().update(email,name, surname, date, city, link, lon, lat)
                    Toast.makeText(context, "Profile is updated successfully", Toast.LENGTH_SHORT).show()
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(context, (error?.get("@error")) as String, Toast.LENGTH_SHORT).show()
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