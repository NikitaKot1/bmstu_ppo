package bl.repository

import bl.entities.Consumer
import bl.entities.RecipePreview
import bl.entities.toRecipePreview
import bl.repositories.IConsumerRepository

class MockCunsumerRepository : IConsumerRepository {
    override fun create(obj: Consumer) {
        ConsumerMockData += obj
    }

    override fun read(id: ULong): Consumer = ConsumerMockData[id.toInt()]

    override fun update(obj: Consumer) {
        ConsumerMockData[obj.id.toInt()] = obj
    }

    override fun delete(id: ULong) {
        ConsumerMockData[id.toInt()].password = "deleted"
    }

    override fun getAll(): List<Consumer> = ConsumerMockData.toList()

    override fun exists(id: ULong): Boolean = ConsumerMockData.find { x -> x.id == id } != null

    override fun isLoginNotExist(login: String): Boolean = ConsumerMockData.find { x -> x.login == login } == null

    override fun getByLogin(login: String): Consumer? = ConsumerMockData.find { x -> x.login == login }

    override fun getSavedMarks(userID: ULong): List<RecipePreview> {
        val lst = SavedRecipesMockData.filter { x -> x.first == userID }.map { x -> x.first }
        return RecipeMockData.filter { x -> x.id in lst }.map { it.toRecipePreview() }
    }
}