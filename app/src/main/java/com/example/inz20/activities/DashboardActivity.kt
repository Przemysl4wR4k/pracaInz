package com.example.inz20.activities

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.inz20.DBHandler
import com.example.inz20.DTO.ToDo
import com.example.inz20.INTENT_TODO_ID
import com.example.inz20.INTENT_TODO_NAME
import com.example.inz20.R
import com.example.inz20.adapter.DashboardAdapter
import com.example.inz20.extensions.applyDivider
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    lateinit var dbHandler: DBHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        dbHandler = DBHandler(this)
        rv_dashboard.applyDivider()

        fab_dashboard.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("Tytuł notatki")
            val view = layoutInflater.inflate(R.layout.dialog_dashboard, null)
            val toDoName = view.findViewById<EditText>(R.id.ev_todo)
            dialog.setView(view)
            dialog.setPositiveButton("Stwórz") { _: DialogInterface, _: Int ->
                if (toDoName.text.isNotEmpty()) {
                    val toDo = ToDo(name = toDoName.text.toString())
                    dbHandler.addToDo(toDo)
                    refreshList()
                }
            }
            dialog.setNegativeButton("Anuluj") { _: DialogInterface, _: Int ->
            }
            dialog.show()
        }

    }

    fun updateToDo(toDo: ToDo) {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Tytuł notatki")
        val view = layoutInflater.inflate(R.layout.dialog_dashboard, null)
        val toDoName = view.findViewById<EditText>(R.id.ev_todo)
        toDoName.setText(toDo.name)
        dialog.setView(view)
        dialog.setPositiveButton("Zmień") { _: DialogInterface, _: Int ->
            if (toDoName.text.isNotEmpty()) {
                toDo.name = toDoName.text.toString()
                dbHandler.updateToDo(toDo)
                refreshList()
            }
        }
        dialog.setNegativeButton("Anuluj") { _: DialogInterface, _: Int ->

        }
        dialog.show()
    }

    override fun onResume() {
        refreshList()
        super.onResume()
    }

    private fun refreshList() {
        rv_dashboard.adapter = DashboardAdapter(
            dbHandler.getToDos(),
            toDoNameClicked = { ToDo ->
                val intent = Intent(this, ItemActivity::class.java)
                intent.putExtra(INTENT_TODO_ID, ToDo.id)
                intent.putExtra(INTENT_TODO_NAME, ToDo.name)
                startActivity(intent)
            },
            menuClicked = { ToDo, image ->
                val popup = PopupMenu(this, image)

                popup.inflate(R.menu.dashboard_child)
                popup.setOnMenuItemClickListener {

                    when (it.itemId) {
                        R.id.menu_edit -> updateToDo(ToDo)
                        R.id.menu_delete -> {
                            val dialog = AlertDialog.Builder(this)
                            dialog.setTitle("Czy jesteś pewny?")
                            dialog.setMessage("Czy na pewno chcesz usunąć te notatkę?")
                            dialog.setPositiveButton("Potwierdź") { _: DialogInterface, _: Int ->
                                dbHandler.deleteToDo(ToDo.id)
                                refreshList()
                            }
                            dialog.setNegativeButton("Anuluj") { _: DialogInterface, _: Int ->

                            }
                            dialog.show()
                        }
                    }

                    true
                }
                popup.show()
            }
        )
    }
}
