package com.example.ecommerceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class Signup : AppCompatActivity() {

    lateinit var signup_email: EditText
    lateinit var signup_pass: EditText
    lateinit var signup: Button

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        signup_email=findViewById(R.id.signup_email)
        signup_pass=findViewById(R.id.signup_pass)
        signup=findViewById(R.id.signup)
        auth = Firebase.auth


        signup.setOnClickListener {
            auth.createUserWithEmailAndPassword(signup_email.text.toString(), signup_pass.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "createUserWithEmail:success")
                        val user = auth.currentUser

                        var home= Intent(this@Signup,MainActivity::class.java)
                        startActivity(home)
                        finish()

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e("====", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }

        }
    }
}