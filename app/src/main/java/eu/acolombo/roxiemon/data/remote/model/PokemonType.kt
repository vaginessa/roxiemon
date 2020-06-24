package eu.acolombo.roxiemon.data.remote.model

import eu.acolombo.roxiemon.data.remote.response.Item

data class PokemonType(
    val slot: Int,
    val type: Item.Named<Type>
)