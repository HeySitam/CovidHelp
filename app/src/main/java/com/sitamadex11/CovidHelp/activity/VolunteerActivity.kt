package com.sitamadex11.CovidHelp.activity

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import com.sitamadex11.CovidHelp.R
import com.sitamadex11.CovidHelp.fragments.ViewVolunteerFragment

class VolunteerActivity : AppCompatActivity() {
    lateinit var json: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_volunteer)
        val fragment = ViewVolunteerFragment()
        fragmentTransaction(fragment)
    }

    private fun fragmentTransaction(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.flVolunteer, fragment).commit()
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
}
