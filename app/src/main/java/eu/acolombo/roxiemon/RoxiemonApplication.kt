package eu.acolombo.roxiemon

import android.app.Application
import com.ww.roxie.Roxie
import eu.acolombo.roxiemon.data.PokeApi
import eu.acolombo.roxiemon.data.PokemonRepository
import eu.acolombo.roxiemon.presentation.PokemonListViewModel
import eu.acolombo.roxiemon.presentation.pokemon.PokemonViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class RoxiemonApplication : Application() {

    private val appModule = module {
        single { PokemonRepository(PokeApi.create()) }
        viewModel { PokemonListViewModel(get()) }
        viewModel { (id: Int) -> PokemonViewModel(id, get()) }
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        Roxie.enableLogging(object : Roxie.Logger {
            override fun log(msg: String) {
                Timber.tag("Pokemon").d(msg)
            }
        })

        startKoin {
            androidContext(this@RoxiemonApplication)
            modules(appModule)
        }
    }
}