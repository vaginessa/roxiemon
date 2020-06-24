package eu.acolombo.roxiemon.presentation

import android.util.Log
import com.ww.roxie.BaseViewModel
import com.ww.roxie.Reducer
import eu.acolombo.roxiemon.data.PokemonRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.ofType
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class PokemonListViewModel(private val pokemonRepository: PokemonRepository) :
    BaseViewModel<Action, State>() {

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
                isLoading = true,
                goToPokemon = change.id,
                isError = false,
                isIdle = false
            )
        }
    }

    init {
        bindActions()

        dispatch(Action.LoadPokemonList)
    }

    private fun bindActions() {

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

    }


}