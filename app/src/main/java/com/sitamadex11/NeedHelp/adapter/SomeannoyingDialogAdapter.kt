package com.sitamadex11.NeedHelp.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.sitamadex11.NeedHelp.R
import com.sitamadex11.NeedHelp.model.SomeannoynigDialogItems

class SomeannoyingDialogAdapter(val context: Context) :
    RecyclerView.Adapter<SomeannoyingDialogAdapter.SomeannoyingViewHolder>() {
    val list = ArrayList<SomeannoynigDialogItems>()

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
        holder.imgDialogItem.setImageResource(list[position].img)
        holder.txtDialogItem.text = list[position].name
        holder.cvDialogItem.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.data = Uri.parse(list[position].url)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(newList: java.util.ArrayList<SomeannoynigDialogItems>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}