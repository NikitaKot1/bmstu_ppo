package bl.repositories

import bl.entities.Manufacturer
import bl.entities.RecipePreview

interface IManufacturerRepository : IRepository<Manufacturer> {
    fun isLoginNotExist(login: String): Boolean
    fun getByLogin(login: String): Manufacturer?
    fun getMarks(userID: ULong): List<RecipePreview>
}