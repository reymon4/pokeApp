package com.reymon.pokeapp.data.network.entities.poke

import kotlinx.serialization.Serializable

@Serializable
data class Other(

    val home: HomeX?=null,
    val officialArtwork: OfficialArtworkX?=null,
    val showdown: ShowdownX?=null
)