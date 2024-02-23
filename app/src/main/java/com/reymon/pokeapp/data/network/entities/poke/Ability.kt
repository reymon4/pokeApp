package com.reymon.pokeapp.data.network.entities.poke

import kotlinx.serialization.Serializable

@Serializable
data class Ability(
    val ability: AbilityX,
    val is_hidden: Boolean,
    val slot: Int
)