package eu.acolombo.roxiemon.data.response

data class ListResponse<I : Item<T>, T>(
    val count: Int?,
    val next: String?,
    val previous: String?,
    val results: List<I>?
)