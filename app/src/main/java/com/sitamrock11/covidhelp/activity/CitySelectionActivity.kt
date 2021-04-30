package com.sitamrock11.covidhelp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import com.royrodriguez.transitionbutton.TransitionButton
import com.sitamrock11.covidhelp.R
import kotlinx.android.synthetic.main.activity_city_selection.*

class CitySelectionActivity : AppCompatActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_selection)
        transitionButton.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("city", "other")
                startActivity(intent)
                Toast.makeText(this, "Here we Go!!", Toast.LENGTH_SHORT).show()
        }
        mdKolkata.setOnClickListener(this)
        mdDelhi.setOnClickListener(this)
        mdPune.setOnClickListener(this)
        mdMumbai.setOnClickListener(this)
        mdBangalore.setOnClickListener(this)
        mdThane.setOnClickListener(this)
        mdHyderbad.setOnClickListener(this)
        mdNagpur.setOnClickListener(this)
        mdLucknow.setOnClickListener(this)
        mdAhmedabad.setOnClickListener(this)
        mdChennai.setOnClickListener(this)
        mdGoa.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.mdKolkata ->{
                val intent=Intent(this, MainActivity::class.java)
                intent.putExtra("city","kolkata")
                startActivity(intent)
            }
            R.id.mdDelhi ->{
                val intent=Intent(this, MainActivity::class.java)
                intent.putExtra("city","delhi")
                startActivity(intent)
            }
            R.id.mdPune ->{
                val intent=Intent(this, MainActivity::class.java)
                intent.putExtra("city","pune")
                startActivity(intent)
            }
            R.id.mdMumbai ->{
                val intent=Intent(this, MainActivity::class.java)
                intent.putExtra("city","mumbai")
                startActivity(intent)
            }
            R.id.mdBangalore ->{
                val intent=Intent(this, MainActivity::class.java)
                intent.putExtra("city","bangalore")
                startActivity(intent)
            }
            R.id.mdThane ->{
                val intent=Intent(this, MainActivity::class.java)
                intent.putExtra("city","thane")
                startActivity(intent)
            }
            R.id.mdHyderbad ->{
                val intent=Intent(this, MainActivity::class.java)
                intent.putExtra("city","hyderbad")
                startActivity(intent)
            }
            R.id.mdNagpur ->{
                val intent=Intent(this, MainActivity::class.java)
                intent.putExtra("city","nagpur")
                startActivity(intent)
            }
            R.id.mdLucknow ->{
                val intent=Intent(this, MainActivity::class.java)
                intent.putExtra("city","lucknow")
                startActivity(intent)
            }
            R.id.mdAhmedabad ->{
                val intent=Intent(this, MainActivity::class.java)
                intent.putExtra("city","ahmedabad")
                startActivity(intent)
            }
            R.id.mdChennai ->{
                val intent=Intent(this, MainActivity::class.java)
                intent.putExtra("city","chennai")
                startActivity(intent)
            }
            R.id.mdGoa ->{
                val intent=Intent(this, MainActivity::class.java)
                intent.putExtra("city","goa")
                startActivity(intent)
            }
        }
    }
}