package com.example.egci428a20544

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class MySQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    //    for create and connect database
    override fun onCreate(database: SQLiteDatabase) {
        database.execSQL(DATABASE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.w(MySQLiteHelper::class.java!!.name,
            "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data")
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS)
        onCreate(db)
    }

    companion object {

        val TABLE_USERS= "users"
        val COLUMN_ID = "id"
        val COLUMN_USERNAME = "username"
        val COLUMN_PASSWORD = "password"

        private val DATABASE_NAME = "users.db"
        private val DATABASE_VERSION = 1

        // Database creation sql statement
        private val DATABASE_CREATE = ("create table "
                + TABLE_USERS + "("
                + COLUMN_ID + " integer primary key autoincrement, "
                + COLUMN_USERNAME + " text not null,"
                + COLUMN_PASSWORD + " text not null);")
    }
}