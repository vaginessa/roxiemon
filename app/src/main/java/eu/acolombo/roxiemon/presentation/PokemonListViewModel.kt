package eu.acolombo.roxiemon.presentation

import com.ww.roxie.BaseAction
import com.ww.roxie.BaseState
import com.ww.roxie.BaseViewModel
import com.ww.roxie.Reducer
import eu.acolombo.roxiemon.data.PokemonRepository
import eu.acolombo.roxiemon.data.local.model.Pokemon
import eu.acolombo.roxiemon.presentation.PokemonListViewModel.Action
import eu.acolombo.roxiemon.presentation.PokemonListViewModel.State
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.ofType
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class PokemonListViewModel(
    private val pokemonRepository: PokemonRepository
) : BaseViewModel<Action, State>() {

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
            ).also { Timber.e(change.throwable) }
            is Change.OpenPokemon -> state.copy(
                isLoading = false,
                goToPokemon = change.id,
                isError = false,
                isIdle = false
            )
        }
    }

    init {
        val pokemonListChange = actions.ofType<Action.LoadPokemonList>()
            .switchMap {
                pokemonRepository.getPokemon()
                    .subscribeOn(Schedulers.io())
                    .toObservable()
                    .map<Change> { Change.PokemonList(it) }
                    .defaultIfEmpty(Change.PokemonList(emptyList()))
                    .onErrorReturn { Change.Error(it) }
                    .startWith(Change.Loading)
            }

        val pokemonDetailChange = actions.ofType<Action.OpenPokemon>()
            .switchMap { Observable.just(Change.OpenPokemon(it.id)) }

        disposables += Observable.merge(pokemonDetailChange, pokemonListChange)
            .scan(initialState, reducer)
            .filter { !it.isIdle }
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(state::setValue, Timber::e)

        dispatch(Action.LoadPokemonList)
    }

    sealed class Action : BaseAction {
        object LoadPokemonList : Action()
        data class OpenPokemon(val id: Int) : Action()
    }

    sealed class Change {
        object Loading : Change()
        data class PokemonList(val pokemon: List<Pokemon>) : Change()
        data class Error(val throwable: Throwable?) : Change()
        data class OpenPokemon(val id: Int) : Change()
    }

    data class State(
        val pokemon: List<Pokemon> = listOf(),
        val isIdle: Boolean = false,
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val goToPokemon: Int? = null
    ) : BaseState

}