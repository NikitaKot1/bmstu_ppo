package da.repositories

import bl.entities.Comment
import bl.entities.Ingredient
import bl.entities.Recipe
import bl.entities.RecipePreview
import bl.repositories.IRecipeRepository
import da.dao.*
import da.exeption.NotFoundInDBException
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction


class PgRecipeRepository : IRecipeRepository {
    override fun create(obj: Recipe) {
        transaction {

            RecipeTable.new {
                name = obj.name
                description = obj.description
                date = obj.date
                ownerId = EntityID(obj.owner.id.toInt(), Manufacturers)
            }
        }
    }

    override fun read(id: ULong): Recipe {
        val dao = transaction {
            RecipeTable.findById(id.toInt())
                ?: throw NotFoundInDBException("Recipe with id = $id not found")
        }
        val res = dao.toEntity()
        return res
    }

    override fun update(obj: Recipe) {
        transaction {
            val dao = RecipeTable.findById(obj.id.toInt())
                ?: throw NotFoundInDBException("Recipe with id = ${obj.id} not found")
            dao.name = obj.name
            dao.description = obj.description
            dao.date = obj.date
            dao.ownerId = EntityID(obj.owner.id.toInt(), Manufacturers)
        }
    }

    override fun delete(id: ULong) {
        transaction {
            val dao = RecipeTable.findById(id.toInt()) ?: throw NotFoundInDBException("Recipe with id = $id not found")
            dao.delete()
        }
    }

    override fun getAll(): List<Recipe> = transaction {
        RecipeTable.all().map { it.toEntity() }
    }

    override fun exists(id: ULong): Boolean = transaction { RecipeTable.findById(id.toInt()) != null }

    override fun getAllPreview(): List<RecipePreview> = transaction {
        RecipePreviewTable.all().map { it.toEntity() }
    }

    override fun getOwnerID(id: ULong): ULong = transaction {
        RecipeTable.findById(id.toInt())?.ownerId?.value?.toULong()
            ?: throw NotFoundInDBException("Recipe with owner id = $id not found")
    }


    override fun addToFavorite(id: ULong, userID: ULong) {
        transaction {
            SavedRecipeTable.new {
                userId = EntityID(userID.toInt(), Consumers)
                recipeId = EntityID(id.toInt(), Recipes)
            }
        }
    }

    override fun addComment(userId: ULong, recipeId: ULong, comment: Comment) {
        transaction {
            CommentTable.new {
                date = comment.date
                text = comment.text
                autorId = EntityID(userId.toInt(), Consumers)
                this.recipeId = EntityID(recipeId.toInt(), Recipes)
            }
        }
    }

    override fun getSortedByDate(): List<RecipePreview> = transaction {
        val query = Recipes.selectAll().orderBy(Recipes.date to SortOrder.DESC)
        RecipePreviewTable.wrapRows(query).map { it.toEntity() }
    }
    
    override fun getSortedByLike(): List<RecipePreview> {
        TODO()
    }

    override fun searchByIngredient(ing: Ingredient): List<RecipePreview> = transaction {
        val query = Recipes.select(where = )
    }
}