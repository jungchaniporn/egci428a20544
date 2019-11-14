package com.example.egci428a20544

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignIn : AppCompatActivity() {
    private var datasource: UsersDataSource? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
// for back btn
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        datasource = UsersDataSource(this)
        datasource!!.open()

        var user: User? = null
        submitSigninBtn.setOnClickListener{
            val username = uNameText.text.toString()
            val password = passWordText.text.toString()
            val condition = datasource!!.checkMatch(username,password)
            if (condition!=0){
                Toast.makeText(applicationContext, "Login...", Toast.LENGTH_SHORT).show()
                uNameText.setText("")
                passWordText.setText("")
                val intent = Intent(this,LoggedIn::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(applicationContext, "Login error", Toast.LENGTH_SHORT).show()
                uNameText.setText("")
                passWordText.setText("")
            }

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
