package com.example.inz20.activities

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.inz20.DBHandler
import com.example.inz20.DTO.PhoneItem
import com.example.inz20.R
import com.example.inz20.adapter.PhonesAdapter
import com.example.inz20.extensions.applyDivider
import kotlinx.android.synthetic.main.activity_dashboard.*

const val MY_PERMISSIONS_REQUEST_CALL_PHONE = 999

class PhonesActivity : AppCompatActivity() {

    lateinit var dbHandler: DBHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_phone)
        dbHandler = DBHandler(this)
        rv_dashboard.applyDivider()

        fab_dashboard.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("Dodaj numer telefonu")
            val view = layoutInflater.inflate(R.layout.dialog_phone_add, null)
            val textPhoneName = view.findViewById<EditText>(R.id.et_phone_name)
            val textPhoneNumber = view.findViewById<EditText>(R.id.et_phone_number)
            dialog.setView(view)
            dialog.setPositiveButton("Potwierdź") { _: DialogInterface, _: Int ->
                if (textPhoneName.text.isNotEmpty() && textPhoneNumber.text.isNotEmpty()) {
                    val phoneItem = PhoneItem(
                        phoneName = textPhoneName.text.toString(),
                        phoneNumber = textPhoneNumber.text.toString(),
                    )
                    Log.e("_TEST", phoneItem.toString())
                    dbHandler.addPhone(phoneItem)
                    refreshList()
                }
            }
            dialog.setNegativeButton("Anuluj") { dialogView: DialogInterface, _: Int ->
                dialogView.dismiss()
            }
            dialog.show()
        }

    }

    private fun updatePhone(phoneItem: PhoneItem) {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Edytuj Numer")
        val view = layoutInflater.inflate(R.layout.dialog_phone_add, null)
        val textPhoneName = view.findViewById<EditText>(R.id.et_phone_name)
        val textPhoneNumber = view.findViewById<EditText>(R.id.et_phone_number)

        textPhoneName.setText(phoneItem.phoneName)
        textPhoneNumber.setText(phoneItem.phoneNumber)

        dialog.setView(view)
        dialog.setPositiveButton("Potwierdź") { _: DialogInterface, _: Int ->
            if (textPhoneName.text.isNotEmpty() && textPhoneNumber.text.isNotEmpty()) {

                phoneItem.phoneName = textPhoneName.text.toString()
                phoneItem.phoneNumber = textPhoneNumber.text.toString()

                dbHandler.updatePhone(phoneItem)
                refreshList()
            }
        }
        dialog.setNegativeButton("Anuluj") { dialogView: DialogInterface, _: Int ->
            dialogView.dismiss()
        }
        dialog.show()
    }

    override fun onResume() {
        refreshList()
        super.onResume()
    }

    private fun refreshList() {
        rv_dashboard.adapter = PhonesAdapter(
            dbHandler.getPhones(),
            phoneClicked = { phone ->
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:${phone.phoneNumber}")
                if (phonePermissionGranted()) ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    MY_PERMISSIONS_REQUEST_CALL_PHONE
                )
                else startActivity(callIntent)
            },
            menuClicked = { phone, image ->
                val popup = PopupMenu(this, image)

                popup.inflate(R.menu.dashboard_child)
                popup.setOnMenuItemClickListener {

                    when (it.itemId) {
                        R.id.menu_edit -> updatePhone(phone)
                        R.id.menu_delete -> {
                            val dialog = AlertDialog.Builder(this)
                            dialog.setTitle("Czy na pewno?")
                            dialog.setMessage("Czy na pewno chcesz usunąć ten numer??")
                            dialog.setPositiveButton("Potwierdź") { _: DialogInterface, _: Int ->
                                dbHandler.deletePhone(phone.id)
                                refreshList()
                            }
                            dialog.setNegativeButton("Anuluj") { dialogView: DialogInterface, _: Int ->
                                dialogView.dismiss()
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

    private fun phonePermissionGranted() = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.CALL_PHONE
    ) != PackageManager.PERMISSION_GRANTED
}
