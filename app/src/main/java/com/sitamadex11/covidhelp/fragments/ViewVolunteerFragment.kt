package com.sitamadex11.covidhelp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sitamadex11.covidhelp.R

class ViewVolunteerFragment : Fragment(),View.OnClickListener {
    lateinit var btnAddVolunteer:FloatingActionButton
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_volunteer, container, false)
        init(view)
        click()
        return view
    }

    private fun click() {
        btnAddVolunteer.setOnClickListener(this)
    }

    private fun init(view:View){
    btnAddVolunteer=view.findViewById(R.id.btnAddVolunteer)
}
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btnAddVolunteer->{
                fragmentTransition(AddVolunteerFragment())
            }

        }
    }

    private fun fragmentTransition(fragment: Fragment) {
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .addToBackStack(fragment.javaClass.simpleName)
            .replace(R.id.flVolunteer,fragment)
            .commit()
    }
}