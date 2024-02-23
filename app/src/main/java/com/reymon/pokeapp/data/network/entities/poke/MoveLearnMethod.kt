package com.reymon.pokeapp.data.network.entities.poke

import kotlinx.serialization.Serializable

@Serializable
data class MoveLearnMethod(
    val name: String,
    val url: String
)