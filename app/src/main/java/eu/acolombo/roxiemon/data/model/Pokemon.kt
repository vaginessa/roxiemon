package eu.acolombo.roxiemon.data.model

data class Pokemon(
    val id: Int,
    var name: String,
    var baseExperience: String,
    var height: Int,
    var isDefault: Boolean,
    var order: Int,
    var weight: Int,
    var types: List<PokemonType>
)