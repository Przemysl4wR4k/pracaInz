package com.example.inz20

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.inz20.DTO.PhoneItem
import com.example.inz20.DTO.ToDo
import com.example.inz20.DTO.UserPassword
import com.example.inz20.adapter.BookInfo

class DBHandler(val context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val createToDoTable = "  CREATE TABLE $TABLE_TODO (" +
                "$COL_ID integer PRIMARY KEY AUTOINCREMENT," +
                "$COL_CREATED_AT datetime DEFAULT CURRENT_TIMESTAMP," +
                "$COL_TEXT varchar," +
                "$COL_NAME varchar);"
        val createPhoneTable =
            "CREATE TABLE $TABLE_PHONE (" +
                    "$COL_PHONE_ID integer PRIMARY KEY AUTOINCREMENT," +
                    "$COL_PHONE_NAME varchar," +
                    "$COL_PHONE_NUMBER varchar);"
        val createUserTable =
            "CREATE TABLE $TABLE_USER (" +
                    "$COL_USER_ID integer PRIMARY KEY AUTOINCREMENT," +
                    "$COL_USER_PASS varchar);"
        val createBookTable =
            "CREATE TABLE $TABLE_BOOK (" +
                    "$COL_BOOK_ID integer PRIMARY KEY AUTOINCREMENT," +
                    "$COL_BOOK_TITLE varchar," +
                    "$COL_BOOK_DESC varchar," +
                    "$COL_BOOK_IMAGE varchar);"

        db.execSQL(createToDoTable)
        db.execSQL(createPhoneTable)
        db.execSQL(createUserTable)
        db.execSQL(createBookTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun addPassword(userPassword: UserPassword): Boolean {
        val savedPassword = readableDatabase.rawQuery("SELECT * from $TABLE_USER", null)
        if (savedPassword.count > 0)  {
            savedPassword.close()
            return false
        }
        savedPassword.close()
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COL_USER_PASS, userPassword.password)
        val result = db.insert(TABLE_USER, null, cv)
        return result != (-1).toLong()
    }

    fun updatePassword(newPassword: String) {
        val currentPasswordId = getUserPassword()?.id
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COL_USER_PASS, newPassword)
        db.update(TABLE_USER, cv, "$COL_ID=?", arrayOf(currentPasswordId.toString()))
    }

    fun getUserPassword(): UserPassword? {
        val db = readableDatabase
        val queryResult = db.rawQuery("SELECT * from $TABLE_USER", null)
        queryResult.moveToFirst()
        return if (queryResult.count > 0) {
            val id = queryResult.getLong(queryResult.getColumnIndex(COL_USER_ID))
            val password = queryResult.getString(queryResult.getColumnIndex(COL_USER_PASS))
            UserPassword(id, password)
        } else {
            null
        }.also { queryResult.close() }
    }

    fun addToDo(toDo: ToDo): Boolean {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COL_NAME, toDo.name)
        cv.put(COL_TEXT, toDo.text)
        cv.put(COL_CREATED_AT, toDo.createdAt)
        Log.e("_TEST_ADD", toDo.toString())
        val result = db.insert(TABLE_TODO, null, cv)
        return result != (-1).toLong()
    }

    fun updateToDo(toDo: ToDo) {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COL_NAME, toDo.name)
        db.update(TABLE_TODO, cv, "$COL_ID=?", arrayOf(toDo.id.toString()))
    }

    fun updateToDoText(text: String, id: String) {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COL_TEXT, text)
        db.update(TABLE_TODO, cv, "$COL_ID=?", arrayOf(id))
    }

    fun deleteToDo(todoId: Long) {
        val db = writableDatabase
        db.delete(TABLE_TODO, "$COL_ID=?", arrayOf(todoId.toString()))
    }

    fun getToDos(): MutableList<ToDo> {
        val result: MutableList<ToDo> = ArrayList()
        val db = readableDatabase
        val queryResult = db.rawQuery("SELECT * from $TABLE_TODO", null)
        if (queryResult.moveToFirst()) {
            do {
                val todo = ToDo()
                todo.id = queryResult.getLong(queryResult.getColumnIndex(COL_ID))
                todo.name = queryResult.getString(queryResult.getColumnIndex(COL_NAME))
                todo.text = queryResult.getString(queryResult.getColumnIndex(COL_TEXT))
                todo.createdAt = queryResult.getLong(queryResult.getColumnIndex(COL_CREATED_AT))
                Log.e("_TEST_GET", todo.toString())
                result.add(todo)
            } while (queryResult.moveToNext())
        }
        queryResult.close()
        Log.e("_TEST", result.toString())
        return result
    }


    fun addPhone(phoneItem: PhoneItem): Boolean {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COL_PHONE_NUMBER, phoneItem.phoneNumber)
        cv.put(COL_PHONE_NAME, phoneItem.phoneName)
        val result = db.insert(TABLE_PHONE, null, cv)
        return result != (-1).toLong()
    }

    fun updatePhone(phoneItem: PhoneItem) {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COL_PHONE_NUMBER, phoneItem.phoneNumber)
        cv.put(COL_PHONE_NAME, phoneItem.phoneName)
        db.update(TABLE_PHONE, cv, "$COL_PHONE_ID=?", arrayOf(phoneItem.id.toString()))
    }

    fun deletePhone(phoneId: Long) {
        val db = writableDatabase
        db.delete(TABLE_PHONE, "$COL_PHONE_ID=?", arrayOf(phoneId.toString()))
    }

    fun getPhones(): MutableList<PhoneItem> {
        val result: MutableList<PhoneItem> = ArrayList()
        val db = readableDatabase
        val queryResult = db.rawQuery("SELECT * from $TABLE_PHONE", null)
        Log.e("_TEST", queryResult.getColumnIndex(COL_PHONE_ID).toString())
        if (queryResult.moveToFirst()) {
            do {
                val item = PhoneItem(
                    queryResult.getLong(queryResult.getColumnIndex(COL_PHONE_ID)),
                    queryResult.getString(queryResult.getColumnIndex(COL_PHONE_NAME)),
                    queryResult.getString(queryResult.getColumnIndex(COL_PHONE_NUMBER)),
                )
                result.add(item)
            } while (queryResult.moveToNext())
        }
        queryResult.close()
        Log.e("_TEST", result.toString())
        return result
    }
}