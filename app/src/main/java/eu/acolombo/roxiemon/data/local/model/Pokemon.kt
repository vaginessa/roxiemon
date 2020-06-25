package eu.acolombo.roxiemon.data.local.model

import eu.acolombo.roxiemon.data.remote.model.Pokemon as RemotePokemon

data class Pokemon(
    val id: Int,
    val name: String,
    val imageFront: String,
    val imageBack: String,
    var types: List<Type> = emptyList()
) : Comparable<Pokemon> {

    override fun compareTo(other: Pokemon) = when {
        this.id > other.id -> 1
        this.id < other.id -> -1
        else -> 0
    }

    constructor(pokemon: RemotePokemon) : this(
        pokemon.id,
        pokemon.name,
        pokemon.sprites.front_default,
        pokemon.sprites.back_default
    )

    constructor(poke: RemotePokemon, types: List<Type>) : this(poke) {
        this.types = types
    }

}