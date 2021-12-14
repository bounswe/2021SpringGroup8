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
import kotlinx.android.synthetic.main.post_item.view.*
import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable

class PostAdapter (

    private val posts: MutableList<Post>

) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private lateinit var mContext: Context

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        mContext = parent.context
        return PostViewHolder(

            LayoutInflater.from(parent.context).inflate(
                R.layout.post_item,
                parent,
                false
            )

        )
    }

    fun deleteAll() {
        posts.clear()
        notifyDataSetChanged()
    }

    fun addPost(pos: Post) {
        posts.add(pos)
        notifyItemInserted(posts.size-1)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val currPost = posts[position]
        holder.itemView.apply {
            TitlePost.text = currPost.title
            DescPost.text = currPost.desc
            ByPost.text = "by " + currPost.by
            DateField.text = currPost.date

            btnOpenPost.setOnClickListener {
                val temp: String = currPost.id
                Data().setCurrentPostId(temp)
                setInfo(temp)
                //Thread.sleep(700)
                // Toast.makeText(context,"$temp this is under implementation still.",Toast.LENGTH_SHORT).show()
                //context.startActivity(Intent(context, CommunityPageActivity::class.java))
            }


        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    private fun setInfo(id:String) {

        val url = Data().getUrl("viewpost")

        var error: JSONObject? = null

        val params: MutableMap<String, String> = HashMap()
        params["postId"] = id

        val stringRequest: StringRequest = object : StringRequest( Method.POST, url,
            Response.Listener { response ->
                try {
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
        //Toast.makeText(mContext,"helalbeee " + res.toString(), Toast.LENGTH_SHORT).show()
        val intent: Intent = Intent(mContext, InsidePost::class.java)
        intent.putExtra("result", res.toString())
        mContext.startActivity(intent)
    }

}
