package bl

import bl.entities.*
import bl.exceptions.*
import bl.repository.*
import bl.repository.factory.MockRepositoryFactory
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime
import kotlin.test.assertEquals

val facade = Facade(MockRepositoryFactory())

class SearchTest {
    @Test
    @DisplayName("Search by ingredient")
    fun searchByIngredient() {
        val rez = facade.searchByIngredient(IngredientMockData[0])
        val expected = RecipeMockData.map { it.toRecipePreview() }
        assertEquals(expected, rez)
    }
}

class CommentTest {
    @Test
    @DisplayName("Update Comment")
    fun updateComment() {
        val expected = CommentMockData[1]
        expected.text = "fixed1"
        facade.updateComment(expected)
        val actual = CommentMockData[1]

        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("Update Comment, not exist")
    fun updateCommentNotExist() {
        val tmp = Comment(9u, LocalDateTime.now(), "text1", ConsumerMockData[1])

        assertThrows<NotExistingCommentException> { facade.updateComment(tmp) }
    }

    @Test
    @DisplayName("Delete Comment")
    fun deleteComment() {
        val expected = "deleted"
        facade.deleteComment(2u)
        val actual = CommentMockData[2].text

        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("Delete Comment, not exist")
    fun deleteCommentNotExist() {
        assertThrows<NotExistingCommentException> { facade.deleteComment(9u) }
    }
}

class IngredientTest {
    @Test
    @DisplayName("Create Ingredient")
    fun createIngredient() {
        val expected = Ingredient(5u, "name5", IngredientType.HOP, 987u)
        facade.createIngredient(expected)
        val actual = IngredientMockData[5]

        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("Create Ingredient, not uniq")
    fun createIngredientNotUniq() {
        val tmp = Ingredient(5u, "name0", IngredientType.YEAST, 987u)

        assertThrows<AlreadyExistingIngredientException> { facade.createIngredient(tmp) }
    }

    @Test
    @DisplayName("Update Ingredient")
    fun updateIngredient() {
        val expected = IngredientMockData[1]
        expected.name = "fixed1"
        facade.updateIngredient(expected)
        val actual = IngredientMockData[1]

        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("Update Ingredient, not exist")
    fun updateIngredientNotExist() {
        val tmp = Ingredient(9u, "name4", IngredientType.HOP, 987u)

        assertThrows<NotExistingIngredientException> { facade.updateIngredient(tmp) }
    }

    @Test
    @DisplayName("Delete Ingredient")
    fun deleteIngredient() {
        val expected = "deleted"
        facade.deleteIngredient(2u)
        val actual = IngredientMockData[2].name

        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("Delete Ingredient, not exist")
    fun deleteIngredientNotExist() {
        assertThrows<NotExistingIngredientException> { facade.deleteIngredient(9u) }
    }

    @Test
    @DisplayName("Find bu name Ingredient")
    fun findByNameIngredient() {
        val expected = IngredientMockData[0]
        val actual = facade.findByNameIngredient(IngredientMockData[0].name)

        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("Find bu name Ingredient, not exist")
    fun findByNameIngredientNotExist() {
        assertThrows<NotExistingIngredientException> { facade.findByNameIngredient("name9") }
    }

    @Test
    @DisplayName("Get all Ingredients")
    fun getAllIngredients() {
        val expected = IngredientMockData.toList()
        val actual = facade.getAllIngredients()

        assertEquals(expected, actual)
    }
}

class RecipeTest {
    @Test
    @DisplayName("Create Recipe")
    fun createRecipe() {
        val expected = Recipe(
            5u,
            "name5",
            "description5",
            LocalDateTime.now(),
            CommentMockData.toList(),
            ManufacturerMockData[4],
            IngredientMockData.toList()
        )
        facade.createRecipe(expected)
        val actual = RecipeMockData[6]

        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("Get Recipe")
    fun getRecipe() {
        val actual = facade.getRecipe(0u)
        val expected = RecipeMockData[0]

        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("Get Recipe, not exist")
    fun getRecipeNotExist() {
        assertThrows<NotExistingRecipeException> { facade.getRecipe(9u) }
    }

    @Test
    @DisplayName("Update Recipe")
    fun updateRecipe() {
        val expected = RecipeMockData[1]
        expected.name = "fixed1"
        facade.updateRecipe(expected)
        val actual = RecipeMockData[1]

        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("Update Recipe, not exist")
    fun updateRecipeNotExist() {
        val tmp = Recipe(
            9u,
            "",
            "description9",
            LocalDateTime.now(),
            CommentMockData.toList(),
            ManufacturerMockData[4],
            IngredientMockData.toList()
        )

        assertThrows<NotExistingRecipeException> { facade.updateRecipe(tmp) }
    }

    @Test
    @DisplayName("Delete Recipe")
    fun deleteRecipe() {
        val expected = "deleted"
        facade.deleteRecipe(2u)
        val actual = RecipeMockData[2].name

        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("Delete Recipe, not exist")
    fun deleteRecipeNotExist() {
        assertThrows<NotExistingRecipeException> { facade.deleteRecipe(9u) }
    }

    @Test
    @DisplayName("Add to favorite")
    fun addToFavorite() {
        val expected = Pair<ULong, ULong>(3u, 3u)
        facade.addToFavorite(expected.first, expected.second)
        val actual = SavedRecipesMockData[5]

        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("Add to favorite, no recipe")
    fun addToFavoriteNoRecipe() {
        assertThrows<NotExistingRecipeException> { facade.addToFavorite(4u, 9u) }
    }

    @Test
    @DisplayName("Add to favorite, no user")
    fun addToFavoriteNoUser() {
        assertThrows<NotExistingUserException> { facade.addToFavorite(9u, 4u) }
    }


    @Test
    @DisplayName("Add comment")
    fun addComment() {
        val expected = Comment(0u, LocalDateTime.now(), "text5", ConsumerMockData[1])
        facade.addComment(1u, "text5", 1u)
        val actual1 = CommentMockData[5]
        val actual2 = RecipeMockData[1].comments[5]

        assertAll("Add comment asserts", { assertEquals(expected, actual1) }, { assertEquals(expected, actual2) })
    }

    @Test
    @DisplayName("Add comment, no user")
    fun addCommentNoUser() {
        assertThrows<NotExistingUserException> { facade.addComment(9u, "text5", 1u) }
    }

    @Test
    @DisplayName("Add comment, no recipe")
    fun addCommentNoRecipe() {
        assertThrows<NotExistingRecipeException> { facade.addComment(1u, "text5", 9u) }
    }

    @Test
    @DisplayName("Is owner")
    fun isOwner() {
        val actual = facade.isOwner(0u, 0u)
        val expected = true

        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("Isn't owner")
    fun isNotOwner() {
        val actual = facade.isOwner(1u, 0u)
        val expected = false

        assertEquals(expected, actual)
    }
}

class ConsumerTest {
    @Test
    @DisplayName("Create Consumer")
    fun createConsumer() {
        val expected = Consumer(6u, "login6", "password6")
        facade.createConsumer(expected)
        val actual = ConsumerMockData.find { it.login == "login6" }

        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("Create Consumer, not uniq")
    fun createConsumerNotUniq() {
        val tmp = Consumer(5u, "login0", "password0")

        assertThrows<AlreadyExistingUserException> { facade.createConsumer(tmp) }
    }

    @Test
    @DisplayName("Get Consumer")
    fun getConsumer() {
        val expected = ConsumerMockData[0]
        val actual = facade.getConsumer(0u)

        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("Get Consumer, not exist")
    fun getConsumerNotExist() {
        assertThrows<NotExistingUserException> { facade.getConsumer(9u) }
    }

    @Test
    @DisplayName("Update Consumer")
    fun updateConsumer() {
        val expected = ConsumerMockData[1]
        expected.password = "fixed1"
        facade.updateConsumer(expected)
        val actual = ConsumerMockData[1]

        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("Update Consumer, not exist")
    fun updateConsumerNotExist() {
        val tmp = Consumer(9u, "login1", "password1")

        assertThrows<NotExistingUserException> { facade.updateConsumer(tmp) }
    }

    @Test
    @DisplayName("Delete Consumer")
    fun deleteConsumer() {
        val expected = "deleted"
        facade.deleteConsumer(2u)
        val actual = ConsumerMockData[2].password

        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("Delete Consumer, not exist")
    fun deleteConsumerNotExist() {
        assertThrows<NotExistingUserException> { facade.deleteConsumer(9u) }
    }
}

class ManufacturerTest {
    @Test
    @DisplayName("Create Manufacturer")
    fun createUser() {
        val expected = Consumer(6u, "login6", "password6")
        facade.createManufacturer(expected)
        val actual = ManufacturerMockData.find { it.login == "login6" }

        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("Create Manufacturer, not uniq")
    fun createUserNotUniq() {
        val tmp = Consumer(5u, "login0", "password0")

        assertThrows<AlreadyExistingUserException> { facade.createManufacturer(tmp) }
    }

    @Test
    @DisplayName("Get Manufacturer")
    fun getManufacturer() {
        val expected = ManufacturerMockData[0]
        val actual = facade.getManufacturer(0u)

        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("Get Manufacturer, not exist")
    fun getManufacturerNotExist() {
        assertThrows<NotExistingUserException> { facade.getManufacturer(9u) }
    }

    @Test
    @DisplayName("Update Manufacturer")
    fun updateManufacturer() {
        val expected = ManufacturerMockData[1]
        expected.password = "fixed1"
        facade.updateManufacturer(expected)
        val actual = ManufacturerMockData[1]

        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("Update Manufacturer, not exist")
    fun updateManufacturerNotExist() {
        val tmp = Consumer(9u, "login1", "password1")

        assertThrows<NotExistingUserException> { facade.updateManufacturer(tmp) }
    }

    @Test
    @DisplayName("Delete Manufacturer")
    fun deleteUser() {
        val expected = "deleted"
        facade.deleteManufacturer(2u)
        val actual = ManufacturerMockData[2].password

        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("Delete Manufacturer, not exist")
    fun deleteManufacturerNotExist() {
        assertThrows<NotExistingUserException> { facade.deleteManufacturer(9u) }
    }

    @Test
    @DisplayName("Get all Manufacturer")
    fun getAllManufacturer() {
        val expected = ManufacturerMockData.toList()
        val actual = facade.getAllManufacturer()

        assertEquals(expected, actual)
    }
}

class ManufacturerAccountTest {
    @Test
    @DisplayName("Register Manufacturer")
    fun register() {
        val login = "login5"
        val password = "password5"

        val expected = Consumer(0u, login, password)
        facade.registerManufacturer(login, password)
        val actual = ManufacturerMockData.find { it.login == login }

        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("Register, not uniq")
    fun registerNotUniq() {
        val login = "login0"
        val password = "password0"

        assertThrows<AlreadyExistingUserException> { facade.registerManufacturer(login, password) }
    }

    @Test
    @DisplayName("LogIn")
    fun logIn() {
        val res = facade.logInManufacturer(ManufacturerMockData[0].login, ManufacturerMockData[0].password)

        assertEquals(ManufacturerMockData[0], res)
    }

    @Test
    @DisplayName("LogIn fail")
    fun logInFail() {
        assertThrows<LogInFailedException> { facade.logInManufacturer(ManufacturerMockData[0].login, "lolololo") }
    }

    @Test
    @DisplayName("LogIn, no User")
    fun logInNoUser() {
        assertThrows<NotExistingUserException> { facade.logInManufacturer("rtyu", "lololololo") }
    }
}


class ConsumerAccountTest {
    @Test
    @DisplayName("Register Consumer")
    fun register() {
        val login = "login5"
        val password = "password5"

        val expected = Consumer(0u, login, password)
        facade.registerConsumer(login, password)
        val actual = ConsumerMockData.find { it.login == login }

        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("Register, not uniq")
    fun registerNotUniq() {
        val login = "login0"
        val password = "password0"

        assertThrows<AlreadyExistingUserException> { facade.registerConsumer(login, password) }
    }

    @Test
    @DisplayName("LogIn")
    fun logIn() {
        val res = facade.logInConsumer(ConsumerMockData[0].login, ConsumerMockData[0].password)

        assertEquals(ConsumerMockData[0], res)
    }

    @Test
    @DisplayName("LogIn fail")
    fun logInFail() {
        assertThrows<LogInFailedException> { facade.logInConsumer(ConsumerMockData[0].login, "lolololo") }
    }

    @Test
    @DisplayName("LogIn, no User")
    fun logInNoUser() {
        assertThrows<NotExistingUserException> { facade.logInConsumer("rtyu", "lololololo") }
    }
}