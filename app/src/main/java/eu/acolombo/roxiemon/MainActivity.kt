package eu.acolombo.roxiemon

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import eu.acolombo.roxiemon.presentation.PokemonListFragment

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.commit { replace(R.id.container, PokemonListFragment(), PokemonListFragment::class.java.name) }
    }

}