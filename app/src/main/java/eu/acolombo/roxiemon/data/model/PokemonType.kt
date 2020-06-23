package eu.acolombo.roxiemon.data.model

import eu.acolombo.roxiemon.data.TMDbApi

data class PokemonType(
    val slot: Int,
    val type: TMDbApi.NamedItem<Type>
)