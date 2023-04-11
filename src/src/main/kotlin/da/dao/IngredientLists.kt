package da.dao

import bl.entities.Ingredient
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object IngredientLists : IntIdTable("ingredient_list") {
    val amount = integer("amount").check { it.greater(0) }
    val ingredient = reference("ingredientid", Ingredients)
}

class IngredientListTable(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<IngredientListTable>(IngredientLists)

    var amount by IngredientLists.amount
    var ingredientId by IngredientLists.ingredient

    var ingredient by IngredientTable referencedOn IngredientLists.ingredient
}

data class IngredientInfo(
    val id: ULong,
    var amount: UInt,
    var ingredient: Ingredient
)

fun IngredientListTable.toEntity(): IngredientInfo = IngredientInfo(
    id = this.id.value.toULong(),
    amount = this.amount.toUInt(),
    ingredient = this.ingredient.toEntity()
)

fun IngredientListTable.toIngredient(): Ingredient {
    val obj = this.ingredient.toEntity()
    obj.amount = this.amount.toUInt()
    return obj
}
