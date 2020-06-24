package eu.acolombo.roxiemon.data.model

import eu.acolombo.roxiemon.data.response.Item

data class PokemonType(
    val slot: Int,
    val type: Item.Named<Type>
)