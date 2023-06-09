package da.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object SavedRecipes : IntIdTable("added_to_like") {
    val user = reference("userid", Consumers)
    val recipe = reference("recipeid", Recipes)
}

class SavedRecipeTable(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<SavedRecipeTable>(SavedRecipes)

    var userId by SavedRecipes.user
    var recipeId by SavedRecipes.recipe

    var user by ConsumerTable referencedOn SavedRecipes.user
    var recipe by RecipeTable referencedOn SavedRecipes.recipe
}