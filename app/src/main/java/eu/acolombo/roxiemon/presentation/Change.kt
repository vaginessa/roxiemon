package eu.acolombo.roxiemon.presentation

import eu.acolombo.roxiemon.data.model.Pokemon

sealed class Change {
    object Loading : Change()
    data class PokemonList(val pokemon: List<Pokemon>) : Change()
    data class Error(val throwable: Throwable?) : Change()
    data class OpenPokemon(val id: Int) : Change()
}