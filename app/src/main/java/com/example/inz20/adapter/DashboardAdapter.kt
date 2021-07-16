package com.example.inz20.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.inz20.DTO.ToDo
import com.example.inz20.R
import com.example.inz20.extensions.convertToDate

class DashboardAdapter(
    val list: MutableList<ToDo>,
    val toDoNameClicked: (ToDo) -> Unit,
    val menuClicked: (ToDo, ImageView) -> Unit,
) : RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.rv_child_dashboard, parent, false)
    )

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.toDoName.text = list[position].name
        holder.toDoDate.text = list[position].createdAt.convertToDate()

        holder.toDoName.setOnClickListener {
            toDoNameClicked(list[position])
        }

        holder.menu.setOnClickListener {
            menuClicked(list[position], holder.menu)
        }
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val toDoName: TextView = v.findViewById(R.id.tv_todo_name)
        val toDoDate: TextView = v.findViewById(R.id.tv_todo_date)
        val menu: ImageView = v.findViewById(R.id.iv_menu)
    }
}