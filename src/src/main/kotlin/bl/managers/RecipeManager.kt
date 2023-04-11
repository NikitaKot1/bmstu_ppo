package bl.managers

import bl.entities.Comment
import bl.entities.Recipe
import bl.entities.RecipePreview
import bl.entities.toRecipePreview
import bl.exceptions.AlreadyExistingRecipeException
import bl.exceptions.NotExistingRecipeException
import bl.exceptions.NotExistingUserException
import bl.repositories.IRecipeRepository
import bl.repositories.IRepository
import java.time.LocalDateTime

object RecipeManager : ICRUDManager<Recipe> {
    private lateinit var repository: IRecipeRepository

    override fun registerRepository(repository: IRepository<Recipe>) {
        this.repository = repository as IRecipeRepository
    }

    override fun create(obj: Recipe) {
        if (!isUniq(obj)) throw AlreadyExistingRecipeException("Recipe already exists")

        repository.create(obj)
    }

    override fun read(id: ULong): Recipe {
        if (!isExist(id)) throw NotExistingRecipeException("Recipe not exists")

        return repository.read(id)
    }

    override fun update(obj: Recipe) {
        if (!isExist(obj.id)) throw NotExistingRecipeException("Recipe not exists")

        repository.update(obj)
    }

    override fun delete(id: ULong) {
        if (!isExist(id)) throw NotExistingRecipeException("Recipe not exists")

        repository.delete(id)
    }

    override fun getAll(): List<Recipe> = repository.getAll()

    override fun isUniq(obj: Recipe) = true

    override fun isExist(id: ULong): Boolean = repository.exists(id)

    fun previewData(id: ULong): RecipePreview {
        isExist(id)
        return repository.read(id).toRecipePreview()
    }

    fun addToFavorite(id: ULong, userID: ULong) {
        if (!isExist(id)) throw NotExistingRecipeException("Recipe not exist")
        if (!ManufacturerManager.isExist(userID)) throw NotExistingUserException("User not exist")

        repository.addToFavorite(id, userID)
    }

    fun isOwner(id: ULong, userID: ULong): Boolean {
        if (!isExist(id)) throw NotExistingRecipeException("Recipe not exist")
        if (!ManufacturerManager.isExist(userID)) throw NotExistingUserException("User not exist")

        return repository.getOwnerID(id) == userID
    }

    fun addComment(userID: ULong, text: String, recipeID: ULong) {
        if (!ManufacturerManager.isExist(userID)) throw NotExistingUserException("User not exists")
        if (!isExist(recipeID)) throw NotExistingRecipeException("Recipe not exists")

        val owner = ManufacturerManager.read(userID)
        var comment = Comment(0u, LocalDateTime.now(), text, owner)

        repository.addComment(userID, recipeID, comment)
    }
}