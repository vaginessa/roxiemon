package eu.acolombo.roxiemon

import android.app.Application
import eu.acolombo.roxiemon.data.PokemonRepository
import eu.acolombo.roxiemon.presentation.PokemonListViewModel
import eu.acolombo.roxiemon.presentation.pokemon.PokemonViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class RoxiemonApplication : Application() {

    private val appModule = module {

        single { PokemonRepository() }
        viewModel { PokemonViewModel(get()) }
        viewModel { PokemonListViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@RoxiemonApplication)
            modules(appModule)
        }
    }
}