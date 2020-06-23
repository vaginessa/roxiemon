package eu.acolombo.roxiemon.data.response

data class ListResponse<T>(
    val count: Int?,
    val next: String?,
    val previous: String?,
    val results: List<Item<T>>?
)