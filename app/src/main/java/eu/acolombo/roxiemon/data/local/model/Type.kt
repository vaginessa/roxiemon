package eu.acolombo.roxiemon.data.local.model

import androidx.annotation.DrawableRes
import eu.acolombo.roxiemon.R

data class Type(
    val id: Int,
    val name: String
) {
    @DrawableRes
    val icon: Int = when (name) {
        "water" -> R.drawable.ic_type_water
        else -> R.drawable.ic_type_unknown
    }
}