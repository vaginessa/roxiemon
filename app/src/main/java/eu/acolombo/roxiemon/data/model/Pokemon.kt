package eu.acolombo.roxiemon.data.model

data class Pokemon(
    val id: Int,
    var name: String,
    var base_experience: String,
    var height: Int,
    var is_default: Boolean,
    var order: Int,
    var weight: Int,
    var types: List<PokemonType>
) : Comparable<Pokemon> {

    override fun compareTo(other: Pokemon) = when {
        this.id > other.id -> 1
        this.id < other.id -> -1
        else -> 0
    }

}