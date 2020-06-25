package eu.acolombo.roxiemon.presentation.pokemon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import eu.acolombo.roxiemon.R
import eu.acolombo.roxiemon.data.local.model.Type
import eu.acolombo.roxiemon.util.load

class TypeAdapter(val onClick: (id: Int) -> Unit = {}) :
    RecyclerView.Adapter<TypeAdapter.ViewHolder>() {

    private val _values: MutableList<Type> = mutableListOf()
    var values: List<Type>
        get() = _values
        set(value) {
            replaceData(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_type, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.imageView.load(item.icon)
        holder.nameView.text = item.name
        holder.view.setOnClickListener { onClick(item.id) }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageType)
        val nameView: TextView = view.findViewById(R.id.textName)
    }

    private fun replaceData(newValues: List<Type>) =
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {

            override fun areItemsTheSame(oldPos: Int, newPos: Int) =
                values[oldPos].id == newValues[newPos].id

            override fun getOldListSize() = values.size

            override fun getNewListSize() = newValues.size

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = false

        }).let { result ->
            _values.clear()
            _values.addAll(newValues)
            result.dispatchUpdatesTo(this@TypeAdapter)
        }

}