package eu.acolombo.roxiemon.data

import eu.acolombo.roxiemon.data.model.Pokemon
import io.reactivex.Single
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers

class PokemonRepository(private val pokeApi: PokeApi) {

    fun getPokemon(): Single<List<Pokemon>> = pokeApi.getPokemon()
        .subscribeOn(Schedulers.io())
        .flatMap { it.results.toObservable() }
        .flatMap { pokeApi.getPokemon(it.name).subscribeOn(Schedulers.io()) }
        .toSortedList()
}