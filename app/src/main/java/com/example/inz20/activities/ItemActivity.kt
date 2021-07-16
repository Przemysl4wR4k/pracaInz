package com.example.inz20.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.inz20.DBHandler
import com.example.inz20.INTENT_TODO_ID
import com.example.inz20.R
import kotlinx.android.synthetic.main.activity_item.*

class ItemActivity : AppCompatActivity() {

    lateinit var dbHandler: DBHandler
    var todoId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
        todoId = intent.getLongExtra(INTENT_TODO_ID, -1)
        dbHandler = DBHandler(this)

        save_item.setOnClickListener {
            updateItem(edit_item.text.toString(), todoId.toString())
        }
    }

    private fun updateItem(text: String, id: String) {
        Log.e("_TEST", "Text: $text, id: $id")
        dbHandler.updateToDoText(text, id)
    }
}
