package com.example.hp.thekleaners.adapters

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.pojoClass.ForCarService
import java.util.*

class InvoiceAdapter(internal var context: Context, private var profiles: ArrayList<ForCarService>) : RecyclerView.Adapter<InvoiceAdapter.ViewHolder>() {

    var mCurrentDate: Calendar? = null
    var day: Int = 0
    var month: Int = 0
    var year: Int = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.invoice_list_layout, parent, false))


    }

    override fun getItemCount(): Int {
        return profiles.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.name.text = profiles[position].carName
        holder.email.text = profiles[position].carNumber
        holder.amount.text = "Service Amount : ₹" + profiles[position].carAmount.toString()
        holder.type.text = profiles[position].carType
        holder.date.text = "Service Date : " + profiles[position].carDate
        holder.singleAmt.text = "Service Single Day Amount : ₹" + profiles[position].carSingleAmount.toString()
        holder.totalAmt.text = "Service Amount : ₹" + profiles[position].carAmountCalculate.toString()

        holder.delete.setOnClickListener { submitDetails() }

        mCurrentDate = Calendar.getInstance()
        day = mCurrentDate!!.get(Calendar.DAY_OF_MONTH)
        month = mCurrentDate!!.get(Calendar.MONTH)
        year = mCurrentDate!!.get(Calendar.YEAR)

        holder.datepicker.inputType = InputType.TYPE_NULL
        month += 1



        holder.datepicker.text = day.toString()
        holder.datepicker.setOnClickListener { holder.mCaDateEditextFunction() }

        if (day == 17) {
            holder.amt.text = profiles[position].carAmount.toString()
        } else {
            holder.amt.text = profiles[position].carAmountCalculate.toString()
        }


    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        var name: TextView
        var email: TextView
        var type: TextView
        var amount: TextView
        var date: TextView
        var singleAmt: TextView
        var totalAmt: TextView
        var datepicker: TextView
        var amt: TextView
        var delete: ImageView



        init {

            name = itemView.findViewById<View>(R.id.post_title) as TextView
            email = itemView.findViewById<View>(R.id.post_desc) as TextView
            amount = itemView.findViewById<View>(R.id.post_amount) as TextView
            type = itemView.findViewById<View>(R.id.post_carType) as TextView
            date = itemView.findViewById<View>(R.id.post_date) as TextView
            singleAmt = itemView.findViewById<View>(R.id.post_singleAmt) as TextView
            totalAmt = itemView.findViewById<View>(R.id.post_totalAmt) as TextView
            datepicker = itemView.findViewById<View>(R.id.mDatepicker) as TextView
            amt = itemView.findViewById<View>(R.id.mAmt) as TextView
            delete = itemView.findViewById<View>(R.id.mDelete) as ImageView

        }


        fun mCaDateEditextFunction() {
            val datePickerDialog = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                var monthOfYear = monthOfYear

                monthOfYear += 1
                datepicker.text = dayOfMonth.toString() + "-" + monthOfYear + "-" + year
            }, year, month, day)
            datePickerDialog.show()
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

        ContextCompat.startActivity(context, emailIntent, null)

    }

}
