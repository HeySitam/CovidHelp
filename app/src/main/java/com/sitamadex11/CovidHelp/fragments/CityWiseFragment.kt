package com.sitamadex11.CovidHelp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.sitamadex11.CovidHelp.R
import com.sitamadex11.CovidHelp.activity.MainActivity

class CityWiseFragment : Fragment(), View.OnClickListener {
    lateinit var btnSearchCity: MaterialButton
    lateinit var mdKolkata: MaterialCardView
    lateinit var mdDelhi: MaterialCardView
    lateinit var mdPune: MaterialCardView
    lateinit var mdMumbai: MaterialCardView
    lateinit var mdBangalore: MaterialCardView
    lateinit var mdJaipur: MaterialCardView
    lateinit var mdHyderbad: MaterialCardView
    lateinit var mdAgra: MaterialCardView
    lateinit var mdLucknow: MaterialCardView
    lateinit var mdAhmedabad: MaterialCardView
    lateinit var mdChennai: MaterialCardView
    lateinit var mdGoa: MaterialCardView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_city_wise, container, false)
        init(view!!)
        btnSearchCity.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("city", "other")
            startActivity(intent)
        }
        mdKolkata.setOnClickListener(this)
        mdDelhi.setOnClickListener(this)
        mdPune.setOnClickListener(this)
        mdMumbai.setOnClickListener(this)
        mdBangalore.setOnClickListener(this)
        mdJaipur.setOnClickListener(this)
        mdHyderbad.setOnClickListener(this)
        mdAgra.setOnClickListener(this)
        mdLucknow.setOnClickListener(this)
        mdAhmedabad.setOnClickListener(this)
        mdChennai.setOnClickListener(this)
        mdGoa.setOnClickListener(this)
        return view
    }

    private fun init(view: View) {
        btnSearchCity = view.findViewById(R.id.btnSearchCity)
        mdKolkata = view.findViewById(R.id.mdKolkata)
        mdDelhi = view.findViewById(R.id.mdDelhi)
        mdPune = view.findViewById(R.id.mdPune)
        mdMumbai = view.findViewById(R.id.mdMumbai)
        mdBangalore = view.findViewById(R.id.mdBangalore)
        mdJaipur = view.findViewById(R.id.mdJaipur)
        mdHyderbad = view.findViewById(R.id.mdHyderbad)
        mdAgra = view.findViewById(R.id.mdAgra)
        mdLucknow = view.findViewById(R.id.mdLucknow)
        mdAhmedabad = view.findViewById(R.id.mdAhmedabad)
        mdChennai = view.findViewById(R.id.mdChennai)
        mdGoa = view.findViewById(R.id.mdGoa)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.mdKolkata -> {
                val intent = Intent(context, MainActivity::class.java)
                intent.putExtra("city", "kolkata")
                startActivity(intent)
            }
            R.id.mdDelhi -> {
                val intent = Intent(context, MainActivity::class.java)
                intent.putExtra("city", "delhi")
                startActivity(intent)
            }
            R.id.mdPune -> {
                val intent = Intent(context, MainActivity::class.java)
                intent.putExtra("city", "pune")
                startActivity(intent)
            }
            R.id.mdMumbai -> {
                val intent = Intent(context, MainActivity::class.java)
                intent.putExtra("city", "mumbai")
                startActivity(intent)
            }
            R.id.mdBangalore -> {
                val intent = Intent(context, MainActivity::class.java)
                intent.putExtra("city", "bangalore")
                startActivity(intent)
            }
            R.id.mdJaipur -> {
                val intent = Intent(context, MainActivity::class.java)
                intent.putExtra("city", "thane")
                startActivity(intent)
            }
            R.id.mdHyderbad -> {
                val intent = Intent(context, MainActivity::class.java)
                intent.putExtra("city", "hyderbad")
                startActivity(intent)
            }
            R.id.mdAgra -> {
                val intent = Intent(context, MainActivity::class.java)
                intent.putExtra("city", "nagpur")
                startActivity(intent)
            }
            R.id.mdLucknow -> {
                val intent = Intent(context, MainActivity::class.java)
                intent.putExtra("city", "lucknow")
                startActivity(intent)
            }
            R.id.mdAhmedabad -> {
                val intent = Intent(context, MainActivity::class.java)
                intent.putExtra("city", "ahmedabad")
                startActivity(intent)
            }
            R.id.mdChennai -> {
                val intent = Intent(context, MainActivity::class.java)
                intent.putExtra("city", "chennai")
                startActivity(intent)
            }
            R.id.mdGoa -> {
                val intent = Intent(context, MainActivity::class.java)
                intent.putExtra("city", "goa")
                startActivity(intent)
            }
        }
    }

}