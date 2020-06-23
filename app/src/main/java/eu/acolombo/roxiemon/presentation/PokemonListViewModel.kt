package eu.acolombo.roxiemon.presentation

import com.ww.roxie.BaseAction
import com.ww.roxie.BaseState
import com.ww.roxie.BaseViewModel
import com.ww.roxie.Reducer
import eu.acolombo.roxiemon.data.PokemonRepository
import eu.acolombo.roxiemon.data.model.Pokemon
import eu.acolombo.roxiemon.presentation.PokemonListViewModel.*

class PokemonListViewModel(val pokemonRepository: PokemonRepository) : BaseViewModel<Action, State>() {

    override val initialState: State = State(isIdle = true)

    private val reducer: Reducer<State, Change> = { state, change ->
        when (change) {
            is Change.Loading -> state.copy(
                isIdle = false,
                isLoading = true,
                pokemon = emptyList(),
                isError = false
            )
            is Change.PokemonList -> state.copy(
                isLoading = false,
                pokemon = change.pokemon
            )
            is Change.Error -> state.copy(
                isLoading = false,
                isError = true
            )
        }
    }

    sealed class Action : BaseAction {
        object LoadPokemonList : Action()
    }

    sealed class Change {
        object Loading : Change()
        data class PokemonList(val pokemon: List<Pokemon>) : Change()
        data class Error(val throwable: Throwable?) : Change()
    }

    data class State(
        val pokemon: List<Pokemon> = listOf(),
        val isIdle: Boolean = false,
        val isLoading: Boolean = false,
        val isError: Boolean = false
    ) : BaseState

}