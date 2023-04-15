package da.repositories.factory

import bl.repositories.factory.IRepositoryFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import da.repositories.*
import io.github.cdimascio.dotenv.dotenv
import org.jetbrains.exposed.sql.Database

class PgRepositoryFactory : IRepositoryFactory {
    init {
        val dotenv = dotenv()
        val hikariConfig = HikariConfig().apply {
            jdbcUrl = "jdbc:" + dotenv["DB_CONNECT"] + "://" + dotenv["DB_HOST"] + ":" +
                    dotenv["DB_PORT"] + "/" + dotenv["DB_NAME"]
            driverClassName = "org.postgresql.Driver"
            username = dotenv["DB_USER"]
            password = dotenv["DB_PASSWORD"]
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }

        Database.connect(HikariDataSource(hikariConfig))
    }

    override fun createCommentRepository(): PgCommentRepository = PgCommentRepository()

    override fun createIngredientRepository(): PgIngredientRepository = PgIngredientRepository()

    override fun createRecipeRepository(): PgRecipeRepository = PgRecipeRepository()

    override fun createConsumerRepository(): PgConsumerRepository = PgConsumerRepository()

    override fun createManufacturerRepository(): PgManufacturerRepository = PgManufacturerRepository()
}