package eu.acolombo.roxiemon.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import eu.acolombo.roxiemon.R
import eu.acolombo.roxiemon.data.local.model.Pokemon
import eu.acolombo.roxiemon.util.load

class PokemonAdapter(initial: List<Pokemon>, val onClick: (id: Int) -> Unit = {}) :
    RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    private val values: MutableList<Pokemon> = initial.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        (item.imageBack ?: item.imageFront)?.let { image ->
            holder.imageView.load(image)
            holder.imageView.visibility = View.VISIBLE
        } ?: run {
            holder.imageView.visibility = View.INVISIBLE
        }
        holder.numberView.text = item.id.toString()
        holder.nameView.text = item.name
        holder.view.setOnClickListener { onClick(item.id) }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imagePokemon)
        val numberView: TextView = view.findViewById(R.id.textNumber)
        val nameView: TextView = view.findViewById(R.id.textName)
    }

    fun addData(newValues: List<Pokemon>) =
        values.addAll(newValues).also { if (it) notifyDataSetChanged() }

    fun replaceData(newValues: List<Pokemon>) =
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {

            override fun areItemsTheSame(oldPos: Int, newPos: Int) =
                values[oldPos].id == newValues[newPos].id

            override fun getOldListSize() = values.size

            override fun getNewListSize() = newValues.size

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = false

        }).also { result ->
            values.clear()
            values.addAll(newValues)
            result.dispatchUpdatesTo(this@PokemonAdapter)
        }

}