package com.example.chatfirebasetest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity: AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var mail: EditText
    private lateinit var pass: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.login_activity)

        auth = FirebaseAuth.getInstance();

        mail = findViewById(R.id.email)
        pass = findViewById(R.id.pass)

        findViewById<Button>(R.id.log).setOnClickListener {

            val email = mail.getText().toString()
            val password = pass.getText().toString()

            if(email == "" && password == ""){
                Toast.makeText(
                    baseContext, "Authentication failed.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                signIn(email, password)
            }
        }
    }

    private fun signIn(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    val user = auth.currentUser
                    val uid = user?.uid.toString()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("name", email)
                    intent.putExtra("uid", uid)
                    startActivity(intent)
                    finish()

                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }


            }
        // [END sign_in_with_email]
    }
}