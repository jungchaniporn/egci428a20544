package com.example.egci428a20544

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_show_all_users.*
import kotlinx.android.synthetic.main.row_main.view.*

class ShowAllUsers : AppCompatActivity() {
    private var datasource: UsersDataSource? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_all_users)
        // for back btn
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        for database
        datasource = UsersDataSource(this)
        datasource!!.open()

        val values = datasource!!.allUsers
        mainListView.adapter = myCustomAdapter(values,datasource!!)
//        datasource!!.close()
    }
    private class myCustomAdapter(userList:ArrayList<User>, datasource: UsersDataSource): BaseAdapter(){
//        private var datasource: UsersDataSource? = null
        private val uUserList:ArrayList<User> = userList
        private val dDataSource:UsersDataSource = datasource
        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
            val rowMain: View
            if (convertView == null){
                val layoutInflator  = LayoutInflater.from(viewGroup!!.context)
                rowMain = layoutInflator.inflate(R.layout.row_main, viewGroup, false)
                val viewHolder = ViewHolder(rowMain.userIdText, rowMain.userNameShowText)
                rowMain.tag = viewHolder
            } else {
                rowMain = convertView
            }
            val viewHolder = rowMain.tag as ViewHolder
            viewHolder.userIdText.text = "User id is: $position"
            viewHolder.userNameShowText.text = "User Name is:"+uUserList[position].username

            rowMain.setOnClickListener {
//                var user:User = uUserList[position]
                dDataSource!!.deleteUser(uUserList[position])
                Log.d("Deleted","Id:"+position)
                uUserList.remove(uUserList[position])
                notifyDataSetChanged()
            }
            return rowMain
        }
        private class ViewHolder(val userIdText: TextView, val userNameShowText: TextView)


        override fun getItem(position: Int): Any {
            return uUserList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            val count:Int = uUserList.size
            return count
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()
//        get abck to home page
        if(id == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}

