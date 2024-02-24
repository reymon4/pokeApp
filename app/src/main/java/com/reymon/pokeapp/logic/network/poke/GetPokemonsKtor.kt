package com.reymon.pokeapp.logic.network.poke

import android.util.Log

import com.reymon.pokeapp.data.network.repository.KtorApiModule
import com.reymon.pokeapp.data.network.repository.RetrofitBase
import com.reymon.pokeapp.logic.network.entities.PokemonLG
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.http.isSuccess
import kotlin.random.Random

class GetPokemonsKtor {

    suspend fun invokeForId(id: Int): PokemonLG {
        var ret = PokemonLG()

        //Endpoint
        runCatching {
            KtorApiModule.getKtorHttpClient().get() {
                url("${RetrofitBase.POKEMON_URL}$id")
            }
        }.onSuccess {
            if (it.status.isSuccess()) {
                ret = it.body()

            }

        }.onFailure {
            Log.d("PokeApi", "No connected to api!")
        }
        return ret
    }

    suspend fun invokeList(max: Int): List<PokemonLG> {
        val pokemonList =
            mutableListOf<PokemonLG>() // Usamos mutableListOf para permitir la adición de elementos

        // Rango 1-920, Pokemons con imagen con gifs
        for (i in 1..max) {
            val randomNumberInRange =
                Random.nextInt(1, 921) // Ajustamos el rango para incluir el 649
            pokemonList.add(invokeForId(randomNumberInRange))
        }

        return pokemonList
    }



    suspend fun invokeForName(name: String): PokemonLG {
        var ret = PokemonLG()
        //Endpoint
        runCatching {
            KtorApiModule.getKtorHttpClient().get() {
                url("${RetrofitBase.POKEMON_URL}$name")

            }
        }.onSuccess {
            if (it.status.isSuccess()) {
                ret = it.body<PokemonLG>()
            }


        }.onFailure {

        }
        return ret
    }


}