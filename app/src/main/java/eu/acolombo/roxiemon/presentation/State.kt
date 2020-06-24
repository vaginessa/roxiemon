package eu.acolombo.roxiemon.presentation

import com.ww.roxie.BaseState
import eu.acolombo.roxiemon.data.local.model.Pokemon

data class State(
    val pokemon: List<Pokemon> = listOf(),
    val isIdle: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val goToPokemon: Int? = null
    ) : BaseState