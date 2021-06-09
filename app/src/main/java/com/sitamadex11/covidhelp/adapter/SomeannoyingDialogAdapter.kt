package com.sitamadex11.covidhelp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.sitamadex11.covidhelp.R

class SomeannoyingDialogAdapter(val context: Context) :
    RecyclerView.Adapter<SomeannoyingDialogAdapter.SomeannoyingViewHolder>() {
    val list = ArrayList<String>()

    inner class SomeannoyingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgDialogItem = itemView.findViewById<ImageView>(R.id.imgDialogItem)
        val txtDialogItem = itemView.findViewById<TextView>(R.id.txtDialogItem)
        val cvDialogItem = itemView.findViewById<MaterialCardView>(R.id.cvDialogItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SomeannoyingViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.custom_dialog_item, parent, false)
        return SomeannoyingViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: SomeannoyingViewHolder, position: Int) {
        holder.imgDialogItem.setImageResource(R.drawable.app_logo)
        holder.txtDialogItem.text = list[position]
        holder.cvDialogItem.setOnClickListener {
            Toast.makeText(context, "${list[position]} clicked", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(newList: java.util.ArrayList<String>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}