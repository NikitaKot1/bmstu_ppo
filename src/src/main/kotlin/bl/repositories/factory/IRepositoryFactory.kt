package bl.repositories.factory

import bl.repositories.*

interface IRepositoryFactory {
    fun createCommentRepository(): ICommentRepository
    fun createIngredientRepository(): IIngredientRepository
    fun createRecipeRepository(): IRecipeRepository
    fun createManufacturerRepository(): IManufacturerRepository
    fun createConsumerRepository(): IConsumerRepository

}