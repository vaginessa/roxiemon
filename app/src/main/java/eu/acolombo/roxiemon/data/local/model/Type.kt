package eu.acolombo.roxiemon.data.local.model

import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import eu.acolombo.roxiemon.R

data class Type(
    val id: Int,
    val name: String
) {
    @DrawableRes
    val icon: Int = when (name) {
        "bug" -> R.drawable.ic_type_bug
        "fire" -> R.drawable.ic_type_fire
        "grass" -> R.drawable.ic_type_grass
        "ice" -> R.drawable.ic_type_ice
        "normal" -> R.drawable.ic_type_normal
        "water" -> R.drawable.ic_type_water
        "poison" -> R.drawable.ic_type_poison
        "flying" -> R.drawable.ic_type_flying
        else -> R.drawable.ic_type_unknown
    }
}