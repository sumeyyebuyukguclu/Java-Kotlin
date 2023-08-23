package com.sumeyyebuyukguclu.engelsizsehr

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// DBHelper.kt
class DBHelper(context: Context) : SQLiteOpenHelper(context, "user_credentials.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)")
    }

    fun checkUserAlreadyHas(email: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM users WHERE username = '$email'"

        val cursor = db.rawQuery(query, null)

        val result = cursor.count > 0

        cursor.close()
        db.close()

        println(result)
        return result
    }

    fun checkUser(email: String, password: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM users WHERE username = '$email' AND password = '$password'"

        val cursor = db.rawQuery(query, null)

        val result = cursor.count > 0

        cursor.close()
        db.close()

        println(result)
        return result
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Upgrade logic if needed
    }

    fun addUser(username: String, password: String): Boolean {
        val values = ContentValues()
        values.put("username", username)
        values.put("password", password)

        val newRowId = writableDatabase.insert("users", null, values)
        return newRowId != -1L // Return true if insertion was successful
    }

    // Add other database methods here
}
