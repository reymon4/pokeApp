package com.reymon.pokeapp.logic.network.usercases

import com.reymon.pokeapp.data.network.entities.users.UserDB
import com.reymon.pokeapp.data.network.repository.authentication.UsersRepository

class GetUserByID {
    suspend fun invoke(id: String): UserDB? {
        return UsersRepository().getUserByID(id).getOrNull()

    }
}