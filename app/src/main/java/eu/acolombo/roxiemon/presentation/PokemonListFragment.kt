package eu.acolombo.roxiemon.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import eu.acolombo.roxiemon.R
import kotlinx.android.synthetic.main.fragment_pokemon_list.*
import org.koin.android.viewmodel.ext.android.viewModel

class PokemonListFragment : Fragment(R.layout.fragment_pokemon_list) {

    private val viewModel: PokemonListViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = PokemonAdapter(listOf())
        }
    }

}