package bl.repositories

import bl.entities.Comment
import bl.entities.Ingredient
import bl.entities.Recipe
import bl.entities.RecipePreview

interface IRecipeRepository : IRepository<Recipe> {
    //TODO убрать превью
    fun getAllPreview(): List<RecipePreview>
    fun getOwnerID(id: ULong): ULong

    fun addToFavorite(id: ULong, userID: ULong)

    fun addComment(userId: ULong, recipeId: ULong, comment: Comment)

    fun getSortedByDate(): List<RecipePreview>
    fun getSortedByLike(): List<RecipePreview>
    fun searchByIngredient(ing: Ingredient): List<RecipePreview>
}