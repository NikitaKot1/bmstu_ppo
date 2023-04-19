package da.repositories


import bl.entities.Manufacturer
import bl.entities.RecipePreview
import bl.repositories.IManufacturerRepository
import da.dao.*
import da.exeption.NotFoundInDBException
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction


class PgManufacturerRepository : IManufacturerRepository {
    override fun create(obj: Manufacturer) {
        transaction {
            val dao = ManufacturerTable.new {
                login = obj.login
                password = obj.password
            }
        }
    }

    override fun read(id: ULong): Manufacturer = transaction {
        val obj = ManufacturerTable.findById(id.toInt())
        obj?.toEntity() ?: throw NotFoundInDBException("Manufacturer with id = $id not found")
    }

    override fun update(obj: Manufacturer) {
        transaction {
            val dao =
                ManufacturerTable.findById(obj.id.toInt())
                    ?: throw NotFoundInDBException("Manufacturer with id = ${obj.id} not found")
            dao.login = obj.login
            dao.password = obj.password
        }
    }

    override fun delete(id: ULong) {
        transaction {
            ManufacturerTable.findById(id.toInt())?.delete()
                ?: throw NotFoundInDBException("Manufacturer with id = $id not found")
        }
    }

    override fun getAll(): List<Manufacturer> = transaction {
        ManufacturerTable.all().toList().map { it.toEntity() }
    }

    override fun exists(id: ULong): Boolean = transaction { ManufacturerTable.findById(id.toInt()) != null }

    override fun isLoginNotExist(login: String): Boolean =
        transaction { ManufacturerTable.find { Manufacturers.login eq login }.firstOrNull() } == null

    override fun getByLogin(login: String): Manufacturer = transaction {
        ManufacturerTable.find { Manufacturers.login eq login }.firstOrNull()?.toEntity()
            ?: throw NotFoundInDBException("Manufacturer with login = $login not found")
    }

    override fun getMarks(userID: ULong): List<RecipePreview> = transaction {
        val query = Recipes.innerJoin(Manufacturers).slice(Recipes.columns).select(Manufacturers.id eq userID.toInt())
        RecipePreviewTable.wrapRows(query).map { it.toEntity() }
    }
}
