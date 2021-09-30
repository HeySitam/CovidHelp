package com.sitamadex11.CovidHelp.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.mikhaellopez.circularimageview.CircularImageView
import com.sitamadex11.CovidHelp.R
import com.sitamadex11.CovidHelp.model.OrgDetails
import java.util.*

class DonationListAdapter(val list: ArrayList<OrgDetails>, val context: Context) :
    RecyclerView.Adapter<DonationListAdapter.DonationViewHolder>() {
    inner class DonationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgDonate: CircularImageView = itemView.findViewById(R.id.imgDonate)
        val txtOrgName: TextView = itemView.findViewById(R.id.txtOrgName)
        val txtOrgDetail: TextView = itemView.findViewById(R.id.txtOrgDetail)
        val btnDonate: MaterialButton = itemView.findViewById(R.id.btnDonate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonationViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.donation_list_item, parent, false)
        return DonationViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: DonationViewHolder, position: Int) {
        holder.apply {
            Glide.with(context).load(list[position].img).into(imgDonate)
            txtOrgName.text = list[position].title
            txtOrgDetail.text = list[position].desc
            btnDonate.setOnClickListener {
                val intent = Intent()
                intent.action = Intent.ACTION_VIEW
                intent.data = Uri.parse(list[position].url)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}