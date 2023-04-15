package da.dao

import bl.entities.Recipe
import bl.entities.RecipePreview
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime

object Recipes : IntIdTable("recipe") {
    val name = text("name")
    val description = text("description")
    val time = integer("time").check { it.greaterEq(0) }
    val servingsNum = integer("servings_num").autoIncrement()
    val date = datetime("date")
    val owner = reference("ownerid", Manufacturers)
}

class RecipeTable(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RecipeTable>(Recipes)

    var name by Recipes.name
    var description by Recipes.description
    var time by Recipes.time
    var date by Recipes.date

    var ownerId by Recipes.owner
    var owner by ManufacturerTable referencedOn Recipes.owner

    val comments by CommentTable referrersOn Comments.recipe

    var savedUsers by ConsumerTable via SavedRecipes
}

class RecipePreviewTable(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RecipePreviewTable>(Recipes)

    var name by Recipes.name
    var description by Recipes.description
    var time by Recipes.time
    var servingsNum by Recipes.servingsNum
}

fun RecipeTable.toEntity(): Recipe = Recipe(
    id = this.id.value.toULong(),
    name = this.name,
    description = this.description,
    date = this.date,
    comments = this.comments.toList().map { it.toEntity() },
    owner = this.owner.toEntity(),
    ingredients = listOf()
)

fun RecipePreviewTable.toEntity(): RecipePreview = RecipePreview(
    id = this.id.value.toULong(),
    name = this.name,
    description = this.description
)