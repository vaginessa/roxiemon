package eu.acolombo.roxiemon.presentation.pokemon


import androidx.fragment.app.Fragment
import eu.acolombo.roxiemon.R
import org.koin.android.viewmodel.ext.android.viewModel

class PokemonFragment : Fragment(R.layout.fragment_pokemon) {

    private val viewModel: PokemonViewModel by viewModel()

}