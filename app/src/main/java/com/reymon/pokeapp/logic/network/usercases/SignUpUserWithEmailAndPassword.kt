package com.reymon.pokeapp.logic.network.usercases

import com.reymon.pokeapp.data.network.entities.users.UserDB
import com.reymon.pokeapp.data.network.repository.authentication.AuthenticationRepository

class SignUpUserWithEmailAndPassword {
    suspend fun invoke(email:String, password: String): UserDB?{
        var user: UserDB? = null
        AuthenticationRepository().createUserWithEmailAndPassword(email,password)
            .onSuccess {
                user=it
            }.onFailure {
                user=null
            }
        return user
    }
}