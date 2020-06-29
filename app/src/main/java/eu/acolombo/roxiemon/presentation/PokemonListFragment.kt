package eu.acolombo.roxiemon.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import eu.acolombo.roxiemon.R
import eu.acolombo.roxiemon.data.local.model.Pokemon
import eu.acolombo.roxiemon.presentation.PokemonListViewModel.Action.LoadMorePokemon
import eu.acolombo.roxiemon.presentation.pokemon.PokemonFragment
import eu.acolombo.roxiemon.util.doOnScrolled
import kotlinx.android.synthetic.main.fragment_pokemon_list.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class PokemonListFragment : Fragment(R.layout.fragment_pokemon_list) {

    companion object {
        private const val PAGING_THRESHOLD = 8
    }

    private val viewModel: PokemonListViewModel by sharedViewModel()
    private lateinit var pokemonAdapter: PokemonAdapter
    private lateinit var gridManager: GridLayoutManager
    private var recyclerListener: RecyclerView.OnScrollListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gridManager = GridLayoutManager(requireContext(), 3)
        pokemonAdapter = PokemonAdapter(viewModel.pokemonList) {
            PokemonFragment.newInstance(it)
                .show(requireActivity().supportFragmentManager, PokemonFragment::class.java.name)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refresh.isEnabled = false

        recycler.apply {
            adapter = pokemonAdapter
            layoutManager = gridManager
            scrollToPosition(viewModel.currentScrollPosition)
        }

        viewModel.observableState.observe(viewLifecycleOwner, Observer { state ->
            showLoading(state.isLoading)
            showError(state.isError)
            showPokemon(state.pokemon)
            showMore(state.more)
        })
    }

    private fun showPokemon(pokemon: List<Pokemon>, replace: Boolean = false) {
        if (replace) pokemonAdapter.replaceData(pokemon) else pokemonAdapter.addData(pokemon)
    }

    private fun showMore(more: Boolean) {
        if (!more) {
            recyclerListener?.let { recycler.removeOnScrollListener(it) }
        } else {
            recyclerListener ?: run {
                recyclerListener = recycler.doOnScrolled { _, _, _ ->
                    val firstPosition = gridManager.findFirstCompletelyVisibleItemPosition()
                    val lastPosition = gridManager.findLastVisibleItemPosition()
                    viewModel.currentScrollPosition = firstPosition
                    if (!refresh.isRefreshing && gridManager.itemCount <= (lastPosition + PAGING_THRESHOLD)) {
                        viewModel.dispatch(LoadMorePokemon)
                    }
                }
            }
        }
    }

    private fun showError(throwable: Throwable?) {
        throwable?.let {
            Toast.makeText(context, it.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLoading(loading: Boolean) {
        refresh.isRefreshing = loading
    }

}