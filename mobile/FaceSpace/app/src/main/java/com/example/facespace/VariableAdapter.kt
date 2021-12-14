package com.example.facespace

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.variable_item.view.*
import org.json.JSONObject

class VariableAdapter(
    private val variables: MutableList<Variable>

): RecyclerView.Adapter<VariableAdapter.ViewHolder>() {


    private lateinit var mContext: Context

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        return ViewHolder(

            LayoutInflater.from(parent.context).inflate(
                R.layout.variable_item,
                parent,
                false
            )

        )
    }

    fun deleteAll() {
        variables.clear()
        notifyDataSetChanged()
    }

    fun addVariable(variable: Variable) {
        variables.add(variable)
        notifyItemInserted(variables.size-1)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currVariable = variables[position]
        holder.itemView.apply {
            name.text = currVariable.name
            type.text =  currVariable.type



        }
    }

    override fun getItemCount(): Int {
        return variables.size
    }

    fun getVariables(): String {
        val retJson = JSONObject()
        for (variable in variables) {
            retJson.put(variable.name, variable.type)
        }
        return retJson.toString()
    }
}