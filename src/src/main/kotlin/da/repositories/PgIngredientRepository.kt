package da.repositories

import bl.entities.Ingredient
import bl.repositories.IIngredientRepository
import da.dao.IngredientTable
import da.dao.Ingredients
import da.dao.toEntity
import da.exeption.NotFoundInDBException
import org.jetbrains.exposed.sql.transactions.transaction

class PgIngredientRepository : IIngredientRepository {
    override fun create(obj: Ingredient) {
        transaction {
            val dao = IngredientTable.new {
                name = obj.name
                type = obj.type
            }
        }
    }

    override fun read(id: ULong): Ingredient = transaction {
        val obj = IngredientTable.findById(id.toInt())
        obj?.toEntity() ?: throw NotFoundInDBException("Ingredient with id = $id not found")
    }

    override fun update(obj: Ingredient) {
        transaction {
            val dao = IngredientTable.findById(obj.id.toInt())
                ?: throw NotFoundInDBException("Ingredient with id = ${obj.id} not found")
            dao.name = obj.name
            dao.type = obj.type
        }
    }

    override fun delete(id: ULong) {
        transaction {
            IngredientTable.findById(id.toInt())?.delete()
                ?: throw NotFoundInDBException("Ingredient with id = $id not found")
        }
    }

    override fun getAll(): List<Ingredient> = transaction {
        IngredientTable.all().map { it.toEntity() }
    }

    override fun exists(id: ULong): Boolean = transaction { IngredientTable.findById(id.toInt()) != null }

    override fun isNameNotExist(name: String): Boolean =
        transaction { IngredientTable.find { Ingredients.name eq name }.firstOrNull() } == null

    override fun findByName(name: String): Ingredient =
        transaction {
            IngredientTable.find { Ingredients.name eq name }.firstOrNull()?.toEntity()
                ?: throw NotFoundInDBException("Ingredient with name = $name not found")
        }
}