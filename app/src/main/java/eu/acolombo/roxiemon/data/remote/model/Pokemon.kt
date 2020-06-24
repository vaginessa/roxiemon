package eu.acolombo.roxiemon.data.remote.model

data class Pokemon(
    val id: Int,
    var name: String,
    var base_experience: String,
    var height: Int,
    var is_default: Boolean,
    var order: Int,
    var weight: Int,
    var types: List<PokemonType>,
    var sprites: PokemonSprites
)