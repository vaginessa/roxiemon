package eu.acolombo.roxiemon.presentation

import com.ww.roxie.BaseAction

sealed class Action : BaseAction {
    object LoadPokemonList : Action()
    data class OpenPokemon(val id: Int) : Action()
}
