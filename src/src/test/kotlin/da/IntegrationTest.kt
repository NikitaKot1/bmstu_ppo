package da
//
//import bl.Facade
//import da.dao.RecipePreviewTable
//import da.dao.Recipes
//import da.dao.toEntity
//import da.repositories.factory.PgRepositoryFactory
//import org.jetbrains.exposed.sql.SortOrder
//import org.jetbrains.exposed.sql.selectAll
//import org.jetbrains.exposed.sql.transactions.transaction
//import org.junit.jupiter.api.DisplayName
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.assertDoesNotThrow
//import kotlin.test.assertEquals
//
//class IntegrationTest {
//    private val facade = Facade(PgRepositoryFactory())
//
//    @Test
//    @DisplayName("Update Comment")
//    fun seeFeed() {
//        val login = "login1"
//        val password = "password1"
//
//        assertDoesNotThrow { facade.logIn(login, password) }
//
//        val feed = facade.getFeedSortedByDate()
//        val expectedFeed = transaction {
//            val query = Recipes.selectAll().orderBy(Recipes.date to SortOrder.DESC)
//            RecipePreviewTable.wrapRows(query).map { it.toEntity() }
//        }
//
//        assertEquals(feed, expectedFeed)
//    }
//
//
//}