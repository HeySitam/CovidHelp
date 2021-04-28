package com.sitamrock11.covidhelp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),CompoundButton.OnCheckedChangeListener {
    lateinit var list:ArrayList<String>
    var url="https://twitter.com/search?q=verified%20Kolkata%20"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         list= ArrayList()
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
        btnSearch.setOnClickListener {
            if (list.isNotEmpty()) {
                var item = list[0]
                for (i in 1 until list.size) item += "%20OR%20${list[i]}"

                url = "$url($item)%20-\"not%20verified\"%20-\"unverified\"%20-\"needed\"%20-\"need\"%20-\"needs\"%20-\"required\"%20-\"require\"%20-\"requires\"%20-\"requirement\"%20-\"requirements\"&f=live"
                println(url)
                val intent = Intent(this, WebActivity::class.java)
                intent.putExtra("urlLink", url)
                startActivity(intent)
            }else{
                Toast.makeText(this,"Select at least one required field",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when(buttonView!!.id){
            R.id.beds->{
                if(isChecked && !list.contains(beds.text.toString())){
                    list.add("bed")
                    list.add("beds")
                }
            }
            R.id.icu->{
                if(isChecked && !list.contains(icu.text.toString())){
                    list.add("icu")
                }
            }
            R.id.oxygen->{
                if(isChecked && !list.contains(beds.text.toString())){
                    list.add("oxygen")
                }
            }
            R.id.ventilator->{
                if(isChecked && !list.contains(beds.text.toString())){
                    list.add("ventilator")

                }
            }
            R.id.tests->{
                if(isChecked && !list.contains(beds.text.toString())){
                    list.add("test")
                    list.add("tests")
                }
            }
            R.id.fabiflu->{
                if(isChecked && !list.contains(beds.text.toString())){
                    list.add("fabiflu")
                }
            }
            R.id.remdesivir->{
                if(isChecked && !list.contains(beds.text.toString())){
                    list.add("remdesivir")
                }
            }
            R.id.favipiravir->{
                if(isChecked && !list.contains(beds.text.toString())){
                    list.add("favipiravir")
                }
            }
            R.id.tocilizumab->{
                if(isChecked && !list.contains(beds.text.toString())){
                    list.add("tocilizumba")
                }
            }
            R.id.plasma->{
                if(isChecked && !list.contains(beds.text.toString())){
                    list.add("plasma")
                }
            }
            R.id.food->{
                if(isChecked && !list.contains(beds.text.toString())){
                    list.add("food")
                }
            }
        }
    }
}