package da.dao

import bl.entities.Ingredient
import bl.entities.IngredientType
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.postgresql.util.PGobject

class PGEnum<T : Enum<T>>(enumTypeName: String, enumValue: T?) : PGobject() {
    init {
        value = enumValue?.name
        type = enumTypeName
    }
}

object Ingredients : IntIdTable("ingredient") {
    val name = text("name")
    val type = customEnumeration(
        "type",
        "ing_type",
        { value -> IngredientType.valueOf(value as String) },
        { PGEnum("ing_type", it) })
}

class IngredientTable(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<IngredientTable>(Ingredients)

    var name by Ingredients.name
    var type by Ingredients.type

    val list by IngredientListTable referrersOn IngredientLists.ingredient
}

fun IngredientTable.toEntity(): Ingredient = Ingredient(
    id = this.id.value.toULong(),
    name = this.name,
    type = this.type,
    amount = 1u
)