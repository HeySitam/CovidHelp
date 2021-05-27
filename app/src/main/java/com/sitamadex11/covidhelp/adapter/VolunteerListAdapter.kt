package com.sitamadex11.covidhelp.adapter

import android.content.Context
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sitamadex11.covidhelp.R
import com.sitamadex11.covidhelp.model.VolunteerDetailsModel
import java.util.*


class VolunteerListAdapter(val context: Context, val listener:VLAdapter) :
    RecyclerView.Adapter<VolunteerListAdapter.VolunteerListViewHolder>() {
    private val volunteerList = ArrayList<VolunteerDetailsModel>()

    inner class VolunteerListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNameVol: TextView = itemView.findViewById(R.id.txtNameVol)
        val txtStateVol: TextView = itemView.findViewById(R.id.txtStateVol)
        val txtDistrictVol: TextView = itemView.findViewById(R.id.txtDistrictVol)
        val txtPhVol: TextView = itemView.findViewById(R.id.txtPhVol)
        val txtOrgVol: TextView = itemView.findViewById(R.id.txtOrgVol)
        val btnVolCall: FloatingActionButton = itemView.findViewById(R.id.btnVolCall)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VolunteerListViewHolder {
        return VolunteerListViewHolder(
            LayoutInflater.from(context).inflate(R.layout.volunteer_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VolunteerListViewHolder, position: Int) {
        holder.txtNameVol.text=volunteerList[position].name
        holder.txtStateVol.text=volunteerList[position].state
        holder.txtDistrictVol.text=volunteerList[position].district
        holder.txtPhVol.text=volunteerList[position].phone.toString()
        holder.txtOrgVol.text=volunteerList[position].organization
        holder.btnVolCall.setOnClickListener {
            Toast.makeText(context,"lets call ${volunteerList[position].name}",Toast.LENGTH_SHORT).show()
            listener.onCallBtnClicked(volunteerList[position].phone.toString())
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
    fun onCallBtnClicked(phone:String)
}