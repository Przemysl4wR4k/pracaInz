package com.example.inz20.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.inz20.DBHandler
import com.example.inz20.R
import kotlinx.android.synthetic.main.activity_reset_password.*

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var db: DBHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        db = DBHandler(this)

        buttonResetPassword.setOnClickListener {
            validatePasswords()
        }
    }

    private fun validatePasswords() {
        val currentPassword = etPasswordCurrent.text.toString()
        val savedPassword = db.getUserPassword()?.password

        val newPassword = etPasswordNew.text.toString()
        val newPasswordReenter = etPasswordNewReenter.text.toString()

        when {
            currentPassword.isEmpty() -> etPasswordCurrent.error = "Podaj hasło."
            currentPassword != savedPassword -> etPasswordCurrent.error = "Obecne hasło jest złe."
            newPassword.isEmpty() -> etPasswordNew.error = "Podaj hasło."
            newPassword == currentPassword -> etPasswordNew.error = "Nowe hasło jest takie samo jak stare."
            newPasswordReenter.isEmpty() -> etPasswordNewReenter.error = "Podaj hasło."
            newPassword != newPasswordReenter -> etPasswordNewReenter.error = "Nowe hasła są różne."
            else -> {
                db.updatePassword(newPassword)
                finish()
            }
        }
    }
}