package da.repositories

import bl.entities.Consumer
import bl.entities.RecipePreview
import bl.repositories.IConsumerRepository
import da.dao.*
import da.exeption.NotFoundInDBException
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction


class PgConsumerRepository : IConsumerRepository {
    override fun create(obj: Consumer) {
        transaction {
            val dao = ConsumerTable.new {
                login = obj.login
                password = obj.password
            }
        }
    }

    override fun read(id: ULong): Consumer = transaction {
        val obj = ConsumerTable.findById(id.toInt())
        obj?.toEntity() ?: throw NotFoundInDBException("User with id = $id not found")
    }

    override fun update(obj: Consumer) {
        transaction {
            val dao =
                ConsumerTable.findById(obj.id.toInt())
                    ?: throw NotFoundInDBException("User with id = ${obj.id} not found")
            dao.login = obj.login
            dao.password = obj.password
        }
    }

    override fun delete(id: ULong) {
        transaction {
            ConsumerTable.findById(id.toInt())?.delete() ?: throw NotFoundInDBException("User with id = $id not found")
        }
    }

    override fun getAll(): List<Consumer> = transaction {
        ConsumerTable.all().toList().map { it.toEntity() }
    }

    override fun exists(id: ULong): Boolean = transaction { ConsumerTable.findById(id.toInt()) != null }

    override fun isLoginNotExist(login: String): Boolean =
        transaction { ConsumerTable.find { Consumers.login eq login }.firstOrNull() } == null


    override fun getByLogin(login: String): Consumer =
        ConsumerTable.find { Consumers.login eq login }.firstOrNull()?.toEntity()
            ?: throw NotFoundInDBException("User with login = $login not found")

    override fun getSavedMarks(userID: ULong): List<RecipePreview> = transaction {
        val query =
            Recipes.innerJoin(SavedRecipes).innerJoin(Consumers).slice(Recipes.columns)
                .select(Consumers.id eq userID.toInt())
        RecipePreviewTable.wrapRows(query).map { it.toEntity() }
    }
}
