package com.reymon.pokeapp.data.network.entities.poke

import kotlinx.serialization.Serializable

@Serializable
data class Type(
    val slot: Int,
    val type: TypeX
)