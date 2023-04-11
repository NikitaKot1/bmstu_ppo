package bl.managers

import bl.entities.Ingredient
import bl.entities.RecipePreview
import bl.repositories.IRecipeRepository

object SearchService {
    private lateinit var repository: IRecipeRepository

    fun registerRepository(repository: IRecipeRepository) {
        this.repository = repository
    }

    fun getSortedByDate(): List<RecipePreview> = repository.getSortedByDate()
    fun getSortedByLike(): List<RecipePreview> = repository.getSortedByLike()

    fun searchByIngredient(ing: Ingredient): List<RecipePreview> = repository.searchByIngredient(ing)
}