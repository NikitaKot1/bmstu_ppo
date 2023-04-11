package bl.repository

import bl.entities.*
import bl.repositories.IRecipeRepository

class MockMarkRepository : IRecipeRepository {
    override fun getOwnerID(id: ULong): ULong = RecipeMockData[id.toInt()].owner.id
    override fun addToFavorite(id: ULong, userID: ULong) {
        SavedRecipesMockData += Pair<ULong, ULong>(userID, id)
    }

    override fun addComment(userId: ULong, recipeId: ULong, comment: Comment) {
        CommentMockData += comment
        RecipeMockData[recipeId.toInt()].comments += comment
    }

    override fun getSortedByDate(): List<RecipePreview> =
        RecipeMockData.sortedBy { it.date }.map { it.toRecipePreview() }

    override fun getSortedByLike(): List<RecipePreview> = RecipeMockData.sortedBy { it.id }.map { it.toRecipePreview() }

    override fun searchByIngredient(ing: Ingredient): List<RecipePreview> {
        val ret = mutableListOf<RecipePreview>()
        for (rep in RecipeMockData)
            if (ing in rep.ingredients)
                ret += rep.toRecipePreview()
        return ret
    }

    override fun create(obj: Recipe) {
        RecipeMockData += obj
    }

    override fun read(id: ULong): Recipe = RecipeMockData[id.toInt()]

    override fun update(obj: Recipe) {
        RecipeMockData[obj.id.toInt()] = obj
    }

    override fun delete(id: ULong) {
        RecipeMockData[id.toInt()].name = "deleted"
    }

    override fun getAll(): List<Recipe> = RecipeMockData.toList()

    override fun exists(id: ULong): Boolean = RecipeMockData.find { x -> x.id == id } != null

    override fun getAllPreview(): List<RecipePreview> = RecipeMockData.map { it.toRecipePreview() }

}