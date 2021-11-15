package com.example.signin_signup

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.facespace.R
import kotlinx.android.synthetic.main.community_item.view.*

class CommunityAdapter (

    private val communities: MutableList<Community>

    ) : RecyclerView.Adapter<CommunityAdapter.CommunityViewHolder>() {

    class CommunityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityViewHolder {
        return CommunityViewHolder(

            LayoutInflater.from(parent.context).inflate(
                R.layout.community_item,
                parent,
                false
            )

        )
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
            button.setOnClickListener {
                val temp: String = currComm.title
                Toast.makeText(context,"$temp this is under implementation still.",Toast.LENGTH_SHORT).show()
            }


        }
    }

    override fun getItemCount(): Int {
        return communities.size
    }
}