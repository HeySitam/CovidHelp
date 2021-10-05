package com.sitamadex11.CovidHelp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.sitamadex11.CovidHelp.R
import com.sitamadex11.CovidHelp.model.VolunteerDetailsModel
import java.util.*


class VolunteerListAdapter(val context: Context, val listener: VLAdapter) :
    RecyclerView.Adapter<VolunteerListAdapter.VolunteerListViewHolder>() {
    private val volunteerList = ArrayList<VolunteerDetailsModel>()

    inner class VolunteerListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNameVol: TextView = itemView.findViewById(R.id.txtNameVol)
        val txtStateVol: TextView = itemView.findViewById(R.id.txtStateVol)
        val txtDistrictVol: TextView = itemView.findViewById(R.id.txtDistrictVol)
        val txtPhVol: TextView = itemView.findViewById(R.id.txtPhVol)
        val txtOrgVol: TextView = itemView.findViewById(R.id.txtOrgVol)
        val btnVolCall: MaterialCardView = itemView.findViewById(R.id.btnVolCall)
        val btnVolMessage: MaterialCardView = itemView.findViewById(R.id.btnVolMessage)
        val txtVolDesc: TextView = itemView.findViewById(R.id.txtVolDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VolunteerListViewHolder {
        return VolunteerListViewHolder(
            LayoutInflater.from(context).inflate(R.layout.volunteer_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VolunteerListViewHolder, position: Int) {
        holder.txtNameVol.text = volunteerList[position].name
        holder.txtStateVol.text = volunteerList[position].state
        holder.txtDistrictVol.text = volunteerList[position].district
        holder.txtPhVol.text = volunteerList[position].phone.toString()
        holder.txtOrgVol.text = volunteerList[position].organization
        holder.btnVolCall.setOnClickListener {
            Toast.makeText(
                context,
                "calling ${volunteerList[position].name}...",
                Toast.LENGTH_SHORT
            ).show()
            listener.onCallBtnClicked(volunteerList[position].phone.toString())
        }
        holder.btnVolMessage.setOnClickListener {
            Toast.makeText(
                context,
                "messaging ${volunteerList[position].name}...",
                Toast.LENGTH_SHORT
            ).show()
            listener.onMessageBtnClicked(volunteerList[position].phone.toString())
        }
        if (volunteerList[position].description.isNullOrEmpty()) {
            holder.txtVolDesc.text = "I am here to help you! 😇"
        } else {
            holder.txtVolDesc.text = volunteerList[position].description
        }
    }

    override fun getItemCount(): Int {
        return volunteerList.size
    }

    fun updateList(newList: ArrayList<VolunteerDetailsModel>) {
        volunteerList.clear()
        volunteerList.addAll(newList)
        notifyDataSetChanged()
    }
}

interface VLAdapter {
    fun onCallBtnClicked(phone: String)
    fun onMessageBtnClicked(phone: String)
}