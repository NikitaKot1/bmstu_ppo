package bl.repository

import bl.entities.RecipePreview
import bl.entities.User
import bl.entities.toRecipePreview
import bl.repositories.IConsumerRepository

class MockCunsumerRepository : IConsumerRepository {
    override fun create(obj: User) {
        ConsumerMockData += obj
    }

    override fun read(id: ULong): User = ConsumerMockData[id.toInt()]

    override fun update(obj: User) {
        ConsumerMockData[obj.id.toInt()] = obj
    }

    override fun delete(id: ULong) {
        ConsumerMockData[id.toInt()].password = "deleted"
    }

    override fun getAll(): List<User> = ConsumerMockData.toList()

    override fun exists(id: ULong): Boolean = ConsumerMockData.find { x -> x.id == id } != null

    override fun isLoginNotExist(login: String): Boolean = ConsumerMockData.find { x -> x.login == login } == null

    override fun getByLogin(login: String): User? = ConsumerMockData.find { x -> x.login == login }

    override fun getSavedMarks(userID: ULong): List<RecipePreview> {
        val lst = SavedRecipesMockData.filter { x -> x.first == userID }.map { x -> x.first }
        return RecipeMockData.filter { x -> x.id in lst }.map { it.toRecipePreview() }
    }
}