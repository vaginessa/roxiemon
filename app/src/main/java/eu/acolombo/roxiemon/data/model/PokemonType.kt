package eu.acolombo.roxiemon.data.model

import eu.acolombo.roxiemon.data.response.NamedItem

data class PokemonType(
    val slot: Int,
    val type: NamedItem<Type>
)