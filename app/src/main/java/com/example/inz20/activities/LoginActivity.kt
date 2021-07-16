package com.example.inz20.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.inz20.DBHandler
import com.example.inz20.DTO.UserPassword
import com.example.inz20.R
import kotlinx.android.synthetic.main.activity_login.*

private const val PASSWORD = "123456"

class LoginActivity : AppCompatActivity() {
    private lateinit var db: DBHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        db = DBHandler(this)
        db.addPassword(UserPassword(password = PASSWORD))


        buttonLogin.setOnClickListener {
            val savedPass = db.getUserPassword()?.password
            val pass = etPassword.text.toString()

            if (pass == savedPass) startActivity(Intent(this, MainActivity::class.java))
            else Toast.makeText(this, "Złe hasło.", Toast.LENGTH_SHORT).show()
        }
    }
}