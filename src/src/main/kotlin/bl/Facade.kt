package bl

import bl.entities.*
import bl.managers.*
import bl.repositories.factory.IRepositoryFactory

class Facade(private var repositoryFactory: IRepositoryFactory) {
    init {
        CommentManager.registerRepository(repositoryFactory.createCommentRepository())
        IngredientManager.registerRepository(repositoryFactory.createIngredientRepository())
        RecipeManager.registerRepository(repositoryFactory.createRecipeRepository())
        ManufacturerManager.registerRepository(repositoryFactory.createManufacturerRepository())
        ConsumerManager.registerRepository(repositoryFactory.createConsumerRepository())
        SearchService.registerRepository(repositoryFactory.createRecipeRepository())
    }

    //Comment
    fun updateComment(obj: Comment) {
        CommentManager.update(obj)
    }

    fun deleteComment(id: ULong) {
        CommentManager.delete(id)
    }

    //Ingredient
    fun createIngredient(obj: Ingredient) {
        IngredientManager.create(obj)
    }

    fun updateIngredient(obj: Ingredient) {
        IngredientManager.update(obj)
    }

    fun deleteIngredient(id: ULong) {
        IngredientManager.delete(id)
    }

    fun findByNameIngredient(name: String) = IngredientManager.findByName(name)

    fun getAllIngredients(): List<Ingredient> = IngredientManager.getAll()

    //Recipe
    fun createRecipe(obj: Recipe) {
        RecipeManager.create(obj)
    }

    fun getRecipe(id: ULong): Recipe = RecipeManager.read(id)
    fun updateRecipe(obj: Recipe) {
        RecipeManager.update(obj)
    }

    fun deleteRecipe(id: ULong) {
        RecipeManager.delete(id)
    }

    fun addToFavorite(userID: ULong, recipeID: ULong) {
        RecipeManager.addToFavorite(recipeID, userID)
    }

    fun addComment(userID: ULong, text: String, recipeID: ULong) {
        RecipeManager.addComment(userID, text, recipeID)
    }

    fun isOwner(userID: ULong, recipeID: ULong): Boolean = RecipeManager.isOwner(userID, recipeID)

    //User
    fun createManufacturer(obj: User) {
        ManufacturerManager.create(obj)
    }

    fun createConsumer(obj: User) {
        ConsumerManager.create(obj)
    }

    fun getManufacturer(id: ULong) = ManufacturerManager.read(id)
    fun getConsumer(id: ULong) = ConsumerManager.read(id)


    fun updateConsumer(obj: User) {
        ConsumerManager.update(obj)
    }

    fun updateManufacturer(obj: User) {
        ManufacturerManager.update(obj)
    }

    fun deleteManufacturer(id: ULong) {
        ManufacturerManager.delete(id)
    }

    fun deleteConsumer(id: ULong) {
        ConsumerManager.delete(id)
    }

    fun getAllManufacturer(): List<User> = ManufacturerManager.getAll()


    //Account
    fun registerManufacturer(login: String, password: String) {
        AccountService.registerManufacturer(login, password)
    }

    fun registerConsumer(login: String, password: String) {
        AccountService.registerConsumer(login, password)
    }

    fun logInConsumer(login: String, password: String): User = AccountService.logINConsumer(login, password)
    fun logInManufacturer(login: String, password: String): User = AccountService.logINManufacturer(login, password)


    //Search
    fun getFeedSortedByDate(): List<RecipePreview> = SearchService.getSortedByDate()
    fun getFeedSortedByLike(): List<RecipePreview> = SearchService.getSortedByLike()
    fun searchByIngredient(ing: Ingredient): List<RecipePreview> = SearchService.searchByIngredient(ing)
}