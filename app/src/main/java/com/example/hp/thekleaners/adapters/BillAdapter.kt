package com.example.hp.thekleaners.adapters

import android.app.DatePickerDialog
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.pojoClass.ForCarService
import java.util.*








class BillAdapter(internal var context: Context, private var profiles: ArrayList<ForCarService>) : RecyclerView.Adapter<BillAdapter.ViewHolder>() {

    var mCurrentDate: Calendar? = null
    var day: Int = 0
    var month: Int = 0
    var year: Int = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.bill_list_layout, parent, false))


    }

    override fun getItemCount(): Int {
        return profiles.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val price = Integer.parseInt(profiles[position].carSingleAmount.toString())
        val count = itemCount

        var tsum = 0
        for (i in 0 until count) {
            tsum += price
        }


        holder.name.text = profiles[position].carName
        holder.email.text = profiles[position].carNumber
        holder.amount.text = "Bill Amount : ₹" + profiles[position].carAmount.toString()
        holder.type.text = profiles[position].carType
        holder.date.text = "Service Date : " + profiles[position].carDate
        holder.singleAmt.text = "Bill Single Day Amount : ₹" + profiles[position].carSingleAmount.toString()
        holder.totalAmt.text = "₹" + profiles[position].carAmountCalculate.toString()
        holder.totalCalculation.text = tsum.toString()






        mCurrentDate = Calendar.getInstance()
        day = mCurrentDate!!.get(Calendar.DAY_OF_MONTH)
        month = mCurrentDate!!.get(Calendar.MONTH)
        year = mCurrentDate!!.get(Calendar.YEAR)

        holder.datepicker.inputType = InputType.TYPE_NULL
        month += 1



        holder.datepicker.text = day.toString()
        holder.datepicker.setOnClickListener { holder.mCaDateEditextFunction() }

        if (day == 1) {
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
        var totalCalculation: TextView



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
            totalCalculation = itemView.findViewById<View>(R.id.totalAmtCalculation) as TextView





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
}
