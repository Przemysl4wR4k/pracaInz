package com.example.inz20.extensions

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inz20.R

fun RecyclerView.applyDivider() {
    ContextCompat.getDrawable(context, R.drawable.recycler_separator)?.let {
        val divider = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        divider.setDrawable(it)
        addItemDecoration(divider)
    }
}