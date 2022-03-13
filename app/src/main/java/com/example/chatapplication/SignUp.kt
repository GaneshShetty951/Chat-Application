package com.example.chatapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class SignUp : AppCompatActivity() {
    private val TAG ="SignUp"

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtName: EditText
    private lateinit var btnSignup: Button

    private lateinit var mDatabase : DatabaseReference

    private lateinit var mAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()
        mAuth = FirebaseAuth.getInstance()

        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        edtName = findViewById(R.id.edt_name)
        btnSignup = findViewById(R.id.btn_signup)

        btnSignup.setOnClickListener {
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString().trim()
            val name = edtName.text.toString().trim()

            signUp(name, email, password)
        }
    }

    private fun signUp(name : String, email: String, password: String) {
        // logic for logging in user

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                   // code for jumping to Home
                    mAuth.currentUser?.let { it1 -> addUserToDatabase(name, email, it1.uid) }

                    val intent = Intent(this, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                   Toast.makeText(this@SignUp, "Some error occurred " + (it.exception?.message ?: ""), Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun addUserToDatabase(name: String, email: String, uid: String) {
        mDatabase = FirebaseDatabase.getInstance().reference

        mDatabase.child("user").child(uid).setValue(User(name, email, uid))
    }

}