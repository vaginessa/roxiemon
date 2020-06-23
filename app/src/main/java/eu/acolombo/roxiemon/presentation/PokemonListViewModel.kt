package eu.acolombo.roxiemon.presentation

import androidx.lifecycle.viewModelScope
import com.ww.roxie.BaseViewModel
import com.ww.roxie.Reducer
import eu.acolombo.roxiemon.data.PokemonRepository
import kotlinx.coroutines.launch

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
            is Change.GoToPokemon -> state.copy(
                isLoading = true,
                goToPokemon = change.id,
                isError = false,
                isIdle = false
            )
        }
    }

    init {
        dispatch(Action.LoadPokemonList)
    }


}