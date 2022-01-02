package com.example.facespace

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.SystemClock.sleep
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.facespaceextenstion.Data
import kotlinx.android.synthetic.main.community_item.view.*
import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable

class CommunityAdapter (

    private val communities: MutableList<Community>


    ) : RecyclerView.Adapter<CommunityAdapter.CommunityViewHolder>() {

    private lateinit var mContext:Context

    class CommunityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityViewHolder {
        mContext = parent.context
        return CommunityViewHolder(

            LayoutInflater.from(parent.context).inflate(
                R.layout.community_item,
                parent,
                false
            )

        )
    }

    fun deleteAll() {
        communities.clear()
        notifyDataSetChanged()
    }

    fun addComm(comm: Community) {
        communities.add(comm)
        notifyItemInserted(communities.size-1)
    }


    /*
    private fun changeText(joinBox: CheckBox, isChecked: Boolean) {
        if(isChecked) {
            joinBox.text = "Joined"
            joinBox.setTextColor(Color.rgb(0,255,0))
        } else {
            joinBox.text = "Join"
            joinBox.setTextColor(Color.rgb(255,0,0))
        }
    }

     */




    override fun onBindViewHolder(holder: CommunityViewHolder, position: Int) {
        val currComm = communities[position]
        holder.itemView.apply {
            Title.text = currComm.title
            Desc.text = currComm.desc
            By.text = "by " + currComm.by
            since.text = currComm.since
            btnOpenComm.setOnClickListener {
                val temp: String = currComm.id
                Data().setCurrentCommunityId(temp)
                setInfo(temp)
            }
        }
    }

    fun deleteComm(comm: Community) {
        val index = communities.indexOf(comm)
        communities.remove(comm)
        notifyItemRemoved(index)
    }

    override fun getItemCount(): Int {
        return communities.size
    }

    private fun setInfo(id:String) {
        val url = Data().getUrl("getcommunity")

        var error: JSONObject? = null

        val params: MutableMap<String, String> = HashMap()
        params["communityId"] = id

        val stringRequest: StringRequest = object : StringRequest( Method.POST, url,
            Response.Listener { response ->
                try {
                    //Parse your api responce here
                    val jsonObject = JSONObject(response)
                    error = jsonObject
                    val results: JSONObject = jsonObject["@return"] as JSONObject
                    helper(results)
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
        val requestQueue = Volley.newRequestQueue(mContext)
        requestQueue.add(stringRequest)

    }
    private fun helper(res: JSONObject) {

        val infos:MutableMap<String,String> = HashMap()

        infos["id"] = res["id"].toString()
        infos["title"]  = res["communityTitle"].toString()
        infos["desc"]  = res["description"].toString()
        infos["date"]  = (res["creationTime"] as JSONObject).toString().substring(0,10)
        infos["by"]  = (res["createdBy"] as JSONObject).toString()
        infos["subscribers"] = res["subscribers"].toString()

        //Toast.makeText(mContext,"helalsfsf", Toast.LENGTH_SHORT).show()
        val intent: Intent = Intent(mContext, CommunityPageActivity::class.java)
        intent.putExtra("result", res.toString())
        mContext.startActivity(intent)
    }
}