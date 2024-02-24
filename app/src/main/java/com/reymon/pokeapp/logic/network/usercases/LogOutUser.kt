package com.reymon.pokeapp.logic.network.usercases

import android.util.Log
import com.reymon.pokeapp.data.network.entities.users.UserDB
import com.reymon.pokeapp.data.network.repository.authentication.AuthenticationRepository

class LogOutUser {
     fun invoke(){
        AuthenticationRepository().signOut()
    }

}