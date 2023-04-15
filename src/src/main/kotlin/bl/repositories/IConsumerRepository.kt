package bl.repositories

import bl.entities.Consumer
import bl.entities.RecipePreview

interface IConsumerRepository : IRepository<Consumer> {
    fun isLoginNotExist(login: String): Boolean
    fun getByLogin(login: String): Consumer?
    fun getSavedMarks(userID: ULong): List<RecipePreview>
}