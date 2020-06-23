package eu.acolombo.roxiemon.data

import eu.acolombo.roxiemon.data.PokeApi.Companion.PAGE_SIZE
import eu.acolombo.roxiemon.data.model.Pokemon
import kotlin.coroutines.suspendCoroutine

class PokemonRepository(val api: PokeApi) {

    suspend fun getPokemonList(page: Int): List<Pokemon> = suspendCoroutine { continuation ->
        val resultList = api.getPokemon(offset = PAGE_SIZE * page).execute()
        if (resultList.isSuccessful) {
            resultList.body()?.results?.mapNotNull {
                api.getPokemon(it.name).execute().takeIf { result -> result.isSuccessful }?.body()
            }?.let { result ->
                continuation.resumeWith(Result.success(result))
            } ?: run {
                continuation.resumeWith(Result.failure(NullPointerException("There's no single pokemon")))
            }
        } else {
            continuation.resumeWith(Result.failure(Exception("Unsuccessful")))
        }
    }

}