package eu.acolombo.roxiemon.data.local.model

data class Pokemon(
    val id: Int,
    val name: String,
    val imageFront: String,
    val imageBack: String,
    val types: List<Type>
) : Comparable<Pokemon> {

    override fun compareTo(other: Pokemon) = when {
        this.id > other.id -> 1
        this.id < other.id -> -1
        else -> 0
    }

}