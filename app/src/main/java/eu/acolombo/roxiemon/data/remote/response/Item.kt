package eu.acolombo.roxiemon.data.remote.response

open class Item<T>(val url: String) {
    class Named<T>(url: String, val name: String) : Item<T>(url)
}