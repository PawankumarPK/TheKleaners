package com.example.hp.thekleaners.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.pojoClass.ForAddress
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.address_viewholder.view.*

class RecyclerAddressAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerAddressAdapter.ViewHolder>() {

    private var user_id: String? = null
    private val db = FirebaseFirestore.getInstance()
    private val notebookRef = db.collection("Users")


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.address_viewholder, parent, false))
    }

    override fun getItemCount(): Int {
        return 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindItems()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindItems() {

            //loadAddressData()
        }


       /* private fun loadAddressData() {
            notebookRef.document(user_id!!).collection("Address").get().addOnSuccessListener { queryDocumentSnapshots ->
                //var data = ""

                for (documentSnapshot in queryDocumentSnapshots) {
                    val note = documentSnapshot.toObject(ForAddress::class.java)
                    note.documentId = documentSnapshot.id

                    val documentaddress = note.address
                    val documentlandmark = note.landmark
                    val documentpincode = note.pincode
                    val documentstate = note.selectState
                    val documentcity = note.selectCity

                    itemView.mAddressSavedAddress.text = documentaddress
                    itemView.mLandmarkSavedAddress.text = documentlandmark
                    itemView.mSelectStateSavedAddress.text = documentstate
                    itemView.mSelectCitySavedAddress.text = documentcity
                    itemView.PinCodeSavedAddress.text = documentpincode

                    // data += "\n\n"
                }
                itemView.savedAddress_progress.visibility = View.INVISIBLE
                *//*mAddressSavedAddress.text = data
            mLandmarkSavedAddress.text = data
            mSelectStateSavedAddress.text = data
            mSelectCitySavedAddress.text = data
            PinCodeSavedAddress.text = data*//*
            }
        }
*/
    }
}
