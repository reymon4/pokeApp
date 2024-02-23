package com.reymon.pokeapp.data.network.entities.poke

import kotlinx.serialization.Serializable

@Serializable
data class Sprites(
    val back_default: String,

    val front_default: String,

    val other: Other?=null,

)