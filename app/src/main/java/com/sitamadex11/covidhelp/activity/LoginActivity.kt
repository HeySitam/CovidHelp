package com.sitamadex11.covidhelp.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.sitamadex11.covidhelp.R
import kotlinx.android.synthetic.main.glass_signup_page.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    var chk: Boolean = true
    private val TAG = "logInStatus"
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.glass_signup_page)
        init()
        clickHandle()

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


    private fun init() {
        txtLogin.text = "Sign In"
        cvUserName.visibility = View.GONE
        auth = Firebase.auth
    }

    private fun clickHandle() {
        txtSignUpNow.setOnClickListener(this)
        btnLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.txtSignUpNow -> {
                if (chk) {
                    txtLogin.text = "Sign Up"
                    cvUserName.visibility = View.VISIBLE
                    btnLogin.text = "Sign Up"
                    txtForgotPassword.visibility = View.GONE
                    txtMember.text = "Already a member?"
                    txtSignUpNow.text = "Sign in now"
                    chk = false
                } else {
                    txtLogin.text = "Sign In"
                    cvUserName.visibility = View.GONE
                    btnLogin.text = "Log In"
                    txtForgotPassword.visibility = View.VISIBLE
                    txtMember.text = "Not a member?"
                    txtSignUpNow.text = "Sign up now"
                    chk = true
                }
            }
            R.id.btnLogin -> {
                if (!chk) {
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
                                    baseContext, "Authentication failed.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }else{
                    auth.signInWithEmailAndPassword(etUserEmail.text.toString(), etUserPassword.text.toString())
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success")
                                val user = auth.currentUser
                                startActivity(Intent(this, ChooseActivity::class.java))
                                finish()
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.exception)
                                Toast.makeText(this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show()
                            }
                        }

                }
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

        FirebaseFirestore.getInstance().collection("users")
            .add(user)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Data Inserted", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, ChooseActivity::class.java))
                    finish()
                } else {
                    auth.currentUser!!.delete()
                    Toast.makeText(this,"Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}