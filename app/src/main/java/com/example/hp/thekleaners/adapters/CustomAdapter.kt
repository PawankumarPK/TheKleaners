package com.example.hp.thekleaners.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.pojoClass.ForCarService
import java.util.*

class CustomAdapter(internal var context: Context, private var profiles: ArrayList<ForCarService>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_layout, parent, false))


    }

    override fun getItemCount(): Int {
        return profiles.size
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CustomAdapter.ViewHolder, position: Int) {
        holder.name.text = profiles[position].carName
        holder.email.text = profiles[position].carNumber
        holder.amount.text = "Service Amount : ₹" + profiles[position].carAmountCalculate.toString()
        holder.type.text = profiles[position].carType
        holder.date.text = "Service Date : " + profiles[position].carDate
        holder.singleAmt.text = "Service Single Day Amount : ₹" + profiles[position].carSingleAmount.toString()
        holder.calcAmt.text = "Service Amount : ₹" + profiles[position].carAmount.toString()
        holder.delete.setOnClickListener { submitDetails() }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name: TextView
        var email: TextView
        var amount: TextView
        var type: TextView
        var date: TextView
        var singleAmt: TextView
        var calcAmt: TextView
        var delete: ImageView


        init {

            name = itemView.findViewById<View>(R.id.post_title) as TextView
            email = itemView.findViewById<View>(R.id.post_desc) as TextView
            amount = itemView.findViewById<View>(R.id.post_amount) as TextView
            type = itemView.findViewById<View>(R.id.post_carType) as TextView
            date = itemView.findViewById<View>(R.id.post_date) as TextView
            singleAmt = itemView.findViewById<View>(R.id.post_singleAmt) as TextView
            calcAmt = itemView.findViewById<View>(R.id.post_calculateAmt) as TextView
            delete = itemView.findViewById<View>(R.id.mDelete) as ImageView
        }
    }

    private fun submitDetails() {


        val emailIntent = Intent(Intent.ACTION_SEND)

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Reason For Remove this service")

        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("info@thekleaners.com"))

        // emailIntent.putExtra(Intent.EXTRA_TEXT, emailData())

        emailIntent.type = "text/plain"

        val matches = context.packageManager.queryIntentActivities(emailIntent, 0)

        var best: ResolveInfo? = null

        for (info in matches)

            if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail"))

                best = info

        if (best != null)

            emailIntent.setClassName(best.activityInfo.packageName, best.activityInfo.name)

        startActivity(context, emailIntent, null)

    }


}
