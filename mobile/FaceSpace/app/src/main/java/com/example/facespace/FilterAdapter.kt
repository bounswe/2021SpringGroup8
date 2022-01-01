package com.example.facespace

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.filter_item.view.*
import kotlinx.android.synthetic.main.variable_item.view.*
import org.json.JSONObject

class FilterAdapter(
    private val filters: MutableList<Filter>

): RecyclerView.Adapter<FilterAdapter.ViewHolder>() {


    private lateinit var mContext: Context

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        return ViewHolder(

            LayoutInflater.from(parent.context).inflate(
                R.layout.filter_item,
                parent,
                false
            )

        )
    }

    fun deleteAll() {
        filters.clear()
        notifyDataSetChanged()
    }

    fun addFilter(filter: Filter) {
        if (itemCount >0) {
            val lastFilter:Filter = filters[itemCount-1]
            if (lastFilter.datatype == filter.datatype) {
                filters.add(filter)
                notifyItemInserted(filters.size-1)
            } else {
                Toast.makeText(mContext, "Filters can be made only in the same data types", Toast.LENGTH_SHORT).show()
            }
        } else {
            filters.add(filter)
            notifyItemInserted(filters.size-1)
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currFilter = filters[position]
        holder.itemView.apply {
            dataTypeName.text = currFilter.datatype
            fieldName.text = currFilter.field
            filterType.text = currFilter.filtertype
            value.text = currFilter.value



        }
    }

    override fun getItemCount(): Int {
        return filters.size
    }
    /*
    fun getFilters(): String {
        val retJson = JSONObject()
        for (filter in filters) {
            retJson.put(filter.name, variable.type)
        }
        return retJson.toString()
    }

     */
}