package bl.repository.factory

import bl.repositories.factory.IRepositoryFactory
import bl.repository.*

class MockRepositoryFactory : IRepositoryFactory {
    override fun createCommentRepository(): MockCommentRepository = MockCommentRepository()

    override fun createIngredientRepository(): MockIngredientRepository = MockIngredientRepository()

    override fun createRecipeRepository(): MockMarkRepository = MockMarkRepository()

    override fun createManufacturerRepository(): MockManufacturerRepository = MockManufacturerRepository()

    override fun createConsumerRepository(): MockCunsumerRepository = MockCunsumerRepository()
}