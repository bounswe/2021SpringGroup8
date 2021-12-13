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
            /*
            joinBox.isChecked = currComm.isJoined
            changeText(joinBox, currComm.isJoined)


            joinBox.setOnCheckedChangeListener { joinbox, isJoined ->
                changeText(joinBox as CheckBox, isJoined)
                currComm.isJoined = !currComm.isJoined

            }
             */
            btnOpenComm.setOnClickListener {
                val temp: String = currComm.id
                Data().setCurrentCommunityId(temp)
                setInfo(temp)
                // Thread.sleep(700)
                // Toast.makeText(context,"$temp this is under implementation still.",Toast.LENGTH_SHORT).show()
<<<<<<< HEAD
                // context.startActivity(Intent(context, CommunityPageActivity::class.java))
=======

>>>>>>> 98e7128ea7b8e9fbb696d31b8e80e09e8a78e2f3
            }


        }
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

        val tempDate: JSONObject = res["creationTime"] as JSONObject
        val tempBy: JSONObject = res["createdBy"] as JSONObject

        infos["title"]  = res["communityTitle"].toString()
        infos["desc"]  = res["description"].toString()
        infos["date"]  = tempDate["_isoformat"].toString().substring(0,10)
        infos["by"]  = tempBy["username"].toString()

        Toast.makeText(mContext,"helalsfsf", Toast.LENGTH_SHORT).show()
        val intent: Intent = Intent(mContext, CommunityPageActivity::class.java)
        intent.putExtra("keys", infos as Serializable)
        intent.putExtra("result", res.toString())
        mContext.startActivity(intent)

    }

}