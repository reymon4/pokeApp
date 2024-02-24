package com.reymon.pokeapp.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.reymon.pokeapp.R

class MarvelActivity : AppCompatActivity() {
    //Debo crear binding, viewModel
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_marvel)

        getUsersData()
        initListeners()
    }

    private fun initListeners() {
        val btn = findViewById<Button>(R.id.btn_salir)
        logOut()
    }


    //Consulto el user en la db
    private fun getUsersData() {
        val user = Firebase.auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val name = it.displayName
            val email = it.email
            val photoUrl = it.photoUrl

            // Check if user's email is verified
            val emailVerified = it.isEmailVerified

            val uid = it.uid

            Log.d("TAG", email.toString())
            Log.d("TAG", uid.toString())
        }
    }

    private fun logOut() {
        auth.signOut()
        startActivity(Intent(this, MainActivity::class.java))
    }
}