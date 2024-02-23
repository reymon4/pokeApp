package com.reymon.pokeapp.logic.entities

import com.reymon.pokeapp.data.network.entities.poke.Form
import com.reymon.pokeapp.data.network.entities.poke.Move
import com.reymon.pokeapp.data.network.entities.poke.Showdown
import com.reymon.pokeapp.data.network.entities.poke.ShowdownX
import com.reymon.pokeapp.data.network.entities.poke.Sprites
import com.reymon.pokeapp.data.network.entities.poke.Type
import kotlinx.serialization.Serializable

@Serializable
data class PokemonLG(
    var id: Int = -1,
    var name: String = "",
    val base_experience: Int = -1,
    val height: Int = -1,
    val weight: Int = -1,
    var sprites: Sprites?=null,
)