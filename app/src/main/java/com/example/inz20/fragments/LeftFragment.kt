package com.example.inz20.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.inz20.R
import com.example.inz20.activities.BooksActivity
import com.example.inz20.activities.DashboardActivity
import com.example.inz20.activities.MainActivity
import com.example.inz20.activities.PhonesActivity
import kotlinx.android.synthetic.main.fragment_left.*

class LeftFragment : Fragment(R.layout.fragment_left) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnTopLeft.setOnClickListener {
            (activity as? MainActivity)?.logoutIfInBackground = false
            val intent = Intent(context, DashboardActivity::class.java)
            startActivity(intent)
        }

        btnTopRight.setOnClickListener {
            (activity as? MainActivity)?.logoutIfInBackground = false
            val intent = Intent(context, PhonesActivity::class.java)
            startActivity(intent)
        }

        btnBottomLeft.setOnClickListener {
            Log.e("_TEST", "BOTTOM_LEFT")
            (activity as? MainActivity)?.logoutIfInBackground = false
            val intent = Intent(context, BooksActivity::class.java)
            startActivity(intent)
        }
    }
}