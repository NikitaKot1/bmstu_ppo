package bl.entities

import java.time.LocalDateTime

data class Recipe(
    var id: ULong,
    var name: String,
    var description: String,
    var date: LocalDateTime,
    var comments: List<Comment>,
    var owner: Manufacturer,
    var ingredients: List<Ingredient>
)


data class RecipePreview(
    val id: ULong, var name: String, var description: String
)

fun Recipe.toRecipePreview(): RecipePreview =
    RecipePreview(this.id, this.name, this.description)