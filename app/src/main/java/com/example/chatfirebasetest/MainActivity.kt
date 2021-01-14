package com.example.chatfirebasetest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var uid: String = ""
    private var email: String = ""
    private var flag: Boolean = false

    private lateinit var tv1: TextView
    private lateinit var tv2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        load()

        if (uid == ""){

            if (intent.extras == null) {
                startActivity(Intent(this, RegisterActivity::class.java))
                val arguments = intent.extras
                email = arguments!!["name"].toString()
                uid = arguments["uid"].toString()
                flag = true
                save()
            } else {
                val arguments = intent.extras
                email = arguments!!["name"].toString()
                uid = arguments["uid"].toString()
            }

            tv1 = findViewById(R.id.text1)
            tv2 = findViewById(R.id.text2)

            tv1.text = email
            tv2.text = uid

        } else {

            tv1 = findViewById(R.id.text1)
            tv2 = findViewById(R.id.text2)

            tv1.text = email
            tv2.text = uid
        }







    }

    private fun save() {
        val sharedPreferences = getSharedPreferences("uid_save", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        flag = true
        editor.putString("uid", uid)
        editor.putString("name", email)
        editor.putBoolean("flag", flag)
        editor.apply()
    }

    private fun load() {
        val sharedPreferences = getSharedPreferences("uid_save", MODE_PRIVATE)
        uid = sharedPreferences.getString("uid", "").toString()
        email = sharedPreferences.getString("name", "").toString()
        flag = sharedPreferences.getBoolean("flag", flag)

    }




}

