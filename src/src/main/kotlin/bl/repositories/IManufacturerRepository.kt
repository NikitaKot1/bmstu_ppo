package bl.repositories

import bl.entities.RecipePreview
import bl.entities.User

interface IManufacturerRepository : IRepository<User> {
    fun isLoginNotExist(login: String): Boolean
    fun getByLogin(login: String): User?
    fun getMarks(userID: ULong): List<RecipePreview>
}