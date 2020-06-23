package eu.acolombo.roxiemon.presentation.pokemon

import com.ww.roxie.BaseAction
import com.ww.roxie.BaseState
import com.ww.roxie.BaseViewModel
import eu.acolombo.roxiemon.data.PokemonRepository
import eu.acolombo.roxiemon.data.model.Pokemon
import eu.acolombo.roxiemon.presentation.pokemon.PokemonViewModel.*

class PokemonViewModel(val pokemonRepository: PokemonRepository) : BaseViewModel<Action, State>() {

    override val initialState: State = State(isIdle = true)

    data class State(
        val pokemon: List<Pokemon> = listOf(),
        val isIdle: Boolean = false,
        val isLoading: Boolean = false,
        val isError: Boolean = false
    ) : BaseState

    sealed class Action : BaseAction {
        data class LoadPokemon(val pokemonId: Int) : Action()
    }

}