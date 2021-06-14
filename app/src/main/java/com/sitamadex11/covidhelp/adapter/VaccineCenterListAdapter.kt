package com.sitamadex11.covidhelp.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.sitamadex11.covidhelp.R
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
        val btnBookVac:MaterialButton = itemView.findViewById(R.id.btnBookVac)
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
        if (centerList[position].sessions[0].dose1.toString() == "0") {
            holder.txtDose1.text = "booked"
            holder.txtDose1.setTextColor(Color.parseColor("#FF0000"))
        } else {
            holder.txtDose1.text = centerList[position].sessions[0].dose1.toString()
            holder.txtDose1.setTextColor(Color.parseColor("#2e7d32"))
        }
        if (centerList[position].sessions[0].dose2.toString() == "0") {
            holder.txtDose2.text = "booked"
            holder.txtDose2.setTextColor(Color.parseColor("#FF0000"))
        } else {
            holder.txtDose2.text = centerList[position].sessions[0].dose2.toString()
            holder.txtDose2.setTextColor(Color.parseColor("#2e7d32"))
        }
        //    holder.txtDose2.text = centerList[position].sessions[0].dose2.toString()
        val slotSize = centerList[position].sessions[0].slots.size
        when(slotSize){
            1 ->{
                holder.txtSlot1.text = centerList[position].sessions[0].slots[0]
                holder.txtSlot2.text = "No Slot Found"
                holder.txtSlot3.text = "No Slot Found"
                holder.txtSlot4.text = "No Slot Found"
            }
            2->{
                holder.txtSlot1.text = centerList[position].sessions[0].slots[0]
                holder.txtSlot2.text = centerList[position].sessions[0].slots[1]
                holder.txtSlot3.text = "No Slot Found"
                holder.txtSlot4.text = "No Slot Found"
            }
            3 ->{
                holder.txtSlot1.text = centerList[position].sessions[0].slots[0]
                holder.txtSlot2.text = centerList[position].sessions[0].slots[1]
                holder.txtSlot3.text = centerList[position].sessions[0].slots[2]
                holder.txtSlot4.text = "No Slot Found"
            }
            4 ->{
                holder.txtSlot1.text = centerList[position].sessions[0].slots[0]
                holder.txtSlot2.text = centerList[position].sessions[0].slots[1]
                holder.txtSlot3.text = centerList[position].sessions[0].slots[2]
                holder.txtSlot4.text = centerList[position].sessions[0].slots[3]
            }
        }
        holder.txtPinCode.text = centerList[position].pinCode.toString()
        holder.btnBookVac.setOnClickListener {
            if (centerList[position].sessions[0].dose2.toString() == "0"
                && centerList[position].sessions[0].dose1.toString() == "0"
            ) {
                 Toast.makeText(context,"Sorry! No slot Available here",Toast.LENGTH_SHORT).show()
            }else{
                val url = "https://selfregistration.cowin.gov.in/"
                val intent = Intent()
                intent.action = Intent.ACTION_VIEW
                intent.data = Uri.parse(url)
                context.startActivity(intent)
            }
        }

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