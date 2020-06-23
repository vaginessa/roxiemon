package eu.acolombo.roxiemon.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import eu.acolombo.roxiemon.R
import eu.acolombo.roxiemon.data.model.Pokemon

class PokemonAdapter(val onClick: (id: Int) -> Unit = {} ) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    private val _values: MutableList<Pokemon> = mutableListOf()
    var values: List<Pokemon>
        get() = _values
        set(value) {
            replaceData(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.id.toString()
        holder.contentView.text = item.name
        holder.view.setOnClickListener { onClick(item.id) }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.item_number)
        val contentView: TextView = view.findViewById(R.id.content)
    }

    private fun replaceData(newValues: List<Pokemon>) =
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {

            override fun areItemsTheSame(oldPos: Int, newPos: Int) =
                values[oldPos].id == newValues[newPos].id

            override fun getOldListSize() = values.size

            override fun getNewListSize() = newValues.size

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = false

        }).let { result ->
            _values.clear()
            _values.addAll(newValues)
            result.dispatchUpdatesTo(this@PokemonAdapter)
        }

}