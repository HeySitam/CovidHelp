package com.sitamadex11.covidhelp.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sitamadex11.covidhelp.R
import com.sitamadex11.covidhelp.model.CenterDetail
import com.sitamadex11.covidhelp.model.CenterItem
import java.util.*

class VaccineCenterListAdapter(val context: Context) :
    RecyclerView.Adapter<VaccineCenterListAdapter.VaccineCenterViewHolder>() {
    private val centerList = ArrayList<CenterItem>()

    inner class VaccineCenterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtCenterName: TextView = itemView.findViewById<TextView>(R.id.txtCenterName)
        val txtCenterAddress: TextView = itemView.findViewById<TextView>(R.id.txtCenterAddress)
        val txtVaccineName: TextView = itemView.findViewById<TextView>(R.id.txtVaccineName)
        val txtVaccineFee: TextView = itemView.findViewById<TextView>(R.id.txtVaccineFee)
        val txtAgeLimit: TextView = itemView.findViewById<TextView>(R.id.txtAgeLimit)
        val txtDose1: TextView = itemView.findViewById<TextView>(R.id.txtDose1)
        val txtDose2: TextView = itemView.findViewById<TextView>(R.id.txtDose2)
        val txtSlot1: TextView = itemView.findViewById<TextView>(R.id.txtSlot1)
        val txtSlot2: TextView = itemView.findViewById<TextView>(R.id.txtSlot2)
        val txtSlot3: TextView = itemView.findViewById<TextView>(R.id.txtSlot3)
        val txtSlot4: TextView = itemView.findViewById<TextView>(R.id.txtSlot4)
        val txtPinCode: TextView = itemView.findViewById<TextView>(R.id.txtPinCode)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VaccineCenterViewHolder {
        return VaccineCenterViewHolder(
            LayoutInflater.from(context).inflate(R.layout.vaccine_center_listitem, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VaccineCenterViewHolder, position: Int) {
        holder.txtCenterName.text = centerList[position].name
        holder.txtCenterAddress.text = centerList[position].address
        holder.txtVaccineName.text = centerList[position].sessions[0].vaccine
        holder.txtVaccineFee.text = centerList[position].feeType
        holder.txtAgeLimit.text = "${centerList[position].sessions[0].minAgeLimit}+"
        if(centerList[position].sessions[0].dose1.toString()=="0") {
            holder.txtDose1.text = "booked"
            holder.txtDose1.setTextColor(Color.parseColor("#FF0000"))
        }else {
            holder.txtDose1.text = centerList[position].sessions[0].dose1.toString()
            holder.txtDose1.setTextColor(Color.parseColor("#2e7d32"))
        }
        if(centerList[position].sessions[0].dose2.toString()=="0") {
            holder.txtDose2.text = "booked"
            holder.txtDose2.setTextColor(Color.parseColor("#FF0000"))
        }else {
            holder.txtDose2.text = centerList[position].sessions[0].dose2.toString()
            holder.txtDose2.setTextColor(Color.parseColor("#2e7d32"))
        }
    //    holder.txtDose2.text = centerList[position].sessions[0].dose2.toString()
        holder.txtSlot1.text = centerList[position].sessions[0].slots[0]
        holder.txtSlot2.text = centerList[position].sessions[0].slots[1]
        holder.txtSlot3.text = centerList[position].sessions[0].slots[2]
        holder.txtSlot4.text = centerList[position].sessions[0].slots[3]
        holder.txtPinCode.text = centerList[position].pinCode.toString()

    }

    override fun getItemCount(): Int {
        return centerList.size
    }

    fun updateList(newList: ArrayList<CenterItem>) {
        centerList.clear()
        centerList.addAll(newList)
        notifyDataSetChanged()
    }
}