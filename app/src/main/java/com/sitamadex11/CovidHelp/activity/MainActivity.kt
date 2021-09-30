package com.sitamadex11.CovidHelp.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sitamadex11.CovidHelp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener {
    var list = ArrayList<String>()
    var otherItem: String? = null
    var verify: String? = null
    var url = "https://twitter.com/search?q=verified%20"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        if (!chkOther.isChecked) {
            tilOthers.visibility = View.GONE
        }
        if (!stwVerification.isChecked) {
            stwVerification.text = "All "
            verify = "\"not%20verified\"%20-\"unverified\"%20-"
        }
        val city = intent.getStringExtra("city")
        if (!city.equals("other")) {
            cvCityName.visibility = View.GONE
        }
        beds.setOnCheckedChangeListener(this)
        icu.setOnCheckedChangeListener(this)
        oxygen.setOnCheckedChangeListener(this)
        ventilator.setOnCheckedChangeListener(this)
        tests.setOnCheckedChangeListener(this)
        fabiflu.setOnCheckedChangeListener(this)
        remdesivir.setOnCheckedChangeListener(this)
        favipiravir.setOnCheckedChangeListener(this)
        tocilizumab.setOnCheckedChangeListener(this)
        plasma.setOnCheckedChangeListener(this)
        food.setOnCheckedChangeListener(this)
        ambulance.setOnCheckedChangeListener(this)
        chkOther.setOnCheckedChangeListener(this)
        if (stwVerification.isChecked) {
            verify = ""
        } else {
            verify = "\"not%20verified\"%20-\"unverified\"%20-"
        }
        stwVerification.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                stwVerification.text = "Verified Only "
                verify = ""
            } else {
                stwVerification.text = "All "
                verify = "\"not%20verified\"%20-\"unverified\"%20-"
            }
        }
        btnSearch.setOnClickListener {
            if (etOthers.text.toString().isNotEmpty()) {
                otherItem = etOthers.text.toString()
                list.add(otherItem!!)
            }
            if (city.equals("other")) {
                if (list.isNotEmpty() && !etCityName.text.toString().isNullOrEmpty()) {
                    var item = list[0]
                    for (i in 1 until list.size) item += "%20OR%20${list[i]}"
                    url =
                        "$url${etCityName.text.toString()}%20($item)%20-$verify\"needed\"%20-\"need\"%20-\"needs\"%20-\"required\"%20-\"require\"%20-\"requires\"%20-\"requirement\"%20-\"requirements\"&f=live"

                    println(url)
                    val intent = Intent(this, WebActivity::class.java)
                    intent.putExtra("urlLink", url)
                    startActivity(intent)
                } else if (!etCityName.text.toString().isNullOrEmpty()) {
                    Toast.makeText(this, "Select at least one required field", Toast.LENGTH_SHORT)
                        .show()
                } else if (etCityName.text.toString().isNullOrEmpty()) {
                    etCityName.error = "City-Name missing"
                    Toast.makeText(this, "Enter a valid City-Name", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (list.isEmpty()) {
                    Toast.makeText(this, "Select at least one required field", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    var item = list[0]
                    for (i in 1 until list.size) item += "%20OR%20${list[i]}"
                    url =
                        "$url$city%20($item)%20-$verify\"needed\"%20-\"need\"%20-\"needs\"%20-\"required\"%20-\"require\"%20-\"requires\"%20-\"requirement\"%20-\"requirements\"&f=live"
                    val intent = Intent(this, WebActivity::class.java)
                    intent.putExtra("urlLink", url)
                    startActivity(intent)
                }
            }

        }
    }

    override fun onPause() {
        super.onPause()
        list.clear()
        otherItem = null
        verify = null
        url = "https://twitter.com/search?q=verified%20"
        beds.isChecked = false
        icu.isChecked = false
        oxygen.isChecked = false
        ventilator.isChecked = false
        tests.isChecked = false
        fabiflu.isChecked = false
        remdesivir.isChecked = false
        favipiravir.isChecked = false
        tocilizumab.isChecked = false
        plasma.isChecked = false
        food.isChecked = false
        ambulance.isChecked = false
        chkOther.isChecked = false
        stwVerification.isChecked = false
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (buttonView!!.id) {
            R.id.beds -> {
                if (isChecked && !(list.contains("bed") || list.contains("beds"))) {
                    list.add("bed")
                    list.add("beds")
                }
                if (!isChecked && (list.contains("bed") || list.contains("beds"))) {
                    list.remove("bed")
                    list.remove("beds")
                }
            }
            R.id.icu -> {
                if (isChecked && !list.contains("icu")) {
                    list.add("icu")
                }
                if (!isChecked && list.contains("icu")) {
                    list.remove("icu")
                }
            }
            R.id.oxygen -> {
                if (isChecked && !list.contains("oxygen")) {
                    list.add("oxygen")
                }
                if (!isChecked && list.contains("oxygen")) {
                    list.remove("oxygen")
                }
            }
            R.id.ventilator -> {
                if (isChecked && !list.contains("ventilator")) {
                    list.add("ventilator")

                }
                if (!isChecked && list.contains("ventilator")) {
                    list.remove("ventilator")

                }
            }
            R.id.tests -> {
                if (isChecked && !(list.contains("test") || list.contains("tests"))) {
                    list.add("test")
                    list.add("tests")
                }
                if (!isChecked && (list.contains("test") || list.contains("tests"))) {
                    list.remove("test")
                    list.remove("tests")
                }
            }
            R.id.fabiflu -> {
                if (isChecked && !list.contains("fabiflu")) {
                    list.add("fabiflu")
                }
                if (!isChecked && list.contains("fabiflu")) {
                    list.remove("fabiflu")
                }
            }
            R.id.remdesivir -> {
                if (isChecked && !list.contains("remdesivir")) {
                    list.add("remdesivir")
                }
                if (!isChecked && list.contains("remdesivir")) {
                    list.remove("remdesivir")
                }
            }
            R.id.favipiravir -> {
                if (isChecked && !list.contains("favipiravir")) {
                    list.add("favipiravir")
                }
                if (!isChecked && list.contains("favipiravir")) {
                    list.remove("favipiravir")
                }
            }
            R.id.tocilizumab -> {
                if (isChecked && !list.contains("tocilizumba")) {
                    list.add("tocilizumba")
                }
                if (!isChecked && list.contains("tocilizumba")) {
                    list.remove("tocilizumba")
                }
            }
            R.id.plasma -> {
                if (isChecked && !list.contains("plasma")) {
                    list.add("plasma")
                }
                if (!isChecked && list.contains("plasma")) {
                    list.remove("plasma")
                }
            }
            R.id.food -> {
                if (isChecked && !list.contains("food")) {
                    list.add("food")
                }
                if (!isChecked && list.contains("food")) {
                    list.remove("food")
                }
            }
            R.id.ambulance -> {
                if (isChecked && !list.contains("ambulance")) {
                    list.add("ambulance")
                }
                if (!isChecked && list.contains("ambulance")) {
                    list.remove("ambulance")
                }
            }
            R.id.chkOther -> {
                if (isChecked)
                    tilOthers.visibility = View.VISIBLE
                else
                    tilOthers.visibility = View.GONE
            }
        }
    }

}