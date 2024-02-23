package com.reymon.pokeapp.data.network.entities.poke

data class VersionGroupDetail(
    val level_learned_at: Int,
    val move_learn_method: MoveLearnMethodX,
    val version_group: VersionGroup
)