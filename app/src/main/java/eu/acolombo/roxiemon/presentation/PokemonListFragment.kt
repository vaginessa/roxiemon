package eu.acolombo.roxiemon.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import eu.acolombo.roxiemon.R
import eu.acolombo.roxiemon.data.model.Pokemon
import eu.acolombo.roxiemon.presentation.pokemon.PokemonFragment
import kotlinx.android.synthetic.main.fragment_pokemon_list.*
import org.koin.android.viewmodel.ext.android.viewModel

class PokemonListFragment : Fragment(R.layout.fragment_pokemon_list) {

    private val viewModel: PokemonListViewModel by viewModel()
    private val pokemonAdapter = PokemonAdapter {
        viewModel.dispatch(Action.OpenPokemon)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refresh.isEnabled = false

        recycler.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = pokemonAdapter
        }

        viewModel.observableState.observe(viewLifecycleOwner, Observer { state ->
            showLoading(state.isLoading)
            showError(state.isError)
            showPokemon(state.pokemon)
            goToPokemon(state.goToPokemon)
        })
    }

    private fun showPokemon(pokemon: List<Pokemon>) {
        pokemonAdapter.values = pokemon
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

    private fun goToPokemon(id: Int?) {
        id?.let {
            requireActivity().supportFragmentManager.commit {
                replace(R.id.container, PokemonFragment.newInstance(it))
            }
        }
    }

}