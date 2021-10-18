Skip to content
Search or jump to…
Pull requests
Issues
Marketplace
Explore
 
@Paulraj-dev 
sitamadex11
/
CovidHelp
Public
1
410
Code
Issues
12
Pull requests
1
Actions
Projects
Wiki
Security
Insights
CovidHelp/app/src/main/java/com/sitamadex11/CovidHelp/activity/CovidTrackerActivity.kt
@sitamadex11
sitamadex11 updated for hackoctober fest
Latest commit 4352b13 3 days ago
 History
 1 contributor
217 lines (201 sloc)  8.54 KB
   
package com.sitamadex11.CovidHelp.activity

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.material.button.MaterialButton
import com.sitamadex11.CovidHelp.R
import com.sitamadex11.CovidHelp.covidTrackerApi.CovidData
import com.sitamadex11.CovidHelp.covidTrackerApi.Data
import com.sitamadex11.CovidHelp.covidTrackerApi.Regional
import com.sitamadex11.CovidHelp.util.ApiUtilities
import com.sitamadex11.CovidHelp.worker.CovidTrackerWorker
import org.eazegraph.lib.charts.BarChart
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.BarModel
import org.eazegraph.lib.models.PieModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class CovidTrackerActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private var mRecovered: TextView? = null
    private var mDeath: TextView? = null
    private var mActive: TextView? = null
    private var mpieChart: PieChart? = null
    private var mSelectState: Spinner? = null
    private var mSelectedStateName: String? = null
    private var mData: Data? = null
    private var mBarChart: BarChart? = null
    lateinit var btnRetry: MaterialButton
    lateinit var btnExit: MaterialButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isConnected = checkConnectivity(this)
        if (!isConnected) {
            val customLayout = layoutInflater
                .inflate(
                    R.layout.network_check_dialog, null
                )
            msgInit(customLayout)
            val builder = AlertDialog.Builder(this)
            builder.setView(customLayout)
            builder.setCancelable(false)
            val dialog = builder.create()
            btnExit.setOnClickListener {
                finish()
            }
            btnRetry.setOnClickListener {
                if (checkConnectivity(this)) {
                    //Do some thing
                    dialog.hide()
                    setContentView(R.layout.covid_tracker_activity)
                    init()
                    val mState_names = resources.getStringArray(R.array.states_name)
                    mSelectState!!.onItemSelectedListener = this
                    val adapter: ArrayAdapter<*> =
                        ArrayAdapter<Any?>(this, R.layout.spinner_item, mState_names)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    mSelectState!!.adapter = adapter
                    ApiUtilities.apiInterface.covidData!!.enqueue(object : Callback<CovidData?> {
                        override fun onResponse(
                            call: Call<CovidData?>,
                            response: Response<CovidData?>
                        ) {
                            val covidData = response.body()
                            mData = covidData!!.data
                            UpdateUI(mData)
                        }

                        override fun onFailure(call: Call<CovidData?>, t: Throwable) {
                            Toast.makeText(
                                applicationContext,
                                "Error : " + t.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                    initWorker()
                } else {
                    Toast.makeText(this, "Sorry!! No Internet connection found", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            dialog.show()
        } else {
            //Do some thing
            setContentView(R.layout.covid_tracker_activity)
            init()
            val mState_names = resources.getStringArray(R.array.states_name)
            mSelectState!!.onItemSelectedListener = this
            val adapter: ArrayAdapter<*> =
                ArrayAdapter<Any?>(this, R.layout.spinner_item, mState_names)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            mSelectState!!.adapter = adapter
            ApiUtilities.apiInterface.covidData!!.enqueue(object : Callback<CovidData?> {
                override fun onResponse(call: Call<CovidData?>, response: Response<CovidData?>) {
                    val covidData = response.body()
                    mData = covidData!!.data
                    UpdateUI(mData)
                }

                override fun onFailure(call: Call<CovidData?>, t: Throwable) {
                    Toast.makeText(applicationContext, "Error : " + t.message, Toast.LENGTH_SHORT)
                        .show()
                }
            })
            initWorker()
        }
    }

    private fun init() {
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
        mpieChart!!.addPieSlice(
            PieModel(
                "Recoverd",
                discharged.toFloat(),
                Color.parseColor("#FFFF00")
            )
        )
        mpieChart!!.addPieSlice(PieModel("Active", total.toFloat(), Color.parseColor("#FF3700B3")))
        mpieChart!!.addPieSlice(PieModel("Death", deaths.toFloat(), Color.parseColor("#F44336")))
        mpieChart!!.startAnimation()
        mBarChart!!.clearChart()
        mBarChart!!.addBar(BarModel("Death", deaths.toFloat(), Color.parseColor("#F44336")))
        mBarChart!!.addBar(BarModel("Recovered", discharged.toFloat(), Color.parseColor("#FFFF00")))
        mBarChart!!.addBar(BarModel("Active", total.toFloat(), Color.parseColor("#FF3700B3")))
        mBarChart!!.startAnimation()
    }

    private fun initWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val notificationWorkRequest =
            OneTimeWorkRequestBuilder<CovidTrackerWorker>()
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(applicationContext).enqueue(
            notificationWorkRequest
        )
    }

    private fun msgInit(v: View?) {
        btnExit = v!!.findViewById(R.id.btnExit)
        btnRetry = v.findViewById(R.id.btnRetry)
    }


    fun checkConnectivity(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork?.isConnected != null) {
            return activeNetwork.isConnected
        } else {
            return false
        }
    }
}
© 2021 GitHub, Inc.
Terms
Privacy
Security
Status
Docs
Contact GitHub
Pricing
API
Training
Blog
About
Loading complete
