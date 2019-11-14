package com.example.egci428a20544

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.*

class SignUp : AppCompatActivity() {
    private var datasource: UsersDataSource? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // for back btn
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        datasource = UsersDataSource(this)
        datasource!!.open()

        var user: User? = null
        submitSignupBtn.setOnClickListener{

            // save the new comment to the database
            val username = usernameText.text.toString()
            val password = passwordText.text.toString()
            if(password.length<8){
                Toast.makeText(applicationContext, "Password is invalid", Toast.LENGTH_SHORT).show()
            }else if(datasource!!.checkDuplicate(username)!=0){
                Toast.makeText(applicationContext, "Username is invalid", Toast.LENGTH_SHORT).show()
            }else{
                user = datasource!!.createUser(username,password)
                Toast.makeText(applicationContext, "Successfully signup", Toast.LENGTH_SHORT).show()
                usernameText.setText("")
                passwordText.setText("")
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
