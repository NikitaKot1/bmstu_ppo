package da

import bl.Facade
import bl.entities.*
import bl.exceptions.*
import da.dao.*
import da.exeption.NotFoundInDBException
import da.repositories.factory.PgRepositoryFactory
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*
import java.time.LocalDateTime
import kotlin.test.assertEquals

val facade = Facade(PgRepositoryFactory())


class IntegrationTest {

    private val login = "testlog1"
    private val password = "testpass1"
    val manuf = Manufacturer(6u, login, password)

    @Test
    @DisplayName("Create Manufacturer")
    fun createManufacturer() {
        facade.createManufacturer(manuf)
        val actual = facade.getManufacturer(manuf.id)
        assertEquals(manuf, actual)
    }

    @Test
    @DisplayName("logIn Manufacturer does not exists")
    fun logInManufacturerNotExists() {
        val log = "1"
        val pas = "1"
        assertThrows<NotFoundInDBException> { facade.logInManufacturer(log, pas) }
    }

    @Test
    @DisplayName("logIn Manufacturer")
    fun logInManufacturer() {
        assertDoesNotThrow { facade.logInManufacturer(login, password) }
    }

    @Test
    @DisplayName("Create Ingredient")
    fun createIngredient() {
        var expected = Ingredient(6u, "dark hop", IngredientType.HOP, 1u)
        facade.createIngredient(expected)
        val actual = facade.findByNameIngredient("dark hop")
        expected.id = actual.id
        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("Create Ingredient, not uniq")
    fun createIngredientNotUniq() {
        val tmp = Ingredient(1u, "name3", IngredientType.HOP, 1u)

        assertThrows<AlreadyExistingIngredientException> { facade.createIngredient(tmp) }
    }

    @Test
    @DisplayName("Update Ingredient")
    fun updateIngredient() {
        val expected = facade.getIngredient(1u)
        expected.name = "fixed1"
        facade.updateIngredient(expected)
        val actual = facade.getIngredient(1u)

        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("Update Ingredient, not exist")
    fun updateIngredientNotExist() {
        val tmp = Ingredient(9u, "kaka", IngredientType.HOP, 1u)

        assertThrows<NotExistingIngredientException> { facade.updateIngredient(tmp) }
    }

    @Test
    @DisplayName("Delete Ingredient")
    fun deleteIngredient() {
        val expected = Ingredient(7u, "absolute uniq", IngredientType.YEAST, 1u)
        facade.createIngredient(expected)
        val nows = facade.findByNameIngredient("absolute uniq")
        facade.deleteIngredient(nows.id)
        assertThrows<NotExistingIngredientException> { facade.getIngredient(nows.id) }
    }

    @Test
    @DisplayName("Delete Ingredient, not exist")
    fun deleteIngredientNotExist() {
        assertThrows<NotExistingIngredientException> { facade.deleteIngredient(9u) }
    }

    @Test
    @DisplayName("Find by name Ingredient")
    fun findByNameIngredient() {
        val expected = facade.getIngredient(1u)
        val actual = facade.findByNameIngredient(expected.name)

        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("Find by name Ingredient, not exist")
    fun findByNameIngredientNotExist() {
        assertThrows<NotFoundInDBException> { facade.findByNameIngredient("plohaya kaka") }
    }

    @Test
    @DisplayName("Create Mark")
    fun createMark() {
        val expected = Recipe(
            6u,
            "Pilsner dark 2",
            "this is perfect",
            LocalDateTime.now(),
            emptyList<Comment>(),
            manuf,
            listOf()
        )
        facade.createRecipe(expected)
        var idd = 0
        transaction {
            for (act in RecipeTable.all())
                if (act.name == "Pilsner dark 2")
                    idd = act.id.value
        }

        val actual = facade.getRecipe(idd.toULong())
        expected.id = idd.toULong()
        expected.date = actual.date
        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("Update Mark")
    fun updateMark() {
        val expected = facade.getRecipe(5u)
        expected.description = "fixed1"
        facade.updateRecipe(expected)
        val actual = facade.getRecipe(5u)

        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("Update Mark, not exist")
    fun updateMarkNotExist() {
        val tmp = Recipe(
            9u,
            "",
            "description9",
            LocalDateTime.now(),
            emptyList<Comment>(),
            manuf,
            emptyList()
        )

        assertThrows<NotExistingRecipeException> { facade.updateRecipe(tmp) }
    }

    @Test
    @DisplayName("Delete Mark")
    fun deleteMark() {
        val newmark = Recipe(
            6u,
            "Newmark",
            "this is not perfect",
            LocalDateTime.now(),
            emptyList<Comment>(),
            manuf,
            listOf()
        )
        facade.createRecipe(newmark)
        var idd = 0
        transaction {
            for (act in RecipeTable.all())
                if (act.name == "Newmark")
                    idd = act.id.value
        }
        val res = facade.getRecipe(idd.toULong())
        for (com in res.comments)
            facade.deleteComment(com.id)
        facade.deleteRecipe(idd.toULong())
        assertThrows<NotExistingRecipeException> { facade.getRecipe(idd.toULong()) }
    }

    @Test
    @DisplayName("Delete Mark, not exist")
    fun deleteMarkNotExist() {
        assertThrows<NotExistingRecipeException> { facade.deleteRecipe(666u) }
    }
}

class CommentTest {
    @Test
    @DisplayName("Update Comment")
    fun updateComment() {
        val expected = facade.getComment(1u)
        expected.text = "fixed1"
        facade.updateComment(expected)
        val actual = facade.getComment(1u)

        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("Update Comment, not exist")
    fun updateCommentNotExist() {
        val tmp = Comment(9u, LocalDateTime.now(), "text1", facade.getConsumer(1u))

        assertThrows<NotExistingCommentException> { facade.updateComment(tmp) }
    }

    @Test
    @DisplayName("Delete Comment")
    fun deleteComment() {
        facade.deleteComment(3u)
        assertThrows<NotExistingCommentException> { facade.getComment(3u) }
    }

    @Test
    @DisplayName("Delete Comment, not exist")
    fun deleteCommentNotExist() {
        assertThrows<NotExistingCommentException> { facade.deleteComment(9u) }
    }
}


class MarkTest {


    @Test
    @DisplayName("Get Mark, not exist")
    fun getMarkNotExist() {
        assertThrows<NotExistingRecipeException> { facade.getRecipe(9u) }
    }


    @Test
    @DisplayName("Add to favorite")
    fun addToFavorite() {
        val expected = Pair<ULong, ULong>(3u, 3u)
        facade.addToFavorite(expected.first, expected.second)
        val act = transaction {
            SavedRecipeTable.findById(16)
        }
        val usid = act?.userId?.value
        val repid = act?.recipeId?.value
        val actual = usid?.let { repid?.let { it1 -> Pair<ULong, ULong>(it.toULong(), it1.toULong()) } }
        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("Add to favorite, no recipe")
    fun addToFavoriteNoRecipe() {
        assertThrows<NotExistingRecipeException> { facade.addToFavorite(4u, 666u) }
    }

    @Test
    @DisplayName("Add to favorite, no user")
    fun addToFavoriteNoUser() {
        assertThrows<NotExistingUserException> { facade.addToFavorite(666u, 4u) }
    }


    @Test
    @DisplayName("Add comment")
    fun addComment() {
        val expected = Comment(0u, LocalDateTime.now(), "text5", facade.getConsumer(1u))
        facade.addComment(1u, "text5", 1u)
        val actual1 = facade.getComment(6u)
        val actual2 = facade.getRecipe(1u).comments[1]

        expected.id = actual1.id
        actual1.date = expected.date
        actual2.date = expected.date

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
        val actual = facade.isOwner(1u, 1u)
        val expected = true

        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("Isn't owner")
    fun isNotOwner() {
        val actual = facade.isOwner(2u, 1u)
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
        val actual = facade.getConsumer(6u)

        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("Create Consumer, not uniq")
    fun createConsumerNotUniq() {
        val tmp = Consumer(5u, "login1", "password1")

        assertThrows<AlreadyExistingUserException> { facade.createConsumer(tmp) }
    }

    @Test
    @DisplayName("Get Consumer")
    fun getConsumer() {
        val expected = transaction { ConsumerTable.findById(1)?.toEntity() }
        val actual = facade.getConsumer(1u)

        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("Get Consumer, not exist")
    fun getConsumerNotExist() {
        assertThrows<NotExistingUserException> { facade.getConsumer(666u) }
    }

    @Test
    @DisplayName("Update Consumer")
    fun updateConsumer() {
        val expected = facade.getConsumer(1u)
        expected.password = "fixed1"
        facade.updateConsumer(expected)
        val actual = facade.getConsumer(1u)

        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("Update Consumer, not exist")
    fun updateConsumerNotExist() {
        val tmp = Consumer(123u, "login1", "password1")

        assertThrows<NotExistingUserException> { facade.updateConsumer(tmp) }
    }

    @Test
    @DisplayName("Delete Consumer")
    fun deleteConsumer() {
        val expected = Consumer(6u, "Master Anala", "moi dungeon")
        facade.createConsumer(expected)
        var idd = 0
        transaction {
            for (con in ConsumerTable.all())
                if (con.login == "Master Anala")
                    idd = con.id.value
        }
        facade.deleteConsumer(idd.toULong())
        assertThrows<NotExistingUserException> { facade.getConsumer(idd.toULong()) }
    }

    @Test
    @DisplayName("Delete Consumer, not exist")
    fun deleteConsumerNotExist() {
        assertThrows<NotExistingUserException> { facade.deleteConsumer(543u) }
    }
}

class ManufacturerAccountTest {
    @Test
    @DisplayName("Register Manufacturer")
    fun register() {
        val login = "Astolfo"
        val password = "hentai666"

        var expected = Manufacturer(0u, login, password)
        facade.registerManufacturer(login, password)
        var idd = 0
        val act = transaction {
            for (man in ManufacturerTable.all())
                if (man.login == login)
                    idd = man.id.value
        }
        val actual = facade.getManufacturer(idd.toULong())
        expected.id = actual.id
        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("Register, not uniq")
    fun registerNotUniq() {
        val login = "login1"
        val password = "password1"

        assertThrows<AlreadyExistingUserException> { facade.registerManufacturer(login, password) }
    }

    @Test
    @DisplayName("LogIn")
    fun logIn() {
        val res = facade.logInManufacturer(facade.getManufacturer(1u).login, facade.getManufacturer(1u).password)

        assertEquals(facade.getManufacturer(1u), res)
    }

    @Test
    @DisplayName("LogIn fail")
    fun logInFail() {
        assertThrows<NotExistingUserException> {
            facade.logInManufacturer(
                facade.getManufacturer(0u).login,
                "lolololo"
            )
        }
    }

    @Test
    @DisplayName("LogIn, no Manufacturer")
    fun logInNoManufacturer() {
        assertThrows<NotFoundInDBException> { facade.logInManufacturer("rtyu", "lololololo") }
    }
}


class ConsumerAccountTest {
    @Test
    @DisplayName("Register Consumer")
    fun register() {
        val login = "Astolfo"
        val password = "hentai666"

        var expected = Consumer(0u, login, password)
        facade.registerConsumer(login, password)
        var idd = 0
        val act = transaction {
            for (man in ConsumerTable.all())
                if (man.login == login)
                    idd = man.id.value
        }
        val actual = facade.getConsumer(idd.toULong())
        expected.id = actual.id
        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("Register, not uniq")
    fun registerNotUniq() {
        val login = "login1"
        val password = "password1"

        assertThrows<AlreadyExistingUserException> { facade.registerConsumer(login, password) }
    }

    @Test
    @DisplayName("LogIn")
    fun logIn() {
        val res = facade.logInConsumer(facade.getConsumer(1u).login, facade.getConsumer(1u).password)

        assertEquals(facade.getConsumer(1u), res)
    }

    @Test
    @DisplayName("LogIn fail")
    fun logInFail() {
        assertThrows<NotExistingUserException> {
            facade.logInConsumer(
                facade.getConsumer(0u).login,
                "lolololo"
            )
        }
    }

    @Test
    @DisplayName("LogIn, no Consumer")
    fun logInNoConsumer() {
        assertThrows<NotFoundInDBException> { facade.logInConsumer("rtyu", "lololololo") }
    }
}