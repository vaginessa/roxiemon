package eu.acolombo.roxiemon.presentation.pokemon

import com.ww.roxie.BaseAction
import com.ww.roxie.BaseState
import com.ww.roxie.BaseViewModel
import com.ww.roxie.Reducer
import eu.acolombo.roxiemon.data.PokemonRepository
import eu.acolombo.roxiemon.data.local.model.Pokemon
import eu.acolombo.roxiemon.presentation.pokemon.PokemonViewModel.Action
import eu.acolombo.roxiemon.presentation.pokemon.PokemonViewModel.State
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.ofType
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class PokemonViewModel(
    id: Int,
    private val pokemonRepository: PokemonRepository
) : BaseViewModel<Action, State>() {

    override val initialState: State = State(isIdle = true)

    private val reducer: Reducer<State, Change> = { state, change ->
        when (change) {
            is Change.Loading -> state.copy(
                isIdle = false,
                isLoading = true,
                pokemon = null,
                isError = false
            )
            is Change.PokemonDetail -> state.copy(
                isLoading = false,
                pokemon = change.pokemon
            )
            is Change.Error -> state.copy(
                isLoading = false,
                isError = true
            ).also { Timber.e(change.throwable) }
        }
    }

    init {
        val pokemonChange = actions.ofType<Action.LoadPokemon>()
            .switchMap {
                pokemonRepository.getPokemon(it.id)
                    .subscribeOn(Schedulers.io())
                    .toObservable()
                    .map<Change> { poke -> Change.PokemonDetail(poke) }
                    .defaultIfEmpty(Change.Error(NullPointerException("empty")))
                    .onErrorReturn { err -> Change.Error(err) }
                    .startWith(Change.Loading)
            }

        disposables += pokemonChange
            .scan(initialState, reducer)
            .filter { !it.isIdle }
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(state::setValue, Timber::e)

        dispatch(Action.LoadPokemon(id))
    }

    sealed class Action : BaseAction {
        data class LoadPokemon(val id: Int) : Action()
    }

    sealed class Change {
        object Loading : Change()
        data class PokemonDetail(val pokemon: Pokemon) : Change()
        data class Error(val throwable: Throwable?) : Change()
    }

    data class State(
        val pokemon: Pokemon? = null,
        val isIdle: Boolean = false,
        val isLoading: Boolean = false,
        val isError: Boolean = false
    ) : BaseState

}