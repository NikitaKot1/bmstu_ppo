package bl.repository

import bl.entities.RecipePreview
import bl.entities.User
import bl.entities.toRecipePreview
import bl.repositories.IManufacturerRepository

class MockManufacturerRepository : IManufacturerRepository {
    override fun create(obj: User) {
        ManufacturerMockData += obj
    }

    override fun read(id: ULong): User = ManufacturerMockData[id.toInt()]

    override fun update(obj: User) {
        ManufacturerMockData[obj.id.toInt()] = obj
    }

    override fun delete(id: ULong) {
        ManufacturerMockData[id.toInt()].password = "deleted"
    }

    override fun getAll(): List<User> = ManufacturerMockData.toList()

    override fun exists(id: ULong): Boolean = ManufacturerMockData.find { x -> x.id == id } != null

    override fun isLoginNotExist(login: String): Boolean = ManufacturerMockData.find { x -> x.login == login } == null

    override fun getByLogin(login: String): User? = ManufacturerMockData.find { x -> x.login == login }

    override fun getMarks(userID: ULong): List<RecipePreview> {
        val lst = SavedRecipesMockData.filter { x -> x.first == userID }.map { x -> x.first }
        return RecipeMockData.filter { x -> x.id in lst }.map { it.toRecipePreview() }
    }
}