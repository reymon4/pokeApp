package com.reymon.pokeapp.data.network.repository.authentication


import android.content.Intent
import android.util.Log
import kotlinx.coroutines.tasks.await
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.reymon.pokeapp.data.network.entities.users.UserDB
import com.reymon.pokeapp.ui.activities.MainActivity


class AuthenticationRepository {

    private val auth = Firebase.auth
    suspend fun createUserWithEmailAndPassword(
        user: String,
        password: String
    )= runCatching {
        var userDB: UserDB? = null
        val usFirebase = auth.createUserWithEmailAndPassword(user, password).await().user
        if (usFirebase != null) {
            userDB = UserDB(usFirebase.uid, usFirebase.email!!, usFirebase.displayName.orEmpty())
        }
       return@runCatching userDB
    }

    suspend fun signInUsers(email: String, password: String): Result<UserDB?> = runCatching {
        var userDB: UserDB? = null
        val usFirebase=auth.signInWithEmailAndPassword(email, password).await().user
         if(usFirebase!=null){
             userDB = UserDB(usFirebase.uid, usFirebase.email!!, usFirebase.displayName.orEmpty())
         }
        return@runCatching userDB
    }

     fun signOut() {
        var userDB = auth.currentUser
        if (userDB != null) {
            Log.d("TAG", userDB.email.toString())
            Log.d("TAG", userDB.uid.toString())
            auth.signOut()
        }

    }



}