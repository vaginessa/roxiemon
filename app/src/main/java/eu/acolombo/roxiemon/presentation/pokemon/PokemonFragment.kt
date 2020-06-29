package eu.acolombo.roxiemon.presentation.pokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import eu.acolombo.roxiemon.R
import eu.acolombo.roxiemon.data.local.model.Pokemon
import eu.acolombo.roxiemon.util.load
import kotlinx.android.synthetic.main.fragment_pokemon.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PokemonFragment : BottomSheetDialogFragment() {

    companion object {
        private const val POKEMON_ID = "pokemonId"

        fun newInstance(id: Int) = PokemonFragment().apply {
            arguments = Bundle().apply { putInt(POKEMON_ID, id) }
        }
    }

    private val pokemonId
        get() = arguments?.getInt(POKEMON_ID) ?: throw IllegalArgumentException("id is required")

    private val viewModel: PokemonViewModel by viewModel { parametersOf(pokemonId) }
    private val typeAdapter = TypeAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_pokemon, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refresh.isEnabled = false
        recyclerTypes.adapter = typeAdapter

        viewModel.observableState.observe(viewLifecycleOwner, Observer { state ->
            showLoading(state.isLoading)
            showError(state.isError)
            state.pokemon?.let { showPokemon(it) }
        })
    }

    private fun showPokemon(pokemon: Pokemon) {
        pokemon.imageFront?.let { image -> imagePokemon.load(image) }
        textNumber.text = pokemon.id.toString()
        textName.text = pokemon.name
        typeAdapter.values = pokemon.types
    }

    private fun showError(error: Boolean) {
        if (error) {
            Toast.makeText(context, getString(R.string.pokemon_list_error), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun showLoading(loading: Boolean) {
        refresh.isRefreshing = loading
    }


}