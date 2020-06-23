package eu.acolombo.roxiemon.data.response

interface NamedItem<T> : Item<T> {
    val name: String
}