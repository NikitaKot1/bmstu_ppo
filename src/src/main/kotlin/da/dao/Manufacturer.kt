package da.dao

import bl.entities.Manufacturer
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Manufacturers : IntIdTable("manufacturer") {
    val login = text("login")
    val password = text("password")
}

class ManufacturerTable(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ManufacturerTable>(Manufacturers)

    var login by Manufacturers.login
    var password by Manufacturers.password

    val recipes by RecipeTable referrersOn Recipes.owner
}

fun ManufacturerTable.toEntity(): Manufacturer = Manufacturer(
    id = this.id.value.toULong(),
    login = this.login,
    password = this.password
)

