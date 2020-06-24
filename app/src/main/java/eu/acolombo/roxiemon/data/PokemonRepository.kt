package eu.acolombo.roxiemon.data

import eu.acolombo.roxiemon.data.local.model.Pokemon
import eu.acolombo.roxiemon.data.remote.PokeApi
import io.reactivex.Single
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers

class PokemonRepository(private val pokeApi: PokeApi) {

    fun getPokemon(): Single<List<Pokemon>> = pokeApi.getPokemon()
        .subscribeOn(Schedulers.io())
        .flatMap { it.results.toObservable() }
        .flatMap { pokeApi.getPokemon(it.name).subscribeOn(Schedulers.io()) }
        .map { Pokemon(it.id, it.name, it.sprites.front_default, emptyList()) }
        .toSortedList()
}