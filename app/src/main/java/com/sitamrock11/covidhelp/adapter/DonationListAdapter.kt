package com.sitamrock11.covidhelp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.mikhaellopez.circularimageview.CircularImageView
import com.sitamrock11.covidhelp.R
import com.sitamrock11.covidhelp.activity.WebActivity
import com.sitamrock11.covidhelp.model.OrgDetails
import java.util.ArrayList

class DonationListAdapter(val list:ArrayList<OrgDetails>,val context: Context): RecyclerView.Adapter<DonationListAdapter.DonationViewHolder>() {
   inner class DonationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       val imgDonate: CircularImageView=itemView.findViewById(R.id.imgDonate)
       val txtOrgName: TextView=itemView.findViewById(R.id.txtOrgName)
       val txtOrgDetail: TextView=itemView.findViewById(R.id.txtOrgDetail)
       val btnDonate: MaterialButton=itemView.findViewById(R.id.btnDonate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonationViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.donation_list_item, parent,false)
        return DonationViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: DonationViewHolder, position: Int) {
        holder.apply{
            imgDonate.setImageResource(list[position].orgImage)
            txtOrgName.text=list[position].orgName
            txtOrgDetail.text=list[position].orgDetail
            btnDonate.setOnClickListener {
                val intent= Intent(context,WebActivity::class.java)
                intent.putExtra("urlLink",list[position].donationLink)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}