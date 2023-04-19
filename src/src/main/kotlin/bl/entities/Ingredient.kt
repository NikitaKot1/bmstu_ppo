package bl.entities

enum class IngredientType {
    YEAST, MALT, HOP, OTHER;

    fun toString(x: IngredientType): String = when (x) {
        YEAST -> "Дрожжи"
        MALT -> "Солод"
        HOP -> "Хмель"
        OTHER -> ""
    }
}

data class Ingredient(
    var id: ULong,
    var name: String,
    var type: IngredientType,
    var amount: UInt
)
