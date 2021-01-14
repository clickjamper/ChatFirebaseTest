package com.example.chatfirebasetest

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TableLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity: AppCompatActivity() {

    private var uid: String = ""
    private var email: String = ""
    private var flag: Boolean = false

    private lateinit var auth: FirebaseAuth
    private lateinit var mail: EditText
    private lateinit var pass: EditText
    private lateinit var pass2: EditText

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.register_activity)

        auth = FirebaseAuth.getInstance();

        mail = findViewById(R.id.email)
        pass = findViewById(R.id.pass)
        pass2 = findViewById(R.id.pass2)

        findViewById<Button>(R.id.reg).setOnClickListener {
            val email = mail.getText().toString()
            val password = pass.getText().toString()
            val password2 = pass2.getText().toString()

            if ((password == password2) && (email != "") && (password != "")) {
                //save()
                createAccount(email, password)
            }
            else {
                Toast.makeText(
                    this,
                    "Пароли не совпадают",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        findViewById<Button>(R.id.log).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
    }

    override fun onBackPressed() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        super.onBackPressed()
    }



    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    uid = user?.uid.toString()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("name", email)
                    intent.putExtra("uid", uid)
                    startActivity(intent)
                    finish()

                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(
                        baseContext,
                        "Данный аккаунт уже зарегестрирован, создайте новый",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
    }
}