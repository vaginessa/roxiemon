package eu.acolombo.roxiemon.data

import eu.acolombo.roxiemon.data.response.ListResponse
import eu.acolombo.roxiemon.data.response.NamedItem
import eu.acolombo.roxiemon.data.model.Pokemon
import eu.acolombo.roxiemon.data.model.Type
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {

    companion object {
        const val PAGE_SIZE = 20
        private const val ROOT_ENDPOINT = "https://pokeapi.co/api/v2/"

        fun create(): PokeApi = Retrofit.Builder()
            .baseUrl(ROOT_ENDPOINT)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(PokeApi::class.java)
    }

    @GET("pokemon")
    fun getPokemon(
        @Query("limit") limit: Int = PAGE_SIZE,
        @Query("offset") offset: Int = PAGE_SIZE
    ): Call<ListResponse<NamedItem<Pokemon>, Pokemon>>

    @GET("pokemon/{id}")
    fun getPokemon(
        @Path("id") id: Int
    ): Call<Pokemon>

    @GET("pokemon/{name}")
    fun getPokemon(
        @Path("name") name: String
    ): Call<Pokemon>

    @GET("type/{id}")
    fun getType(
        @Path("id") id: Int
    ): Call<Type>

    @GET("type/{name}")
    fun getType(
        @Path("name") name: String
    ): Call<Type>

}