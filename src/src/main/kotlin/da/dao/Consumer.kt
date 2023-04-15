package da.dao

import bl.entities.Consumer
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Consumers : IntIdTable("user") {
    val login = text("login")
    val password = text("password")
}

class ConsumerTable(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ConsumerTable>(Consumers)

    var login by Consumers.login
    var password by Consumers.password

    val comments by CommentTable referrersOn Comments.autor

    var savedRecipes by RecipeTable via SavedRecipes
}

fun ConsumerTable.toEntity(): Consumer = Consumer(
    id = this.id.value.toULong(),
    login = this.login,
    password = this.password
)

