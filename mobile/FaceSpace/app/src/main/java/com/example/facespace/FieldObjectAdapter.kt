package com.example.facespace

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.icu.text.CaseMap
import android.os.Bundle
import android.os.SystemClock.sleep
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.facespaceextenstion.Data
import kotlinx.android.synthetic.main.field_object_item.view.*
import kotlinx.android.synthetic.main.post_item.view.*
import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable
import android.app.Activity




class FieldObjectAdapter (

    private val fieldObjects: MutableList<FieldObject>

) : RecyclerView.Adapter<FieldObjectAdapter.FieldObjectViewHolder>() {

    private lateinit var mContext: Context

    class FieldObjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FieldObjectAdapter.FieldObjectViewHolder {
        mContext = parent.context
        return FieldObjectAdapter.FieldObjectViewHolder(

            LayoutInflater.from(parent.context).inflate(
                R.layout.field_object_item,
                parent,
                false
            )

        )
    }

    fun deleteAll() {
        fieldObjects.clear()
        notifyDataSetChanged()
    }

    fun addFieldObject(obj: FieldObject) {
        fieldObjects.add(obj)
        notifyItemInserted(fieldObjects.size-1)
    }

    fun deleteFieldObject(obj: FieldObject) {
        val index = fieldObjects.indexOf(obj)
        fieldObjects.remove(obj)
        notifyItemRemoved(index)
    }

    override fun onBindViewHolder(holder: FieldObjectAdapter.FieldObjectViewHolder, position: Int) {
        val currFieldObject = fieldObjects[position]
        holder.itemView.apply {
            FieldName.text = currFieldObject.name + ":"

            if (currFieldObject.type == "location") {
                valLoc.visibility = View.VISIBLE
                val locJson = JSONObject(currFieldObject.value)
                valLoc.text = locJson["locname"].toString()
            }
            else {
                valText.visibility = View.VISIBLE
                if (currFieldObject.type == "datetime") {
                    val dateJson = JSONObject(currFieldObject.value)
                    valText.text = dateJson["_isoformat"].toString().substring(0,10)
                }
                else {
                    valText.text = currFieldObject.value
                }
            }

            valLoc.setOnClickListener {
                val locJson = JSONObject(currFieldObject.value)
                val lon : Double = locJson["longitude"].toString().toDouble()
                val lat : Double = locJson["latitude"].toString().toDouble()
                //Toast.makeText(mContext, "lon: $lon and lat: $lat", Toast.LENGTH_LONG).show()
                val intent = Intent(mContext, MapsActivity::class.java)
                intent.putExtra("lon", lon)
                intent.putExtra("lat", lat)
                mContext.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return fieldObjects.size
    }
}
