package eu.acolombo.roxiemon.presentation.pokemon


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import eu.acolombo.roxiemon.R
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PokemonFragment : BottomSheetDialogFragment() {

    companion object {
        private const val POKEMON_ID = "pokemonId"

        fun newInstance(id: Int) = PokemonFragment().apply {
            arguments = Bundle().apply { putInt(POKEMON_ID, id) }
        }
    }

    private val viewModel: PokemonViewModel by viewModel {
        parametersOf(
            this,
            arguments?.getInt(POKEMON_ID) ?: throw IllegalArgumentException("id is required")
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_pokemon, container, false)


}