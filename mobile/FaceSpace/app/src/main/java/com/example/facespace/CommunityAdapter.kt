package com.example.facespace

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.community_item.view.*
import org.json.JSONException
import org.json.JSONObject

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


    private fun changeText(joinBox: CheckBox, isChecked: Boolean) {
        if(isChecked) {
            joinBox.text = "Joined"
            joinBox.setTextColor(Color.rgb(0,255,0))
        } else {
            joinBox.text = "Join"
            joinBox.setTextColor(Color.rgb(255,0,0))
        }
    }




    override fun onBindViewHolder(holder: CommunityViewHolder, position: Int) {
        val currComm = communities[position]
        holder.itemView.apply {
            Title.text = currComm.title
            Desc.text = currComm.desc
            By.text = currComm.by
            since.text = currComm.since
            joinBox.isChecked = currComm.isJoined
            changeText(joinBox, currComm.isJoined)

            joinBox.setOnCheckedChangeListener { joinbox, isJoined ->
                changeText(joinBox as CheckBox, isJoined)
                currComm.isJoined = !currComm.isJoined

            }
            btnOpenComm.setOnClickListener {
                val temp: String = currComm.id
                Data().setCurrentComunityId(temp)
                setInfo(temp)
                Thread.sleep(500)
                // Toast.makeText(context,"$temp this is under implementation still.",Toast.LENGTH_SHORT).show()
                context.startActivity(Intent(context, CommunityPageActivity::class.java))
            }


        }
    }

    override fun getItemCount(): Int {
        return communities.size
    }

    private fun setInfo(id:String) {

        val url = "http://3.144.184.237:8080/getcommunity"

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

        val tempDate: JSONObject = res["creationTime"] as JSONObject
        val tempBy: JSONObject = res["createdBy"] as JSONObject

        val title = res["communityTitle"].toString()
        val desc = res["description"].toString()
        val date = tempDate["_isoformat"].toString()
        val by = tempBy["username"].toString()

        Toast.makeText(mContext,"helalsfsf", Toast.LENGTH_SHORT).show()

        Data().setCommInfo(title ,desc, by, date.substring(0,10))

    }

}