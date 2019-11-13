package com.example.egci428a20544

class User {
    var id: Long = 0
    var username: String? = null
    var password: String? = null

    // Will be used by the ArrayAdapter in the ListView
    override fun toString(): String {
        return username.toString()
    }
}