package com.example.facespace

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.data_type_field_item.view.*
import kotlinx.android.synthetic.main.variable_item.view.*
import org.json.JSONObject

class DataTypeFieldAdapter(
    private val dataTypeFields: MutableList<DataTypeField>

): RecyclerView.Adapter<DataTypeFieldAdapter.ViewHolder>() {

    private lateinit var mContext: Context

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        return ViewHolder(

            LayoutInflater.from(parent.context).inflate(
                R.layout.data_type_field_item,
                parent,
                false
            )

        )
    }

    fun deleteAll() {
        dataTypeFields.clear()
        notifyDataSetChanged()
    }

    fun addDataTypeField(dataTypeField: DataTypeField) {
        dataTypeFields.add(dataTypeField)
        notifyItemInserted(dataTypeFields.size-1)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currDataTypeField = dataTypeFields[position]
        holder.itemView.apply {
            textName.text = currDataTypeField.name
            textType.text = currDataTypeField.type
            textValue.text = currDataTypeField.value
        }
    }

    override fun getItemCount(): Int {
        return dataTypeFields.size
    }

    fun getDataTypeFields(): String {
        val retJson = JSONObject()
        for (dataTypeField in dataTypeFields) {
            retJson.put(dataTypeField.name, dataTypeField.value)
        }
        return retJson.toString()
    }
}