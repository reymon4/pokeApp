package com.reymon.pokeapp.data.network.entities.poke

import kotlinx.serialization.Serializable

@Serializable
data class Pokemon(
    val abilities: List<Ability>?=null,
    val base_experience: Int?=-1,
    val forms: List<Form>?=null,
    val height: Int?=-1,
    val id: Int,
    val is_default: Boolean,
    val location_area_encounters: String?="",
    val moves: List<Move>?=null,
    val name: String?="",
    val weight: Int?=-1,
    val sprites: Sprites?=null,

)