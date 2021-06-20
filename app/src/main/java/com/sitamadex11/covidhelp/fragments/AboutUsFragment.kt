package com.sitamadex11.covidhelp.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.sitamadex11.covidhelp.R

class AboutUsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_about_us, container, false)
        val btnViewRepo = view!!.findViewById<MaterialButton>(R.id.btnViewRepository)
        btnViewRepo.setOnClickListener {
            val url = "https://github.com/sitamadex11/CovidHelp"
            intentBrowse(url)
        }
        return view
    }

    private fun intentBrowse(url: String) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.data = Uri.parse(url)
        requireContext().startActivity(intent)
    }
}