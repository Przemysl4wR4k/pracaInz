package com.example.inz20.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.inz20.DBHandler
import com.example.inz20.R
import com.example.inz20.activities.MainActivity
import com.example.inz20.activities.ResetPasswordActivity
import com.example.inz20.adapter.BookInfo
import kotlinx.android.synthetic.main.fragment_right.*
import kotlinx.android.synthetic.main.item_view_pager.*

class RightFragments : Fragment(R.layout.fragment_right) {
    private lateinit var db: DBHandler

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = DBHandler(requireContext())

        btnTop.setOnClickListener {
            (activity as? MainActivity)?.logoutIfInBackground = false
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "image/*"
                startActivityForResult(it, 0)
            }
        }

        btnMid.setOnClickListener {
            (activity as? MainActivity)?.logoutIfInBackground = false
            startActivity(Intent(context, ResetPasswordActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 0) {
            val uri = data?.data
            imageView.setImageURI(uri)
        }
    }
}