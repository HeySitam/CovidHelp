package com.sitamadex11.covidhelp.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.sitamadex11.covidhelp.R
import com.sitamadex11.covidhelp.covidTrackerApi.CovidData
import com.sitamadex11.covidhelp.covidTrackerApi.Data
import com.sitamadex11.covidhelp.covidTrackerApi.Regional
import com.sitamadex11.covidhelp.util.ApiUtilities
import org.eazegraph.lib.charts.BarChart
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.BarModel
import org.eazegraph.lib.models.PieModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class CovidTrackerActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private var mRecovered: TextView? = null
    private var mDeath: TextView? = null
    private var mActive: TextView? = null
    private var mpieChart: PieChart? = null
    private var mSelectState: Spinner? = null
    private var mSelectedStateName: String? = null
    private var mData: Data? = null
    private var mBarChart: BarChart? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_covid_tracker)
        init()
        val mState_names = resources.getStringArray(R.array.states_name)
        mSelectState!!.onItemSelectedListener = this
        val adapter: ArrayAdapter<*> = ArrayAdapter<Any?>(this, R.layout.spinner_item, mState_names)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mSelectState!!.adapter = adapter
        ApiUtilities.apiInterface.covidData!!.enqueue(object : Callback<CovidData?> {
            override fun onResponse(call: Call<CovidData?>, response: Response<CovidData?>) {
                val covidData = response.body()
                mData = covidData!!.data
                UpdateUI(mData)
            }

            override fun onFailure(call: Call<CovidData?>, t: Throwable) {
                Toast.makeText(applicationContext, "Error : " + t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun init(){
            mRecovered = findViewById(R.id.total_recover)
            mActive = findViewById(R.id.total_active)
            mDeath = findViewById(R.id.total_death)
            mpieChart = findViewById(R.id.piechart)
            mSelectState = findViewById(R.id.spinnerSelectState)
            mBarChart = findViewById(R.id.barchart)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val valueFromSpinner = parent!!.getItemAtPosition(position).toString()
        mSelectedStateName = valueFromSpinner
        UpdateUI(mData)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        mSelectedStateName = getString(R.string.Inida)
    }
    private fun UpdateUI(data: Data?) {
        var total = 0
        var deaths = 0
        var discharged = 0

        if (data != null) {
            if (mSelectedStateName == "India") {
                val summery = data.summary
                total = summery.total.toInt()
                deaths = summery.deaths.toInt()
                discharged = summery.discharged.toInt()
            } else {
                val regionals: MutableList<Regional> = ArrayList()
                regionals.addAll(data.regional)
                for (i in regionals.indices) {
                    if (regionals[i].loc == mSelectedStateName) {
                        total = regionals[i].totalConfirmed.toInt()
                        deaths = regionals[i].deaths.toInt()
                        discharged = regionals[i].discharged.toInt()
                    }

                }
            }
        }
        mActive!!.text = total.toString()
        mDeath!!.text = deaths.toString()
        mRecovered!!.text = discharged.toString()
        mpieChart!!.clearChart()
        mpieChart!!.addPieSlice(PieModel("Recoverd", discharged.toFloat(), Color.parseColor("#FFFF00")))
        mpieChart!!.addPieSlice(PieModel("Active", total.toFloat(), Color.parseColor("#FF3700B3")))
        mpieChart!!.addPieSlice(PieModel("Death", deaths.toFloat(), Color.parseColor("#F44336")))
        mpieChart!!.startAnimation()

        mBarChart!!.clearChart()
        mBarChart!!.addBar(BarModel("Death", deaths.toFloat(), Color.parseColor("#F44336")))
        mBarChart!!.addBar(BarModel("Recovered",discharged.toFloat(), Color.parseColor("#FFFF00")))
        mBarChart!!.addBar(BarModel("Active", total.toFloat(), Color.parseColor("#FF3700B3")))

        mBarChart!!.startAnimation()

    }
}