package com.reymon.pokeapp.logic.network.usercases

import com.reymon.pokeapp.data.network.entities.users.UserDB
import com.reymon.pokeapp.data.network.repository.authentication.UsersRepository

class SaveUserInDB {
    suspend fun invoke(id: String, email: String, name: String): UserDB? {
        return UsersRepository().saveUserDB(id, email, name).getOrNull()

    }

}