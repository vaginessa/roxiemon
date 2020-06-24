package eu.acolombo.roxiemon.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import eu.acolombo.roxiemon.data.model.Pokemon
import eu.acolombo.roxiemon.data.model.Type
import eu.acolombo.roxiemon.data.response.Item
import eu.acolombo.roxiemon.data.response.ListResponse
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
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
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .build()
                )
            )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(PokeApi::class.java)
    }

    @GET("pokemon")
    fun getPokemon(
        @Query("limit") limit: Int = PAGE_SIZE,
        @Query("offset") offset: Int = 0
    ): Observable<ListResponse<Item.Named<Pokemon>, Pokemon>>

    @GET("pokemon/{id}")
    fun getPokemon(
        @Path("id") id: Int
    ): Observable<Pokemon>

    @GET("pokemon/{name}")
    fun getPokemon(
        @Path("name") name: String
    ): Observable<Pokemon>

    @GET("type/{id}")
    fun getType(
        @Path("id") id: Int
    ): Observable<Type>

    @GET("type/{name}")
    fun getType(
        @Path("name") name: String
    ): Observable<Type>

}