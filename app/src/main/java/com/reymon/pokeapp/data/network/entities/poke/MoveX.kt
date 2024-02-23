package com.reymon.pokeapp.data.network.entities.poke

import kotlinx.serialization.Serializable

@Serializable
data class MoveX(
    val name: String,
    val url: String
)