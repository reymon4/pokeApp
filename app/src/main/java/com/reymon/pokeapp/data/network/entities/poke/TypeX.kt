package com.reymon.pokeapp.data.network.entities.poke

import kotlinx.serialization.Serializable

@Serializable
data class TypeX(
    val name: String,
    val url: String
)