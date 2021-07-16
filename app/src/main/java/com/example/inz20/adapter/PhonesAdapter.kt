package com.example.inz20.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.inz20.DTO.PhoneItem
import com.example.inz20.DTO.ToDo
import com.example.inz20.R
import com.example.inz20.extensions.convertToDate

class PhonesAdapter(
    val list: MutableList<PhoneItem>,
    val phoneClicked: (PhoneItem) -> Unit,
    val menuClicked: (PhoneItem, ImageView) -> Unit,
) : RecyclerView.Adapter<PhonesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.rv_child_dashboard, parent, false)
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.phoneNumber.text = list[position].phoneNumber
        holder.phoneName.text = list[position].phoneName

        holder.phoneName.setOnClickListener {
            phoneClicked(list[position])
        }

        holder.menu.setOnClickListener {
            menuClicked(list[position], holder.menu)
        }
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val phoneNumber: TextView = v.findViewById(R.id.tv_todo_name)
        val phoneName: TextView = v.findViewById(R.id.tv_todo_date)
        val menu: ImageView = v.findViewById(R.id.iv_menu)
    }
}