package com.example.hp.thekleaners.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import com.example.hp.thekleaners.pojoClass.ForCarService
import java.util.*

class CustomAdapter(internal var context: Context, private var profiles: ArrayList<ForCarService>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_layout, parent, false))


    }

    override fun getItemCount(): Int {
        return profiles.size
    }


    override fun onBindViewHolder(holder: CustomAdapter.ViewHolder, position: Int) {
        holder.name.text = profiles[position].carName
        holder.email.text = profiles[position].carNumber
        holder.amount.text = "Bill Amount : ₹" + profiles[position].carAmount.toString()
        holder.type.text = profiles[position].carType
        holder.date.text = "Service Date : " +profiles[position].carDate
        holder.singleAmt.text = "Bill Single Day Amount : ₹" + profiles[position].carSingleAmount.toString()

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name: TextView
        var email: TextView
        var amount: TextView
        var type: TextView
        var date: TextView
        var singleAmt: TextView


        init {

            name = itemView.findViewById<View>(R.id.post_title) as TextView
            email = itemView.findViewById<View>(R.id.post_desc) as TextView
            amount = itemView.findViewById<View>(R.id.post_amount) as TextView
            type = itemView.findViewById<View>(R.id.post_carType) as TextView
            date= itemView.findViewById<View>(R.id.post_date) as TextView
            singleAmt = itemView.findViewById<View>(R.id.post_singleAmt) as TextView
        }
    }
}
