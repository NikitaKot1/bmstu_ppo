package bl.repositories

import bl.entities.RecipePreview
import bl.entities.User

interface IConsumerRepository : IRepository<User> {
    fun isLoginNotExist(login: String): Boolean
    fun getByLogin(login: String): User?
    fun getSavedMarks(userID: ULong): List<RecipePreview>
}