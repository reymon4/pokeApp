package com.reymon.pokeapp.data.network.entities.poke

import kotlinx.serialization.Serializable

@Serializable
data class Pokemon(
    val abilities: List<Ability>,
    val base_experience: Int,
    val forms: List<Form>,
    val height: Int,
    val id: Int,
    val is_default: Boolean,
    val location_area_encounters: String,
    val moves: List<Move>,
    val name: String,
    val weight: Int,
    val sprites: Sprites?=null,

)