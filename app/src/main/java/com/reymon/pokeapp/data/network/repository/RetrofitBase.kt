package com.reymon.pokeapp.data.network.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBase {

    const val POKEMON_URL = "https://pokeapi.co/api/v2/pokemon/"
    fun getRetrofitPokeConnection(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(POKEMON_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}