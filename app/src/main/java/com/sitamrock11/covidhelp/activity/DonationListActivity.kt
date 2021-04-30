package com.sitamrock11.covidhelp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sitamrock11.covidhelp.R
import com.sitamrock11.covidhelp.adapter.DonationListAdapter
import com.sitamrock11.covidhelp.model.OrgDetails
import kotlinx.android.synthetic.main.donation_list_activity.*
import java.util.*

class DonationListActivity : AppCompatActivity() {
    val list = ArrayList<OrgDetails>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.donation_list_activity)
        addItemToList()
        val adapter = DonationListAdapter(list,this)

        recycler.adapter = adapter
        recycler.set3DItem(true)
        recycler.setAlpha(true)
        val carouselLayoutManager = recycler.getCarouselLayoutManager()
        val currentlyCenterPosition = recycler.getSelectedPosition()
    }

    private fun addItemToList() {
        list.add(
            OrgDetails(
                R.drawable.hemkunt_foundation,
                "Hemkunt Foundation",
                getString(R.string.hemkunt_foundation),
                "https://hemkuntfoundation.com/donate-now/"
            )
        )
        list.add(
            OrgDetails(
                R.drawable.act_grants,
                "ACT Grants",
                getString(R.string.act_grants),
                "https://actgrants.in/donate/"
            )
        )
        list.add(
            OrgDetails(
                R.drawable.give_india,
                "Give India",
                getString(R.string.give_india),
                "https://covid.giveindia.org/safeperiod/"
            )
        )
        list.add(
                OrgDetails(
                    R.drawable.zomato,
                    "Zomato Feeding India",
                    getString(R.string.zomato_feeding_india),
                    "https://www.feedingindia.org/donate/help-save-my-india?review_id=oxygen"
                )
                )
        list.add(
            OrgDetails(
                R.drawable.goonj,
                "Goonj",
                getString(R.string.goonj),
                "https://goonj.org/support-covid-19-affected/"
            )
        )
        list.add(
            OrgDetails(
                R.drawable.paytm,
                "Paytm Foundation",
                getString(R.string.paytm_foundation),
                "https://paytm.com/offer/donate-oxygen"
            )
        )

    }
}