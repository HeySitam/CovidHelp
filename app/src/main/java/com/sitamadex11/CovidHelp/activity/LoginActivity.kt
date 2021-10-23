package com.sitamadex11.CovidHelp.activity

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.sitamadex11.CovidHelp.R
import com.sitamadex11.CovidHelp.model.District
import com.sitamadex11.CovidHelp.model.DistrictItems
import com.sitamadex11.CovidHelp.model.State
import com.sitamadex11.CovidHelp.util.Constants
import kotlinx.android.synthetic.main.glass_signup_page.*


class LoginActivity : AppCompatActivity(), View.OnClickListener {
    var chk: Boolean = true
    private val TAG = "logInStatus"
    lateinit var btnRetry: MaterialButton
    lateinit var btnExit: MaterialButton
    private lateinit var auth: FirebaseAuth
    val state_list = java.util.ArrayList<String>()
    val district_list = java.util.ArrayList<DistrictItems>()
    val district_name_list = java.util.ArrayList<String>()
    lateinit var requestOueue: RequestQueue

    // google signin client
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
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
                    setContentView(R.layout.glass_signup_page)
                    init()
                    clickHandle()
                    stateJsonParse()
                } else {
                    Toast.makeText(this, "Sorry!! No Internet connection found", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            dialog.show()
        } else {
            //Do some thing
            setContentView(R.layout.glass_signup_page)
            init()
            clickHandle()
            stateJsonParse()
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, ChooseActivity::class.java))
            finish()
        }
    }

    private fun msgInit(v: View?) {
        btnExit = v!!.findViewById(R.id.btnExit)
        btnRetry = v.findViewById(R.id.btnRetry)
    }

    private fun init() {
        txtLogin.text = "Sign In"
        cvUserName.visibility = View.GONE
        llUser.visibility = View.GONE
        cvPhone.visibility = View.GONE
        auth = Firebase.auth
        requestOueue = Volley.newRequestQueue(this)

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.server_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun clickHandle() {
        txtSignUpNow.setOnClickListener(this)
        btnLogin.setOnClickListener(this)
        txtForgotPassword.setOnClickListener(this)
        // google signin button
        btnGoogleSignIn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.txtSignUpNow -> {
                if (chk) {
                    txtLogin.text = "Sign Up"
                    cvUserName.visibility = View.VISIBLE
                    btnLogin.text = "Sign Up"
                    txtForgotPassword.visibility = View.GONE
                    cvPhone.visibility = View.VISIBLE
                    txtMember.text = "Already a member?"
                    txtSignUpNow.text = "Sign in now"
                    llUser.visibility = View.VISIBLE
                    chk = false
                } else {
                    txtLogin.text = "Sign In"
                    cvUserName.visibility = View.GONE
                    btnLogin.text = "Log In"
                    cvPhone.visibility = View.GONE
                    txtForgotPassword.visibility = View.VISIBLE
                    txtMember.text = "Not a member?"
                    txtSignUpNow.text = "Sign up now"
                    llUser.visibility = View.GONE
                    chk = true
                }
            }
            R.id.btnLogin -> {
                if (!chk) {
                    if (etUserName.text!!.isNotEmpty()
                        && etUserEmail.text!!.isNotEmpty()
                        && etUserPhone.text!!.isNotEmpty()
                        && etUserPassword.text!!.isNotEmpty()
                        && etUserState.text!!.isNotEmpty()
                        && etUserDistrict.text!!.isNotEmpty()
                    ) {
                        auth.createUserWithEmailAndPassword(
                            etUserEmail.text.toString(),
                            etUserPassword.text.toString()
                        )
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success")
                                    val user = auth.currentUser
                                    addDetails()
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                                    Toast.makeText(
                                        baseContext,
                                        "Either User already exist or check your Internet connection",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        etUserName.error = null
                        etUserEmail.error = null
                        etUserPhone.error = null
                        etUserPassword.error = null
                        etUserState.error = null
                        etUserDistrict.error = null
                    }
                    if (etUserName.text.isNullOrEmpty()) {
                        etUserName.error = "Your name can't be empty."
                    }
                    if (etUserEmail.text.isNullOrEmpty()) {
                        etUserEmail.error = "Your email can't be empty."
                    }
                    if (etUserPhone.text.isNullOrEmpty()) {
                        etUserPhone.error = "Your phone number can't be empty."
                    }
                    if (etUserPassword.text.isNullOrEmpty()) {
                        etUserPassword.error = "Your password can't be empty."
                    }
                    if (etUserState.text.isNullOrEmpty()) {
                        etUserState.error = "Your State can't be empty."
                    }
                    if (etUserDistrict.text.isNullOrEmpty()) {
                        etUserDistrict.error = "Your district can't be empty."
                    }
                } else {
                    etUserName.error = null
                    etUserEmail.error = null
                    etUserPhone.error = null
                    etUserPassword.error = null
                    etUserState.error = null
                    etUserDistrict.error = null
                    if (etUserEmail.text!!.isNotEmpty()
                        && etUserPassword.text!!.isNotEmpty()
                    ) {
                        etUserEmail.error = null
                        etUserPassword.error = null
                        auth.signInWithEmailAndPassword(
                            etUserEmail.text.toString(),
                            etUserPassword.text.toString()
                        )
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success")
                                    val user = auth.currentUser
                                    startActivity(Intent(this, ChooseActivity::class.java))
                                    Toast.makeText(this, "Welcome Back", Toast.LENGTH_SHORT).show()
                                    finish()
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                                    Toast.makeText(
                                        this,
                                        "Failed!! Check your UserName, Password or Internet Connectivity",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    }
                    if (etUserEmail.text.isNullOrEmpty()) {
                        etUserEmail.error = "Your email can't be empty."
                    }
                    if (etUserPassword.text.isNullOrEmpty()) {
                        etUserPassword.error = "Your password can't be empty."
                    }
                }
            }
            R.id.txtForgotPassword -> {
                if (etUserEmail.text.isNullOrEmpty()) {
                    etUserEmail.error = "Please enter a valid email"
                } else {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(etUserEmail.text.toString())
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    this, "an Verification Mail has been sent",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }.addOnFailureListener {
                            Toast.makeText(
                                this, "can't send an Verification Mail",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }
            }
            R.id.btnGoogleSignIn -> {
                signIn()
            }
        }
    }

    private fun addDetails() {
        val userProfileChangeRequest = UserProfileChangeRequest.Builder()
            .setDisplayName("${etUserName.text}")
            .build()

        auth.currentUser!!.updateProfile(userProfileChangeRequest)

        val user: HashMap<String, String> = HashMap()
        user["uid"] = auth.currentUser!!.uid
        user["UserName"] = etUserName.text.toString()
        user["Email"] = etUserEmail.text.toString()
        user["isVol"] = "0"
        user["address"] = "Not Provided"
        user["state"] = etUserState.text.toString()
        user["district"] = etUserDistrict.text.toString()
        user["phone"] = etUserPhone.text.toString()

        FirebaseFirestore.getInstance().collection("users")
            .add(user)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(
                        this,
                        "Your account is created successfully.",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this, ChooseActivity::class.java))
                    finish()
                } else {
                    auth.currentUser!!.delete()
                    Toast.makeText(
                        this, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun stateJsonParse() {
        val url = Constants.STATE_URL
        val stateString = StringRequest(url, { str ->
            val state_class = Gson().fromJson(str, State::class.java)
            for (i in state_class.states.indices) {
                state_list.add(state_class.states[i].state_name)
            }
            val stateAdapter =
                ArrayAdapter(this, R.layout.dropdown_item, state_list)
            etUserState.setAdapter(stateAdapter)
            etUserState.setOnItemClickListener { _, _, position, _ ->
                Toast.makeText(this, state_list[position], Toast.LENGTH_SHORT).show()
                etUserDistrict.isEnabled = true
                district_list.clear()
                district_name_list.clear()
                districtJsonParse(position)
                etUserState.error = null
            }
            Log.d("chk_state", str)
        }, {
            Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show()
        })
        requestOueue.add(stateString)
    }

    private fun districtJsonParse(position: Int) {
        val url = "${Constants.DISTRICT_URL}${position}"
        Log.d("chk_url", url)
        val districtString = StringRequest(url, { str ->
            val district_class = Gson().fromJson(str, District::class.java)
            for (i in district_class.districts!!.indices) {
                district_list.add(district_class.districts[i]!!)
                district_name_list.add(district_class.districts[i]!!.district_name!!)
            }
            val stateAdapter =
                ArrayAdapter(this, R.layout.dropdown_item, district_name_list)
            etUserDistrict.apply {
                setAdapter(stateAdapter)
            }
            Log.d("chk_state", str)
            etUserDistrict.setOnItemClickListener { parent, view, position, id ->
                etUserDistrict.error = null
            }
        }, {
            Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show()
        })
        requestOueue.add(districtString)
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

    // this  lets keyboard close when clicked in background
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_UP) {
            val view = currentFocus
            if (view != null) {
                val consumed = super.dispatchTouchEvent(ev)
                val viewTmp = currentFocus
                val viewNew: View = viewTmp ?: view
                if (viewNew == view) {
                    val rect = Rect()
                    val coordinates = IntArray(2)
                    view.getLocationOnScreen(coordinates)
                    rect[coordinates[0], coordinates[1], coordinates[0] + view.width] =
                        coordinates[1] + view.height
                    val x = ev.x.toInt()
                    val y = ev.y.toInt()
                    if (rect.contains(x, y)) {
                        return consumed
                    }
                } else {
                    return consumed
                }
                val inputMethodManager =
                    getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(viewNew.windowToken, 0)
                viewNew.clearFocus()
                return consumed
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credentials = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credentials)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser

                    if (!chk) {
                        addDetails()
                    } else {
                        startActivity(Intent(this, ChooseActivity::class.java))
                        Toast.makeText(this, "Welcome Back", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        this,
                        "Failed!! Check your UserName, Password or Internet Connectivity",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    companion object {
        private const val RC_SIGN_IN = 9001
    }
}