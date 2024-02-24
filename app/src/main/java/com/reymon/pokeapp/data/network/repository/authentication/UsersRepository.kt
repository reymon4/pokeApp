package com.reymon.pokeapp.data.network.repository.authentication


import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.reymon.pokeapp.data.network.entities.users.UserDB
import kotlinx.coroutines.tasks.await

class UsersRepository {

    private val db = Firebase.firestore

    //To return user
    suspend fun saveUserDB(id: String, email: String, name: String): Result<UserDB> = runCatching {
        val us = UserDB(id, email, name)
        //Collection save Jsons
        val a = db.collection("Users").add(us).await()
        return@runCatching us
    }

    suspend fun getUserByID(id: String) = runCatching {
        val us = UserDB(id, "", "")
        return@runCatching db.collection("Users")
            .document(us.id)
            .get()
            .await<DocumentSnapshot?>()?.toObject<UserDB>(UserDB::class.java)

    }

    suspend fun updateUserByID(id: String) = runCatching {
        val us = UserDB(id, "", "")
        val update= db.collection("User")
            .document(us.id)
            .get()
            .await<DocumentSnapshot?>()?.toObject<UserDB>(UserDB::class.java)
        if (update != null) {
            update!!.email = "hola"

            db.collection("Users").document(update!!.id).set(update)
        }
    }
}