package com.example.egci428a20544

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import java.sql.SQLException

class UsersDataSource(context: Context) {
//    for function add, edit, del

        // Database fields
        private var database: SQLiteDatabase? = null
        private val dbHelper: MySQLiteHelper
        private val allColumns = arrayOf<String>(MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_USERNAME)

        // make sure to close the cursor
        val allUsers: ArrayList<User>
            get() {
                val users = ArrayList<User>()

                val cursor = database!!.query(MySQLiteHelper.TABLE_USERS,
                    allColumns, null, null, null, null, null)
//            pointer point to 1st one
                cursor.moveToFirst()
                while (!cursor.isAfterLast) {
                    val user = cursorToUser(cursor)
                    users.add(user)
                    cursor.moveToNext()
                }
                cursor.close()
                return users
            }

        init {
//        do every first time
            dbHelper = MySQLiteHelper(context)
        }

        @Throws(SQLException::class)
        fun open() {
            database = dbHelper.getWritableDatabase()
        }

        fun close() {
            dbHelper.close()
        }

        fun createUser(username: String, password:String): User {
//        prepare value
            val values = ContentValues()
//        tell col that want to insert
            values.put(MySQLiteHelper.COLUMN_USERNAME, username)
            values.put(MySQLiteHelper.COLUMN_PASSWORD, password)
//        insert value in which table
            val insertId = database!!.insert(MySQLiteHelper.TABLE_USERS, null,
                values)
//        bring out new record
            val cursor = database!!.query(MySQLiteHelper.TABLE_USERS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null)
            cursor.moveToFirst()
//        keep in the form of Comment obj.
            val newUser = cursorToUser(cursor)
            cursor.close()
            return newUser
        }

        fun deleteUser(user: User) {
            val id = user.id
            database!!.delete(MySQLiteHelper.TABLE_USERS, MySQLiteHelper.COLUMN_ID
                    + " = " + id, null)
        }
        fun checkDuplicate(username: String): Int{
            val user = database!!.query(MySQLiteHelper.TABLE_USERS,
                allColumns, MySQLiteHelper.COLUMN_USERNAME + " = '$username'", null, null, null, null)
            val num = user.count
            return num
        }
        fun checkMatch(username: String,password: String): Int{
            val col_username = MySQLiteHelper.COLUMN_USERNAME
            val col_password = MySQLiteHelper.COLUMN_PASSWORD
            val user = database!!.query(MySQLiteHelper.TABLE_USERS,
                allColumns, "$col_username = '$username' AND $col_password='$password'", null, null, null, null)
            val num = user.count
            return num
        }
        private fun cursorToUser(cursor: Cursor): User {
            val user = User()
            user.id = cursor.getLong(0)
            user.username = cursor.getString(1)
            user.password = cursor.getString(1)
            return user
        }
    }