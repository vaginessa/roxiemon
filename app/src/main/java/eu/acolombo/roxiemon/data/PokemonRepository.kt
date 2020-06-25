package eu.acolombo.roxiemon.data

import eu.acolombo.roxiemon.data.local.model.Pokemon
import eu.acolombo.roxiemon.data.local.model.Type
import eu.acolombo.roxiemon.data.remote.PokeApi
import io.reactivex.Single
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers

class PokemonRepository(private val pokeApi: PokeApi) {

    fun getPokemon(): Single<List<Pokemon>> = pokeApi.getPokemon()
        .subscribeOn(Schedulers.io())
        .flatMap { it.results.toObservable() }
        .flatMap { pokeApi.getPokemon(it.name).subscribeOn(Schedulers.io()) }
        .map { Pokemon(it) }
        .toSortedList()

    fun getPokemon(id: Int): Single<Pokemon> = pokeApi.getPokemon(id)
        .subscribeOn(Schedulers.io())
        .flatMap({ pokemon ->
            pokemon.types.toObservable()
                .flatMap { pokeApi.getType(it.type.name) }
                .map { Type(it.id, it.name) }
                .toSortedList().toObservable()
        }, { pokemon, type -> Pokemon(pokemon, type) })
        .singleOrError()

}