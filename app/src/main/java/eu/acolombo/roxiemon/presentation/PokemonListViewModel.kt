package eu.acolombo.roxiemon.presentation

import com.ww.roxie.BaseAction
import com.ww.roxie.BaseState
import com.ww.roxie.BaseViewModel
import eu.acolombo.roxiemon.data.PokemonRepository
import eu.acolombo.roxiemon.data.model.Pokemon
import eu.acolombo.roxiemon.presentation.PokemonListViewModel.*

class PokemonListViewModel(val pokemonRepository: PokemonRepository) : BaseViewModel<Action, State>() {

    override val initialState: State = State(isIdle = true)

    sealed class Action : BaseAction {
        object LoadPokemon : Action()
    }

    data class State(
        val pokemon: List<Pokemon> = listOf(),
        val isIdle: Boolean = false,
        val isLoading: Boolean = false,
        val isError: Boolean = false
    ) : BaseState

}