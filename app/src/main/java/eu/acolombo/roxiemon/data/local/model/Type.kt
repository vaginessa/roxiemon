package eu.acolombo.roxiemon.data.local.model

data class Type(
    val id: Int,
    val name: String
) {
    val emoji: String = when (name) {
        "normal" -> "\uD83D\uDE10"
        "fire" -> "\uD83D\uDD25"

        "fighting" -> "\uD83E\uDD4A"
        "water" -> "\uD83D\uDCA7"

        "flying" -> "\u2708\uFE0f"
        "grass" -> "\uD83E\uDD66"

        "poison" -> "\u2623\uFE0F"
        "electric" -> "\u26A1"

        "ground" -> "\uD83C\uDF31"
        "psychic" -> "\uD83D\uDD2E"

        "rock" -> "\uD83E\uDD18"
        "ice" -> "\uD83E\uDDCA"

        "bug" -> "\uD83D\uDC1E"
        "dragon" -> "\uD83D\uDC32"

        "ghost" -> "\uD83D\uDC7B"
        "dark" -> "\uD83D\uDD76"

        "steel" -> "\uD83D\uDD29"
        "fairy" -> "\uD83E\uDDDA"

        else -> "\u2753"
    }
}