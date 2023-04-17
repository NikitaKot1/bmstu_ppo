import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.github.cdimascio.dotenv.dotenv
import org.jetbrains.exposed.sql.Database

fun main(args: Array<String>) {
    val dotenv = dotenv()
    val hikariConfig = HikariConfig().apply {
        jdbcUrl =
            "jdbc:" + dotenv["DB_CONNECT"] + "://" + dotenv["DB_HOST"] + ":" + dotenv["DB_PORT"] + "/" + dotenv["DB_NAME"]
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